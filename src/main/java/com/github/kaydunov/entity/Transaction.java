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
    Long accountSourceId;
    Long accountDestinationId;
    TransactionType transactionType;

    /**
     * I need this constructor to generate ID using database
     * Lombok can't to create constructor without one argument
     * @param amount
     * @param createdAt
     * @param accountSourceId
     * @param accountDestinationId
     * @param transactionType
     */
    public Transaction(BigDecimal amount, Timestamp createdAt, Long accountSourceId, Long accountDestinationId, TransactionType transactionType) {
        this.amount = amount;
        this.createdAt = createdAt;
        this.accountSourceId = accountSourceId;
        this.accountDestinationId = accountDestinationId;
        this.transactionType = transactionType;
    }
}
