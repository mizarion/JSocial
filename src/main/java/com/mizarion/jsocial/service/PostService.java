package com.mizarion.jsocial.service;

import com.mizarion.jsocial.model.dto.PostDto;

import java.util.List;

public interface PostService {

    List<PostDto> getAllPosts();

    List<PostDto> getOwnerPosts(String owner);

    void savePost(PostDto postDto);
}
