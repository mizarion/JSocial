package com.mizarion.jsocial.service;

import com.mizarion.jsocial.model.dto.SubscriptionDto;

import java.util.List;

public interface SubscriptionService {

    void subscribe(SubscriptionDto dto);

    void unsubscribe(SubscriptionDto dto);

    List<SubscriptionDto> getSubscriptions(String subscriber);

    boolean checkFriendship(SubscriptionDto subscriptionDto);
}
