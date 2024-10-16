package com.github.kaydunov.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Transaction {
    Long id;
    BigDecimal amount;
    Timestamp createdAt;
    Long sourceAccountId;
    Long destinationAccountId;
    TransactionType transactionType;

    /**
     * I need this constructor to generate ID using database
     * Lombok can't to create constructor without one argument
     * @param amount
     * @param createdAt
     * @param sourceAccountId
     * @param destinationAccountId
     * @param transactionType
     */
    public Transaction(BigDecimal amount, Timestamp createdAt, Long sourceAccountId, Long destinationAccountId, TransactionType transactionType) {
        this.amount = amount;
        this.createdAt = createdAt;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.transactionType = transactionType;
    }
}
