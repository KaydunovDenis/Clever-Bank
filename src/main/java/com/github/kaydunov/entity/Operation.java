package com.github.kaydunov.entity;

import com.github.kaydunov.service.AccountService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Operation {
    Long id;
    BigDecimal amount;
    Timestamp createdAt;
    OperationType operationType;
    Long accountSourceId;
    Long destinationSourceId;
}
