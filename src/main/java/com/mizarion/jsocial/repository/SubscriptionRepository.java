package com.mizarion.jsocial.repository;

import com.mizarion.jsocial.model.entity.SubscriptionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, UUID> {

    Boolean existsBySubscriberAndPublisher(String subscriber, String publisher);

    Optional<SubscriptionEntity> findBySubscriberAndPublisher(String subscriber, String publisher);

    List<SubscriptionEntity> findAllBySubscriber(String subscriber);

}