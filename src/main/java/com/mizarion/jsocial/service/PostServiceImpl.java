package com.mizarion.jsocial.service;

import com.mizarion.jsocial.model.dto.PostDto;
import com.mizarion.jsocial.model.entity.PostEntity;
import com.mizarion.jsocial.repository.PostRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<PostDto> getAllPosts() {
        return StreamSupport.stream(postRepository.findAll().spliterator(), false)
                .map(e -> modelMapper.map(e, PostDto.class))
                .toList();
    }

    @Override
    public List<PostDto> getOwnerPosts(String owner) {
        return postRepository.findAllByOwner(owner).stream()
                .map(e -> modelMapper.map(e, PostDto.class))
                .toList();
    }

    @SneakyThrows
    @Override
    public void savePost(PostDto postDto) {

        if (postRepository.existsByOwnerAndTitle(postDto.getOwner(), postDto.getTitle())) {
            throw new IllegalAccessException("Post with owner '" + postDto.getOwner() + "' and title '" + postDto.getTitle() + "'exist");
        }
        postRepository.save(modelMapper.map(postDto, PostEntity.class));
    }
}
