package com.mizarion.jsocial.controller;

import com.mizarion.jsocial.model.dto.post.CreatePostDto;
import com.mizarion.jsocial.model.dto.post.DeletePostDto;
import com.mizarion.jsocial.model.dto.post.PostDto;
import com.mizarion.jsocial.model.dto.post.UpdatePostDto;
import com.mizarion.jsocial.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
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

    /**
     * Возвращает список постов текущего пользователя
     */
    @GetMapping()
    public ResponseEntity<List<PostDto>> getOwnerPosts(Authentication authentication) {
        return ResponseEntity.ok(postService.getOwnerPosts(authentication.getName()));
    }

    /**
     * Создает пост
     */
    @PostMapping()
    public ResponseEntity<PostDto> createPost(Authentication authentication, @RequestBody @Valid CreatePostDto dto) {
        String user = authentication.getName();
        log.info("user '" + user + "' creating post");
        PostDto postDto = new PostDto(user, dto.getTitle(), dto.getText(), LocalDateTime.now());
        return ResponseEntity.ok(postService.savePost(postDto));
    }

    /**
     * Обновляет пост.
     */
    @PutMapping()
    public ResponseEntity<PostDto> updatePost(Authentication authentication, @RequestBody @Valid UpdatePostDto dto) {
        String user = authentication.getName();
        log.info("user '" + user + "' update post");
        PostDto postDto = postService.updatePost(new PostDto(dto.getId(), user, dto.getTitle(), dto.getText(), LocalDateTime.now()));
        return ResponseEntity.ok(postDto);
    }

    /**
     * Удаляет пост
     */
    @DeleteMapping()
    public ResponseEntity<Void> deletePost(Authentication authentication, @RequestParam Long id) {
        String user = authentication.getName();
        log.info("user '" + user + "' delete post");
        postService.deletePost(new DeletePostDto(id, user));
        return ResponseEntity.ok(null);
    }
}
