package com.mizarion.jsocial.controller;

import com.mizarion.jsocial.model.dto.SubscriptionDto;
import com.mizarion.jsocial.service.SubscriptionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
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

    @GetMapping()
    @ApiOperation("Returns a list of all subscriptions of an authorized user")
    @ApiResponse(code = 200, message = "List of subscriptions")
    public ResponseEntity<List<SubscriptionDto>> getSubscriptions(Authentication authentication) {
        String subscriber = authentication.getName();
        return ResponseEntity.ok(subscriptionService.getSubscriptions(subscriber));
    }

    @PostMapping()
    @ApiOperation("Subscribe an authorized user to a user with the nickname 'username'")
    public ResponseEntity<Void> subscribe(Authentication authentication,
                                          @RequestParam(name = "username") @NotBlank String username) {
        subscriptionService.subscribe(new SubscriptionDto(authentication.getName(), username));
        return ResponseEntity.ok(null);
    }

    @DeleteMapping()
    @ApiOperation("UnSubscribe an authorized user from a user with the nickname 'username'")
    public ResponseEntity<Void> unsubscribe(Authentication authentication,
                                            @RequestParam(name = "username") @NotBlank String username) {
        subscriptionService.unsubscribe(new SubscriptionDto(authentication.getName(), username));
        return ResponseEntity.ok(null);
    }
}
