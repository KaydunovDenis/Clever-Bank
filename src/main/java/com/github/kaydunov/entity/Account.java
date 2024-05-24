package com.github.kaydunov.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Long id;
    private BigDecimal balance;
    private Long bankId;
    private Long userId;
    private List<Operation> operations;

}
