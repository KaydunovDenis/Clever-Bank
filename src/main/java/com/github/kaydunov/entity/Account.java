package com.github.kaydunov.entity;

import com.github.kaydunov.exception.InsufficientFundsException;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Account {
    String id;
    BigDecimal balance;
    String currency;
    Long bankId;
    Long userId;
    boolean isSavingAccount;
    List<Long> transactionsIds;
    Timestamp createdAt;

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

    //todo make a private
    public void setValueAsBalance(String balance) {
        this.balance = new BigDecimal(balance);
    }

    public LocalDate getCreatedAtAsLocalDate() {
        return createdAt.toLocalDateTime().toLocalDate();
    }
}
