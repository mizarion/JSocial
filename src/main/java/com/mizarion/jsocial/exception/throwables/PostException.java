package com.mizarion.jsocial.exception.throwables;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class PostException extends RuntimeException {
    @Getter
    private final HttpStatus status;

    public PostException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public PostException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }
}
