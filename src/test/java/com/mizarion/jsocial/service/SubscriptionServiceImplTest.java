package com.mizarion.jsocial.service;

import com.mizarion.jsocial.model.dto.SubscriptionDto;
import com.mizarion.jsocial.model.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SubscriptionServiceImplTest {


    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;
    private final static UserDto user1 = new UserDto("testuser1@mail.com", "user1", "password");
    private final static UserDto user2 = new UserDto("testuser2@mail.com", "user2", "password");


    @BeforeEach
    void init() {
        userService.addUser(user1);
        userService.addUser(user2);
    }

    @Test
    void subscribeTest() {
        Assertions.assertTrue(subscriptionService.getSubscriptions(user1.getUsername()).isEmpty());

        subscriptionService.subscribe(new SubscriptionDto(user1.getUsername(), user2.getUsername()));

        Assertions.assertEquals(1, subscriptionService.getSubscriptions(user1.getUsername()).size());
    }

    /**
     * user1 subscribe on user2
     * user2's subscriptions not changing
     */
    @Test
    void oneSideSubscribeTest() {
        Assertions.assertTrue(subscriptionService.getSubscriptions(user2.getUsername()).isEmpty());

        subscriptionService.subscribe(new SubscriptionDto(user1.getUsername(), user2.getUsername()));

        Assertions.assertTrue(subscriptionService.getSubscriptions(user2.getUsername()).isEmpty());
    }

    @Test
    void subscribeTwiceTest() {
        Assertions.assertTrue(subscriptionService.getSubscriptions(user1.getUsername()).isEmpty());

        SubscriptionDto subscriptionDto = new SubscriptionDto(user1.getUsername(), user2.getUsername());
        subscriptionService.subscribe(subscriptionDto);
        Assertions.assertEquals(1, subscriptionService.getSubscriptions(user1.getUsername()).size());
        // Ошибка повторной подписки
        Assertions.assertThrows(Exception.class, () -> subscriptionService.subscribe(subscriptionDto));
        // Количество подписок не изменилось
        Assertions.assertEquals(1, subscriptionService.getSubscriptions(user1.getUsername()).size());
    }
}