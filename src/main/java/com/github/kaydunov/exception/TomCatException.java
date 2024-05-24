package com.github.kaydunov.exception;

public class TomCatException extends RuntimeException {

    public TomCatException(String message, Exception e) {
        super(message, e);
    }
}
