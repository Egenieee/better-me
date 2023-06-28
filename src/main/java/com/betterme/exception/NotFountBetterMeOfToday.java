package com.betterme.exception;

public class NotFountBetterMeOfToday extends RuntimeException {
    public NotFountBetterMeOfToday() {
        super();
    }

    public NotFountBetterMeOfToday(String message) {
        super(message);
    }

    public NotFountBetterMeOfToday(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFountBetterMeOfToday(Throwable cause) {
        super(cause);
    }
}
