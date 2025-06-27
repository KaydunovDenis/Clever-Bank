package com.github.kaydunov.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transaction {
    Long id;
    BigDecimal amount;
    Timestamp createdAt;
    String sourceAccountId;
    String destinationAccountId;
    TransactionType transactionType;
    String currency;

    /**
     * I need this constructor to generate ID using database
     * Lombok can't to create constructor without one argument
     *
     * @param amount
     * @param createdAt
     * @param sourceAccountId
     * @param destinationAccountId
     * @param transactionType
     * @param currency
     */
    public Transaction(BigDecimal amount,
                       Timestamp createdAt,
                       String sourceAccountId,
                       String destinationAccountId,
                       TransactionType transactionType,
                       String currency) {
        this.amount = amount;
        this.createdAt = createdAt;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.transactionType = transactionType;
        this.currency = currency;
    }
}
