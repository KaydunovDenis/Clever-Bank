package com.github.kaydunov.exception;

public class DatabaseConfigurationException extends RuntimeException {
    public DatabaseConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
