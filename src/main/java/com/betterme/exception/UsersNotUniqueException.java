package com.betterme.exception;

public class UsersNotUniqueException extends RuntimeException {
    public UsersNotUniqueException() {
        super();
    }

    public UsersNotUniqueException(String message) {
        super(message);
    }

    public UsersNotUniqueException(String message, Throwable cause) {
        super(message, cause);
    }
}
