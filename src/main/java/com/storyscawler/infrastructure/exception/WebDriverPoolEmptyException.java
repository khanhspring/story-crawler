package com.storyscawler.infrastructure.exception;

public class WebDriverPoolEmptyException extends PoolEmptyException {
    public WebDriverPoolEmptyException() {
        super("Web driver pool is empty");
    }
}
