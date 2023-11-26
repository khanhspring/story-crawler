package com.storyscawler.infrastructure.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationException extends RuntimeException {

    private String message;

    public ApplicationException() {
        super();
    }

    public ApplicationException(Throwable e) {
        super(e);
        this.message = e.getMessage();
    }

    public ApplicationException(String message) {
        super(message);
        this.message = message;
    }
}
