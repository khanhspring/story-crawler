package com.storyscawler.infrastructure.config;

import com.storyscawler.infrastructure.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {UnauthorizedException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorResponse handle(UnauthorizedException e) {
        log.error("", e);
        return ErrorResponse.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorResponse handle(ForbiddenException e) {
        log.error("", e);
        return ErrorResponse.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorResponse handle(AccessDeniedException e) {
        log.error("", e);
        return ErrorResponse.builder()
                .message("Access denied")
                .build();
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResponse handle(ResourceNotFoundException e) {
        log.error("", e);
        return ErrorResponse.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = {ApplicationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(ApplicationException e) {
        log.error("", e);
        return ErrorResponse.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(Exception e) {
        log.error("", e);
        return ErrorResponse.builder()
                .message("Unknown error")
                .build();
    }
}
