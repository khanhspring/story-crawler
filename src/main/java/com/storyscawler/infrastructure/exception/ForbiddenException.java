package com.storyscawler.infrastructure.exception;

public class ForbiddenException extends ApplicationException {

    public ForbiddenException() {
        super("Forbidden");
    }
}
