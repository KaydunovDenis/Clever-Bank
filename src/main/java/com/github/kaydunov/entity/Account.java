package com.github.kaydunov.entity;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Long id;
    private BigDecimal balance;
    private Bank bank;
    private User user;


}
