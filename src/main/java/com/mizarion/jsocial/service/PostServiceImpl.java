package com.mizarion.jsocial.service;

import com.mizarion.jsocial.model.dto.post.DeletePostDto;
import com.mizarion.jsocial.model.dto.post.PostDto;
import com.mizarion.jsocial.model.entity.PostEntity;
import com.mizarion.jsocial.repository.PostRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    // todo: Разрешить дубли?
    @SneakyThrows
    @Override
    public PostDto savePost(PostDto postDto) {
        if (postRepository.existsByOwnerAndTitle(postDto.getOwner(), postDto.getTitle())) {
            throw new IllegalArgumentException("Post with owner '" + postDto.getOwner() + "' and title '" + postDto.getTitle() + "' exist");
        }
//        return postRepository.save(modelMapper.map(postDto, PostEntity.class));

        PostEntity entity = modelMapper.map(postDto, PostEntity.class);
        PostEntity saved = postRepository.save(entity);
        return modelMapper.map(saved, PostDto.class);
    }

    @SneakyThrows
    @Override
    public void deletePost(DeletePostDto postDto) {
        Optional<PostEntity> postEntity = postRepository.findById(postDto.getId());
        if (postEntity.isEmpty()) {
            throw new IllegalArgumentException("Post with id '" + postDto.getId() + "' not exist");
        } else if (!postEntity.get().getOwner().equals(postDto.getOwner())) {
            throw new IllegalArgumentException("Post with id '" + postDto.getId() + "' belongs to another user");
        }
        postRepository.delete(postEntity.get());
    }

    @SneakyThrows
    @Override
    public PostDto updatePost(PostDto postDto) {
        Optional<PostEntity> postEntity = postRepository.findById(postDto.getId());
        if (postEntity.isEmpty()) {
            throw new IllegalArgumentException("Post with id '" + postDto.getId() + "' not exist");
        } else if (!postEntity.get().getOwner().equals(postDto.getOwner())) {
            throw new IllegalArgumentException("Post with id '" + postDto.getId() + "' belongs to another user");
        }
        PostEntity entity = modelMapper.map(postDto, PostEntity.class);
        PostEntity saved = postRepository.save(entity);
        return modelMapper.map(saved, PostDto.class);
    }
}
