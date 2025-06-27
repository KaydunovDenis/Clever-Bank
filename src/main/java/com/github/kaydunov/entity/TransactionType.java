package com.github.kaydunov.entity;

public enum TransactionType {
    DEPOSIT,
    WITHDRAW,
    INTEREST;

    public static TransactionType getByOrdinal(int ordinal) {
        return TransactionType.values()[ordinal];
    }
}