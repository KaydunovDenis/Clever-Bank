package com.github.kaydunov.entity;

import com.github.kaydunov.exception.InsufficientFundsException;
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
    private List<Transaction> transactions;
    private boolean isSavingAccount;

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
