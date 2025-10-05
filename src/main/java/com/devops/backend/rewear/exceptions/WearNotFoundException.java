package com.devops.backend.rewear.exceptions;

public class WearNotFoundException extends RuntimeException {
    public WearNotFoundException(String message) {
        super(message);
    }
}
