package com.storyscawler.infrastructure.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends ApplicationException {

    public ResourceNotFoundException() {
        super("Resource not found");
    }
}
