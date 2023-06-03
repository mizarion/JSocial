package com.mizarion.jsocial.controller;

import com.mizarion.jsocial.model.dto.PostDto;
import com.mizarion.jsocial.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/post")
public class PostRestController {

    @Autowired
    private PostService postService;

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());

    }

    @GetMapping()
    public ResponseEntity<List<PostDto>> getOwnerPosts(Authentication authentication) {
        return ResponseEntity.ok(postService.getOwnerPosts(authentication.getName()));
    }

    @PostMapping()
    public ResponseEntity<Void> createPost(Authentication authentication, @RequestBody PostDto postDto) {
        String user = authentication.getName();
        log.info("user '" + user + "' creating post");

        postDto.setOwner(user);

        postService.savePost(postDto);
        return ResponseEntity.ok(null);
    }
}
