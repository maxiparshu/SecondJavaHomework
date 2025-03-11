package org.example.springapp.exception;

public class BadRequestException extends Exception{
    public BadRequestException(final String message) {
        super(message);
    }
}
