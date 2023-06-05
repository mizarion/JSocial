package com.mizarion.jsocial.service;

import com.mizarion.jsocial.model.dto.post.DeletePostDto;
import com.mizarion.jsocial.model.dto.post.PostDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PostServiceImplTest {


    @Autowired
    private PostService postService;
    private final static PostDto dto = new PostDto(1L, "test_owner", "Test Title", "Test Text", LocalDateTime.now());
    private final static PostDto dto2 = new PostDto(1L, "test_owner", "Test Title 2", "Test Text 2", LocalDateTime.now());

    private final static PostDto user1 = new PostDto("test_user1", "Test User Title 1", "Test User Text 1");
    private final static PostDto user2 = new PostDto("test_user2", "Test User Title 2", "Test User Text 2");

    @Test
    public void getEmptyPostsTest() {
        Assertions.assertTrue(postService.getAllPosts().isEmpty());
    }

    @Test
    public void getEmptyOwnerPostsTest() {
        Assertions.assertTrue(postService.getOwnerPosts(dto.getOwner()).isEmpty());
    }

    @Test
    public void savePostTest() {
        Assertions.assertTrue(postService.getOwnerPosts(dto.getOwner()).isEmpty());
        postService.savePost(dto);
        Assertions.assertEquals(1, postService.getOwnerPosts(dto.getOwner()).size());
    }

    @Test
    public void deletePostTest() {
        Assertions.assertTrue(postService.getOwnerPosts(dto.getOwner()).isEmpty());
        postService.savePost(dto);
        Assertions.assertFalse(postService.getOwnerPosts(dto.getOwner()).isEmpty());
        postService.deletePost(new DeletePostDto(dto.getId(), dto.getOwner()));
        Assertions.assertTrue(postService.getOwnerPosts(dto.getOwner()).isEmpty());
    }

    @Test
    public void updatePostTestSize() {
        Assertions.assertTrue(postService.getOwnerPosts(dto.getOwner()).isEmpty());
        postService.savePost(dto);
        Assertions.assertEquals(1, postService.getOwnerPosts(dto.getOwner()).size());
        postService.updatePost(dto2);
        Assertions.assertEquals(1, postService.getOwnerPosts(dto2.getOwner()).size());
    }

    @Test
    public void updatePostTestValue() {
        Assertions.assertTrue(postService.getOwnerPosts(dto.getOwner()).isEmpty());
        postService.savePost(dto);
        Assertions.assertEquals(1, postService.getOwnerPosts(dto.getOwner()).size());
        postService.updatePost(dto2);
        Assertions.assertNotEquals(dto, postService.getOwnerPosts(dto2.getOwner()).get(0));
        Assertions.assertEquals(dto2, postService.getOwnerPosts(dto2.getOwner()).get(0));
    }

    /**
     * Пользователей пытается изменить чужой пост
     */
    @Test
    public void anotherUserUpdatesPostTest() {
        PostDto dto3 = new PostDto(dto.getId(), "test_owner 3", "Test Title 3", "Test Text 3", LocalDateTime.now());
        Assertions.assertTrue(postService.getOwnerPosts(dto.getOwner()).isEmpty());
        postService.savePost(dto);
        Assertions.assertEquals(1, postService.getOwnerPosts(dto.getOwner()).size());
        // При попытке изменения выдается исключение
        Assertions.assertThrows(Exception.class, () -> postService.updatePost(dto3));
        // Данные изменяемого пользователя не меняются
        Assertions.assertEquals(dto, postService.getOwnerPosts(dto.getOwner()).get(0));
        // Новый пост не создается у меняющего
        Assertions.assertTrue(postService.getOwnerPosts(dto3.getOwner()).isEmpty());
    }

    /**
     * Пользователей пытается изменить чужой пост
     */
    @Test
    public void twoUserCreatePostsTest() {
        Assertions.assertTrue(postService.getOwnerPosts(user1.getOwner()).isEmpty());
        Assertions.assertTrue(postService.getOwnerPosts(user2.getOwner()).isEmpty());

        postService.savePost(user1);
        Assertions.assertEquals(1, postService.getOwnerPosts(user1.getOwner()).size());
        Assertions.assertEquals(0, postService.getOwnerPosts(user2.getOwner()).size());

        postService.savePost(user2);
        Assertions.assertEquals(1, postService.getOwnerPosts(user1.getOwner()).size());
        Assertions.assertEquals(1, postService.getOwnerPosts(user2.getOwner()).size());

        postService.savePost(new PostDto(user1.getOwner(), user1.getTitle() + " 2", user1.getText() + " 2"));
        Assertions.assertEquals(2, postService.getOwnerPosts(user1.getOwner()).size());
        Assertions.assertEquals(1, postService.getOwnerPosts(user2.getOwner()).size());
    }
}