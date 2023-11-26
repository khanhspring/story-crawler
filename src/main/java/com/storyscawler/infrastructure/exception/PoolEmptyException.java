package com.storyscawler.infrastructure.exception;

public abstract class PoolEmptyException extends ApplicationException {
    public PoolEmptyException(String message) {
        super(message);
    }
}
