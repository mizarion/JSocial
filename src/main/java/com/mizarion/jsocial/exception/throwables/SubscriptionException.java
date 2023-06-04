package com.mizarion.jsocial.exception.throwables;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class SubscriptionException extends RuntimeException {
    @Getter
    private final HttpStatus status;

    public SubscriptionException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public SubscriptionException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }
}
