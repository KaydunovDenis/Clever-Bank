package com.github.kaydunov.exception;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException() {
        super("Insufficient funds in account");
    }
}
