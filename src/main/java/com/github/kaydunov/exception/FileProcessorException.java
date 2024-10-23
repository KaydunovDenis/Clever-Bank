package com.github.kaydunov.exception;

import java.io.IOException;

public class FileProcessorException extends RuntimeException  {
    public FileProcessorException(String s, IOException e) {
        super("Exception saving to file: " + s, e);
    }
}
