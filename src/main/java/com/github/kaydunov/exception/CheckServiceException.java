package com.github.kaydunov.exception;

import java.io.IOException;

public class CheckServiceException extends RuntimeException  {
    public CheckServiceException(String s, IOException e) {
        super("Exception saving to file: " + s, e);
    }
}
