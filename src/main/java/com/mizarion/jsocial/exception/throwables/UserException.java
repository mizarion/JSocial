package com.mizarion.jsocial.exception.throwables;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class UserException extends RuntimeException {
    @Getter
    private final HttpStatus status;

    public UserException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public UserException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }
}
