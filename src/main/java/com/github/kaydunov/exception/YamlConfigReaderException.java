package com.github.kaydunov.exception;

import java.io.FileNotFoundException;

public class YamlConfigReaderException extends RuntimeException {

    public YamlConfigReaderException(FileNotFoundException e) {

    }
}
