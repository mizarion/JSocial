package com.mizarion.jsocial.controller;

import com.mizarion.jsocial.model.dto.SubscriptionDto;
import com.mizarion.jsocial.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@Slf4j
@RestController
@RequestMapping("/api/chat")
@Validated
public class ChatRestController {

    @Autowired
    private SubscriptionService subscriptionService;

    /**
     * Авторизованный пользователь запрашивает чат с username.
     */
    @GetMapping()
    public ResponseEntity<String> openChat(Authentication authentication,
                                           @RequestParam(name = "username") @NotBlank String username) {
        SubscriptionDto dto = new SubscriptionDto(authentication.getName(), username);
        log.info("user '" + dto.getSubscriber() + "' subscribe on '" + dto.getPublisher() + "'");
        boolean isFriends = subscriptionService.checkFriendship(dto);
        if (isFriends) {
            return new ResponseEntity<>(dto.getSubscriber() + " and " + dto.getPublisher() + " are friends.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(dto.getSubscriber() + " and " + dto.getPublisher() + " not friends.", HttpStatus.BAD_REQUEST);
        }
    }

}
