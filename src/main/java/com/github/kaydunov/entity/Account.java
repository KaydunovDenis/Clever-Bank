package com.github.kaydunov.entity;

import com.github.kaydunov.exception.InsufficientFundsException;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Account {
    Long id;
    BigDecimal balance;
    Long bankId;//todo bank   здесь они не нужны
    Long userId;//todo user
    List<Transaction> transactions;
    boolean isSavingAccount;

    public void withdrawBalance(BigDecimal amount) {
        BigDecimal newSourceBalance = this.getBalance().subtract(amount);
        if (newSourceBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsException();
        }
        this.setBalance(newSourceBalance);
    }

    public void depositBalance(BigDecimal amount) {
        BigDecimal newSourceBalance = this.getBalance().add(amount);
        this.setBalance(newSourceBalance);
    }
}
