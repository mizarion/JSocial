package com.mizarion.jsocial.service;

import com.mizarion.jsocial.model.dto.post.DeletePostDto;
import com.mizarion.jsocial.model.dto.post.PostDto;

import java.util.List;

public interface PostService {

    List<PostDto> getAllPosts();

    List<PostDto> getOwnerPosts(String owner);

    PostDto savePost(PostDto postDto);

    PostDto updatePost(PostDto postDto);

    void deletePost(DeletePostDto postDto);
}
