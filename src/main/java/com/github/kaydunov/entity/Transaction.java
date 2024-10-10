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

    public Transaction(BigDecimal amount, Timestamp createdAt, Long accountSourceId, Long accountDestinationId, TransactionType transactionType) {
        this.amount = amount;
        this.createdAt = createdAt;
        this.accountSourceId = accountSourceId;
        this.accountDestinationId = accountDestinationId;
        this.transactionType = transactionType;
    }//todo remove
}
