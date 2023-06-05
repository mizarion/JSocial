package com.mizarion.jsocial.service;

import com.mizarion.jsocial.exception.throwables.SubscriptionException;
import com.mizarion.jsocial.model.dto.SubscriptionDto;
import com.mizarion.jsocial.model.entity.SubscriptionEntity;
import com.mizarion.jsocial.repository.SubscriptionRepository;
import com.mizarion.jsocial.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void subscribe(SubscriptionDto dto) {
        if (!userRepository.existsByUsername(dto.getPublisher())) {
            throw new SubscriptionException("User with username '" + dto.getPublisher() + "' not exist");
        }
        if (subRepository.existsBySubscriberAndPublisher(dto.getSubscriber(), dto.getPublisher())) {
            throw new SubscriptionException("User with username '" + dto.getSubscriber() + "' already subscribe on '" + dto.getPublisher() + "'");
        }
        SubscriptionEntity entity = modelMapper.map(dto, SubscriptionEntity.class);
        subRepository.save(entity);
    }

    @Override
    public void unsubscribe(SubscriptionDto dto) {
        if (!userRepository.existsByUsername(dto.getPublisher())) {
            throw new SubscriptionException("User with username '" + dto.getPublisher() + "' not exist");
        }
        Optional<SubscriptionEntity> entity = subRepository.findBySubscriberAndPublisher(dto.getSubscriber(), dto.getPublisher());
        if (entity.isEmpty()) {
            throw new SubscriptionException("User with username '" + dto.getSubscriber() + "'not subscribe on '" + dto.getPublisher() + "'");
        }
        subRepository.delete(entity.get());
    }

    @Override
    public List<SubscriptionDto> getSubscriptions(String subscriber) {
        return subRepository.findAllBySubscriber(subscriber).stream()
                .map(e -> modelMapper.map(e, SubscriptionDto.class))
                .toList();
    }

    @Override
    public boolean checkFriendship(SubscriptionDto dto) {
        if (!userRepository.existsByUsername(dto.getPublisher())) {
            throw new SubscriptionException("User with username '" + dto.getPublisher() + "' not exist");
        }
        return subRepository.findBySubscriberAndPublisher(dto.getSubscriber(), dto.getPublisher()).isPresent()
               && subRepository.findBySubscriberAndPublisher(dto.getPublisher(), dto.getSubscriber()).isPresent();
    }
}
