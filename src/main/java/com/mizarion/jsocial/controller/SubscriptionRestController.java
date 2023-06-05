package com.mizarion.jsocial.controller;

import com.mizarion.jsocial.model.dto.SubscriptionDto;
import com.mizarion.jsocial.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/subscription")
@Validated
public class SubscriptionRestController {

    @Autowired
    private SubscriptionService subscriptionService;

    /**
     * Выводит список всех подписок авторизованного пользователя
     */
    @GetMapping()
    public ResponseEntity<List<SubscriptionDto>> getSubscriptions(Authentication authentication) {
        String subscriber = authentication.getName();
        return ResponseEntity.ok(subscriptionService.getSubscriptions(subscriber));
    }

    /**
     * Авторизованный пользователь подписывается на username
     */
    @PostMapping()
    public ResponseEntity<Void> subscribe(Authentication authentication,
                                          @RequestParam(name = "username") @NotBlank String username) {
        SubscriptionDto subscriptionDto = new SubscriptionDto(authentication.getName(), username);
        log.info("user '" + subscriptionDto.getSubscriber() + "' subscribe on '" + subscriptionDto.getPublisher() + "'");
        subscriptionService.subscribe(subscriptionDto);
        return ResponseEntity.ok(null);
    }

    /**
     * Авторизованный пользователь отписывается от username
     */
    @DeleteMapping()
    public ResponseEntity<Void> unsubscribe(Authentication authentication,
                                            @RequestParam(name = "username") @NotBlank String username) {
        SubscriptionDto subscriptionDto = new SubscriptionDto(authentication.getName(), username);
        log.info("user '" + subscriptionDto.getSubscriber() + "' unsubscribe from '" + subscriptionDto.getPublisher() + "'");
        subscriptionService.unsubscribe(subscriptionDto);
        return ResponseEntity.ok(null);
    }
}
