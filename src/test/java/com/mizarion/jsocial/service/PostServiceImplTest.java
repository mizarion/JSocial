package com.mizarion.jsocial.service;

import com.mizarion.jsocial.model.dto.PostDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PostServiceImplTest {


    @Autowired
    private PostService postService;
    private final static PostDto dto = new PostDto("test_owner", "Test Title", "Test Text");

    @Test
    public void getEmptyPosts() {
        Assertions.assertTrue(postService.getAllPosts().isEmpty());
    }

    @Test
    public void getEmptyOwnerPosts() {
        Assertions.assertTrue(postService.getOwnerPosts(dto.getOwner()).isEmpty());
    }

    @Test
    public void savePost() {
        Assertions.assertTrue(postService.getOwnerPosts(dto.getOwner()).isEmpty());
        postService.savePost(dto);
        Assertions.assertEquals(1, postService.getOwnerPosts(dto.getOwner()).size());

    }
}