package com.springboot.Exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super("Resource not found");
    }
    // Custom message
    public ResourceNotFoundException(String message) {
        super(message);
    }


}