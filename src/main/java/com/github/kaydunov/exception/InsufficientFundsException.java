package com.github.kaydunov.exception;

public class InsufficientFundsException extends RuntimeException {
    private static final String INSUFFICIENT_FUNDS = "Insufficient funds in account";

    public InsufficientFundsException() {
        super(INSUFFICIENT_FUNDS);
    }
}
