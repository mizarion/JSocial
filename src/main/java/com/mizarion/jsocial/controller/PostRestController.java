package com.mizarion.jsocial.controller;

import com.mizarion.jsocial.model.dto.post.CreatePostDto;
import com.mizarion.jsocial.model.dto.post.DeletePostDto;
import com.mizarion.jsocial.model.dto.post.PostDto;
import com.mizarion.jsocial.model.dto.post.UpdatePostDto;
import com.mizarion.jsocial.service.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/post")
public class PostRestController {

    @Autowired
    private PostService postService;

    @GetMapping("/all")
    @ApiOperation("Get all existing messages from all users")
    @ApiResponse(code = 200, message = "List of posts")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());

    }

    @GetMapping()
    @ApiOperation("Returns a list of the current user's posts")
    @ApiResponse(code = 200, message = "List of posts")
    public ResponseEntity<List<PostDto>> getOwnerPosts(Authentication authentication) {
        return ResponseEntity.ok(postService.getOwnerPosts(authentication.getName()));
    }

    /**
     * Создает пост
     */
    @PostMapping()
    @ApiOperation("Returns a list of the current user's posts")
    @ApiResponse(code = 200, message = "List of posts")
    public ResponseEntity<PostDto> createPost(Authentication authentication, @RequestBody @Valid CreatePostDto createPostDto) {
        PostDto postDto = new PostDto(authentication.getName(), createPostDto.getTitle(), createPostDto.getText());
        return ResponseEntity.ok(postService.savePost(postDto));
    }

    @PutMapping()
    @ApiOperation("Updates the post by the given id")
    @ApiResponse(code = 200, message = "Successfully updated post")
    public ResponseEntity<PostDto> updatePost(Authentication authentication, @RequestBody @Valid UpdatePostDto updatePostDto) {
        PostDto postDto = new PostDto(updatePostDto.getId(), authentication.getName(), updatePostDto.getTitle(), updatePostDto.getText());
        return ResponseEntity.ok(postService.updatePost(postDto));
    }

    @DeleteMapping()
    @ApiOperation("Deletes the post by the given id")
    @ApiResponse(code = 200, message = "Successfully deleted post")
    public ResponseEntity<Void> deletePost(Authentication authentication, @RequestParam Long id) {
        postService.deletePost(new DeletePostDto(id, authentication.getName()));
        return ResponseEntity.ok(null);
    }
}
