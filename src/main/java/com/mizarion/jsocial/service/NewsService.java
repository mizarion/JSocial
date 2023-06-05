package com.mizarion.jsocial.service;

import com.mizarion.jsocial.model.dto.NewsRequestDto;
import com.mizarion.jsocial.model.dto.post.PostDto;

import java.util.List;

public interface NewsService {

    List<PostDto> getAllNews(String username);

    List<PostDto> getPageNews(NewsRequestDto username);

}
