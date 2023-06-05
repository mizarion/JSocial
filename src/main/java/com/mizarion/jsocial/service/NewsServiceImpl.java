package com.mizarion.jsocial.service;

import com.mizarion.jsocial.model.dto.NewsRequestDto;
import com.mizarion.jsocial.model.dto.post.PostDto;
import com.mizarion.jsocial.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PostDto> getAllNews(String username) {
        return subscriptionService.getSubscriptions(username).stream()
                .map(subscriptionDto -> postService.getOwnerPosts(subscriptionDto.getPublisher()))
                .collect(ArrayList::new, List::addAll, List::addAll);
    }


    @Override
    public List<PostDto> getPageNews(NewsRequestDto dto) {
        Pageable pageable = PageRequest.of(dto.getPageNum(), dto.getPageSize(), Sort.by("creationTime").descending());
        return subscriptionService.getSubscriptions(dto.getUsername()).stream()
                .map(sub -> postRepository.findAllByOwner(sub.getPublisher(), pageable))
                .collect(ArrayList::new, List::addAll, List::addAll)
                .stream().map(entity -> modelMapper.map(entity, PostDto.class))
                .toList();
    }
}
