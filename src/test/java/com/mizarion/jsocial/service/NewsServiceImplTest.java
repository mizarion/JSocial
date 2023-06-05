package com.mizarion.jsocial.service;

import com.mizarion.jsocial.model.dto.NewsRequestDto;
import com.mizarion.jsocial.model.dto.SubscriptionDto;
import com.mizarion.jsocial.model.dto.UserDto;
import com.mizarion.jsocial.model.dto.post.PostDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class NewsServiceImplTest {

    @Autowired
    private PostService postService;

    @Autowired
    private NewsService newsService;
    @Autowired
    private UserService userService;
    @Autowired
    private SubscriptionService subscriptionService;

    private final static UserDto user1 = new UserDto("testuser1@mail.com", "test_user1", "password");
    private final static UserDto user2 = new UserDto("testuser2@mail.com", "test_user2", "password");
    private final static UserDto user3 = new UserDto("testuser3@mail.com", "test_user3", "password");
    private final static PostDto post1 = new PostDto("test_user1", "Test User Title 1", "Test User Text 1");
    private final static PostDto post2 = new PostDto("test_user2", "Test User Title 2", "Test User Text 2");


    @BeforeEach
    void init() {
        // register users
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);
        // subscribe user
        SubscriptionDto sub12 = new SubscriptionDto(user1.getUsername(), user2.getUsername());
        SubscriptionDto sub21 = new SubscriptionDto(user2.getUsername(), user1.getUsername());
        subscriptionService.subscribe(sub12);
        subscriptionService.subscribe(sub21);
    }

    /**
     * Пост публикует user2.
     * user2 проверяет свои новости.
     * новости == посты пользователей, на которых подписан
     */
    @Test
    public void getAllPostByPublisherTest() {
        Assertions.assertTrue(newsService.getAllNews(user2.getUsername()).isEmpty());
        postService.savePost(post2);
        Assertions.assertTrue(newsService.getAllNews(user2.getUsername()).isEmpty());
    }

    /**
     * Пост публикует user2.
     * user1 проверяет свои новости.
     */
    @Test
    public void getAllPostBySubscriberTest() {
        Assertions.assertTrue(newsService.getAllNews(user1.getUsername()).isEmpty());
        postService.savePost(post2);
        Assertions.assertEquals(1, newsService.getAllNews(user1.getUsername()).size());
    }


    @Test
    public void getSinglePageNewsTest() {
        Assertions.assertTrue(newsService.getAllNews(user1.getUsername()).isEmpty());
        postService.savePost(post2);
        NewsRequestDto newstRequest = new NewsRequestDto(user1.getUsername(), 0, 5);
        Assertions.assertEquals(1, newsService.getPageNews(newstRequest).size());
    }

    @Test
    public void getPageNewsTest() {
        Assertions.assertTrue(newsService.getAllNews(user1.getUsername()).isEmpty());
        for (int i = 0; i < 12; i++) {
            postService.savePost(new PostDto(user2.getUsername(), i + " title", i + " text"));
        }
        int pageSize = 5;
        Assertions.assertEquals(pageSize, newsService.getPageNews(new NewsRequestDto(user1.getUsername(), 0, pageSize)).size());
        Assertions.assertEquals(pageSize, newsService.getPageNews(new NewsRequestDto(user1.getUsername(), 1, pageSize)).size());
        Assertions.assertEquals(2, newsService.getPageNews(new NewsRequestDto(user1.getUsername(), 2, pageSize)).size());
    }

    @Test
    public void dontGetPageNewsFromAnotherUserTest() {
        int pageSize = 5;
        Assertions.assertTrue(newsService.getPageNews(new NewsRequestDto(user1.getUsername(), 0, pageSize)).isEmpty());
        for (int i = 0; i < 10; i++) {
            postService.savePost(new PostDto(user3.getUsername(), i + " title", i + " text"));
        }
        Assertions.assertTrue(newsService.getPageNews(new NewsRequestDto(user1.getUsername(), 0, pageSize)).isEmpty());
    }


    /**
     * Создает посты с разными датами и проверяет сортировку.
     */
    @Test
    public void sortPageNewsTest() {
        int pageSize = 5;
        LocalDateTime time = LocalDateTime.now();

        Assertions.assertTrue(newsService.getAllNews(user1.getUsername()).isEmpty());

        for (int i = 1; i < 5; i++) {
            postService.savePost(new PostDto(user2.getUsername(), i + " title minus", i + " text", time.minusMinutes(i * 2)));
            postService.savePost(new PostDto(user2.getUsername(), i + " title plus", i + " text", time.plusMinutes(i * 2)));
        }

        List<PostDto> result = new ArrayList<>();
        result.addAll(newsService.getPageNews(new NewsRequestDto(user1.getUsername(), 0, pageSize)));
        result.addAll(newsService.getPageNews(new NewsRequestDto(user1.getUsername(), 1, pageSize)));

        for (int i = 0; i < result.size() - 1; i++) {
            Assertions.assertTrue(result.get(i).getCreationTime().isAfter(result.get(i + 1).getCreationTime()));
        }
    }
}