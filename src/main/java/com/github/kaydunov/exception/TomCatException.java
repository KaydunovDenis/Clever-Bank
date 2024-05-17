package com.github.kaydunov.exception;

import org.apache.catalina.LifecycleException;

public class TomCatException extends RuntimeException {

    public TomCatException(LifecycleException e) {

    }
}
