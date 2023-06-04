package com.mizarion.jsocial.service;

import com.mizarion.jsocial.exception.throwables.SubscriptionException;
import com.mizarion.jsocial.model.dto.SubscriptionDto;
import com.mizarion.jsocial.model.entity.SubscriptionEntity;
import com.mizarion.jsocial.repository.SubscriptionRepository;
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
    private ModelMapper modelMapper;

    @Override
    public void subscribe(SubscriptionDto dto) {

        if (subRepository.existsBySubscriberAndPublisher(dto.getSubscriber(), dto.getPublisher())) {
            throw new SubscriptionException("User with username '" + dto.getSubscriber() + "' already subscribe on '" + dto.getPublisher() + "'");
        }

        SubscriptionEntity entity = modelMapper.map(dto, SubscriptionEntity.class);
        subRepository.save(entity);
    }

    @Override
    public void unsubscribe(SubscriptionDto dto) {
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
}
