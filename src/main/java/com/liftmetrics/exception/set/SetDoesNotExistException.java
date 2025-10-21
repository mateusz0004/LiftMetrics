package com.liftmetrics.exception.set;

public class SetDoesNotExistException extends RuntimeException {
    public SetDoesNotExistException(String message) {
        super(message);
    }
}
