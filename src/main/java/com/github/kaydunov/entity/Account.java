package com.github.kaydunov.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Account {
    private Long id;
    private Long number;
    private BigDecimal balance;
    private Bank bank;
    private List<Transaction> transactions;


    public Account(Long id, Long number, BigDecimal balance) {
        this.id = id;
        this.number = number;
        this.balance = balance;
    }
}
