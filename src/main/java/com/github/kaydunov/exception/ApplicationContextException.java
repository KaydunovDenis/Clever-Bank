package com.github.kaydunov.exception;

public class ApplicationContextException extends RuntimeException {
    public ApplicationContextException(String s, Exception e) {
        super(s, e);
    }
}
