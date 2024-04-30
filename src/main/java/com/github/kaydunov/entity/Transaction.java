package com.github.kaydunov.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Transaction {
    Long id;
    BigDecimal amount;
    Timestamp timestamp;
    Type type;

    enum Type {

    }
}
