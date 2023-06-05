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
    private final static UserDto user3 = new UserDto("testuser3@mail.com", "user3", "password");

    private final static SubscriptionDto sub12 = new SubscriptionDto(user1.getUsername(), user2.getUsername());
    private final static SubscriptionDto sub21 = new SubscriptionDto(user2.getUsername(), user1.getUsername());

    @BeforeEach
    void init() {
        userService.addUser(user1);
        userService.addUser(user2);
    }

    @Test
    void subscribeTest() {
        Assertions.assertTrue(subscriptionService.getSubscriptions(user1.getUsername()).isEmpty());

        subscriptionService.subscribe(sub12);

        Assertions.assertEquals(1, subscriptionService.getSubscriptions(user1.getUsername()).size());
    }

    /**
     * user1 subscribe on user2
     * user2's subscriptions not changing
     */
    @Test
    void oneSideSubscribeTest() {
        Assertions.assertTrue(subscriptionService.getSubscriptions(user2.getUsername()).isEmpty());

        subscriptionService.subscribe(sub12);

        Assertions.assertTrue(subscriptionService.getSubscriptions(user2.getUsername()).isEmpty());
    }

    @Test
    void subscribeTwiceTest() {
        Assertions.assertTrue(subscriptionService.getSubscriptions(user1.getUsername()).isEmpty());

        subscriptionService.subscribe(sub12);
        Assertions.assertEquals(1, subscriptionService.getSubscriptions(user1.getUsername()).size());
        // Ошибка повторной подписки
        Assertions.assertThrows(Exception.class, () -> subscriptionService.subscribe(sub12));
        // Количество подписок не изменилось
        Assertions.assertEquals(1, subscriptionService.getSubscriptions(user1.getUsername()).size());
    }

    @Test
    void subscribeOnNonExistUserTest() {
        Assertions.assertTrue(subscriptionService.getSubscriptions(user1.getUsername()).isEmpty());

        Assertions.assertThrows(Exception.class, () -> subscriptionService.subscribe(new SubscriptionDto(user1.getUsername(), user3.getUsername())));

        Assertions.assertTrue(subscriptionService.getSubscriptions(user1.getUsername()).isEmpty());
    }

    @Test
    void friendshipTest() {
        Assertions.assertFalse(subscriptionService.checkFriendship(sub12));
        Assertions.assertFalse(subscriptionService.checkFriendship(sub21));
        subscriptionService.subscribe(sub12);
        Assertions.assertFalse(subscriptionService.checkFriendship(sub12));
        Assertions.assertFalse(subscriptionService.checkFriendship(sub21));
        subscriptionService.subscribe(sub21);
        Assertions.assertTrue(subscriptionService.checkFriendship(sub12));
        Assertions.assertTrue(subscriptionService.checkFriendship(sub21));
    }

    @Test
    void friendshipWithNonExistUserTest() {
        SubscriptionDto sub13 = new SubscriptionDto(user1.getUsername(), user3.getUsername());

        Assertions.assertThrows(Exception.class, () -> subscriptionService.checkFriendship(sub13));
    }

}