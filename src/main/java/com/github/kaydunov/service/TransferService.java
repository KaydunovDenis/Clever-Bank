package com.github.kaydunov.service;

import com.github.kaydunov.dao.ConnectionManager;
import com.github.kaydunov.dao.crud.AccountDao;
import com.github.kaydunov.entity.Account;
import com.github.kaydunov.entity.Transaction;
import com.github.kaydunov.entity.TransactionType;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTransactionRollbackException;
import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
@Component
public class TransferService {

    @Autowired
    private AccountDao accountDao;

    public Transaction transfer(BigDecimal amount, String accountSourceId, String accountDestinationId) throws SQLException {
        final Timestamp createdAt = Timestamp.from(Instant.now());

        Account sourceAccount = accountDao.findById(accountSourceId).get();
        sourceAccount.withdrawBalance(amount);
        String sourceCurrency = sourceAccount.getCurrency();
        Transaction withdrawTransaction = new Transaction(amount, createdAt, accountSourceId, accountDestinationId, TransactionType.WITHDRAW, sourceCurrency);

        Account destinationAccount = accountDao.findById(accountDestinationId).get();
        //TODO convert sourceCurrency, add table currency, add entity currency, add DAO and service
        destinationAccount.depositBalance(amount);
        String destinationCurrency = destinationAccount.getCurrency();
        Transaction depositTransaction = new Transaction(amount, createdAt, accountDestinationId, accountSourceId, TransactionType.DEPOSIT,destinationCurrency);

        Connection connection = ConnectionManager.getConnection();
        try {
            connection.setAutoCommit(false);

            accountDao.updateWithTransaction(sourceAccount, withdrawTransaction);
            accountDao.updateWithTransaction(destinationAccount, depositTransaction);

            connection.commit();
            log.info("Transfer %s from %f account to %f account was successfully completed.", amount, accountSourceId, accountDestinationId);
        } catch (SQLException e) {
            connection.rollback();
            log.info("Transfer was automatically rolled back. Reason: {}", e.getMessage());
            throw new SQLTransactionRollbackException(e.getMessage(), e);
        }
        //todo return Transfer transaction
        String currency = sourceAccount.getCurrency();
        return new Transaction(amount, createdAt, accountSourceId, accountDestinationId, TransactionType.WITHDRAW, currency);
    }
}
