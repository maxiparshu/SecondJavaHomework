package org.example.springapp.exception;

public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException(final String message) {
        super(message);
    }
}
