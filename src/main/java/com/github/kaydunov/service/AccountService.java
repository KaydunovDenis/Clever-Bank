package com.github.kaydunov.service;

import com.github.kaydunov.dao.impl.AccountDao;
import com.github.kaydunov.entity.Account;
import com.github.kaydunov.entity.Operation;
import com.github.kaydunov.exception.InsufficientFundsException;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class AccountService {
    @Autowired
    private AccountDao accountDao;


    private void deposit(Operation operation) {
        Long accountSourceId = operation.getAccountSourceId();
        BigDecimal amount = operation.getAmount();

        Account sourceAccount = accountDao.findById(accountSourceId).get();
        BigDecimal newSourceBalance = sourceAccount.getBalance().add(amount);
        sourceAccount.setBalance(newSourceBalance);

        Account destinationAccount = accountDao.findById(accountSourceId).get();
        BigDecimal newDestinationBalance = destinationAccount.getBalance().add(amount);
        destinationAccount.setBalance(newDestinationBalance);

        accountDao.update(sourceAccount);
        accountDao.update(destinationAccount);
    }

    private void withdraw(Operation operation) throws InsufficientFundsException {
        // Logic to withdraw funds from the account

//        Account account = accountDao.findById(accountId).get();
//        BigDecimal newBalance = account.getBalance().subtract(amount);
//        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
//            throw new InsufficientFundsException("Insufficient funds in account");
//        }
//        account.setBalance(newBalance);
//        accountDao.update(account);
    }

    public void performRequestOperation(Operation operation) throws SQLException, InsufficientFundsException {
        switch (operation.getOperationType()) {
            case DEPOSIT:
                deposit(operation);
                break;
            case WITHDRAW:
                withdraw(operation);
                break;
            default:
                throw new IllegalArgumentException("Invalid operation type");
        }
    }
}
