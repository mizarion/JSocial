package com.mizarion.jsocial.controller;

import com.mizarion.jsocial.model.dto.NewsPageResponseDto;
import com.mizarion.jsocial.model.dto.NewsRequestDto;
import com.mizarion.jsocial.model.dto.post.PostDto;
import com.mizarion.jsocial.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/news")
@Validated
public class NewsRestController {

    @Autowired
    private NewsService newsService;

    /**
     * Авторизованный пользователь получает все посты подписчиков.
     */
    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllNews(Authentication authentication) {
        return ResponseEntity.ok(newsService.getAllNews(authentication.getName()));
    }


    /**
     * Авторизованный пользователь получает часть постов подписчиков отсортированных по времени создания.
     */
    @GetMapping("/{page}")
    public ResponseEntity<NewsPageResponseDto> getPageNews(Authentication authentication,
                                                           @PathVariable("page") @PositiveOrZero int page,
                                                           @RequestParam(name = "size", required = false, defaultValue = "10") @Positive int size) {
        List<PostDto> news = newsService.getPageNews(new NewsRequestDto(authentication.getName(), page, size));
        return ResponseEntity.ok(new NewsPageResponseDto(news, page, size));
    }

}
