package com.github.kaydunov.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Account {
    Long id;
    Long number;
    BigDecimal balance;
    Bank bank;
    List<Transaction> transactions;
}
