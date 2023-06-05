package com.mizarion.jsocial.exception;

import com.mizarion.jsocial.exception.throwables.PostException;
import com.mizarion.jsocial.exception.throwables.SubscriptionException;
import com.mizarion.jsocial.exception.throwables.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorDto> defaultErrorHandler(HttpServletRequest req, Exception e) {
        log.warn("e.toString()=" + e.toString());
        log.warn("e.getClass()=" + e.getClass());
        log.warn("e.getMessage()=" + e.getMessage());

        ErrorDto.ErrorDtoBuilder error = ErrorDto.builder()
                .timestamp(LocalDateTime.now())
                .path(req.getContextPath() + req.getServletPath())
                .message(e.getLocalizedMessage());

        if (e instanceof UserException ex) {
            return new ResponseEntity<>(error.build(), ex.getStatus());
        } else if (e instanceof PostException ex) {
            return new ResponseEntity<>(error.build(), ex.getStatus());
        } else if (e instanceof SubscriptionException ex) {
            return new ResponseEntity<>(error.build(), ex.getStatus());
        } else if (e instanceof MethodArgumentNotValidException ex) {
            List<String> missingParams = ex
                    .getBindingResult()
                    .getFieldErrors().stream()
                    .map(param -> "Missing param in JSON '" + param.getField() + "'. " + param.getDefaultMessage())
                    .toList();
            error.message(missingParams.toString());
            return new ResponseEntity<>(error.build(), HttpStatus.NOT_ACCEPTABLE);
        } else if (e instanceof MissingServletRequestParameterException
                   || e instanceof BadCredentialsException
                   || e instanceof HttpMessageNotReadableException
                   || e instanceof ConstraintViolationException) {
            return new ResponseEntity<>(error.build(), HttpStatus.NOT_ACCEPTABLE);
        } else {
            return new ResponseEntity<>(error.build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

