package com.github.kaydunov.dao.impl;

import com.github.kaydunov.dao.ConnectionManager;
import com.github.kaydunov.dao.CrudRepository;
import com.github.kaydunov.entity.Account;
import com.github.kaydunov.entity.Transaction;
import com.github.kaydunov.entity.TransactionType;
import com.github.kaydunov.exception.DaoException;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class AccountDao implements CrudRepository<Account, Long> {

    public static final String SQL_CREATE = "INSERT INTO account (balance, bank_id, user_id, is_saving_account) VALUES (?, ?, ?, ?)";
    public static final String SQL_SELECT_BY_ID = "SELECT * FROM account WHERE id = ?";
    public static final String SQL_SELECT_ALL = "SELECT * FROM account ORDER BY id";
    public static final String SQL_UPDATE_BALANCE = "UPDATE account SET balance = ? WHERE id = ?";
    public static final String SQL_DELETE_BY_ID = "DELETE FROM account WHERE id = ?";

    public static final String SQL_SELECT_ALL_SAVING_ACCOUNTS = "SELECT * FROM account WHERE is_saving_account = TRUE";
    @Autowired
    private TransactionDao transactionDao;

    @Override
    public Account create(Account account) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE)) {
            statement.setBigDecimal(1, account.getBalance());
            statement.setLong(2, account.getBankId());
            statement.setLong(3, account.getUserId());
            statement.setBoolean(4, account.isSavingAccount());
            ResultSet resultSet = statement.executeQuery();
            account = mapResultSetToAccount(resultSet);
            log.info(SQL_CREATE);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return account;
    }

    @Override
    public Optional<Account> findById(Long id) {
        Optional<Account> account;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    account = Optional.of(mapResultSetToAccount(resultSet));
                } else  account = Optional.empty();
            }
            log.info(SQL_SELECT_BY_ID);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return account;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Account account = mapResultSetToAccount(resultSet);
                accounts.add(account);
            }
            log.info(SQL_SELECT_ALL);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return accounts;
    }

    public List<Account> findAllSavingAccounts() {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_SAVING_ACCOUNTS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Account account = mapResultSetToAccount(resultSet);
                    accounts.add(account);
                }
            }
            log.info(SQL_SELECT_ALL_SAVING_ACCOUNTS);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return accounts;
    }

    @Override
    public void update(Account account) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_BALANCE)) {
            statement.setBigDecimal(1, account.getBalance());
            statement.setLong(2, account.getId());
            statement.executeUpdate();
            log.info(SQL_UPDATE_BALANCE);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public void transfer(BigDecimal amount, Long accountSourceId, Long accountDestinationId) throws SQLException {
        Timestamp createdAt = Timestamp.from(Instant.now());

        Account sourceAccount = findById(accountSourceId).get();
        sourceAccount.withdrawBalance(amount);
        Transaction withdrawTransaction = new Transaction(amount, createdAt, accountSourceId, accountDestinationId, TransactionType.WITHDRAW);

        Account destinationAccount = findById(accountDestinationId).get();
        destinationAccount.depositBalance(amount);
        Transaction depositTransaction = new Transaction(amount, createdAt, accountDestinationId, accountSourceId, TransactionType.DEPOSIT);

        Connection connection = ConnectionManager.getConnection();
        try {
            connection.setAutoCommit(false);

            updateWithTransaction(sourceAccount, withdrawTransaction);
            updateWithTransaction(destinationAccount, depositTransaction);

            connection.commit();
            log.info("Transfer %s from %s account to %s account was successfully completed.", amount, sourceAccount.getId(), destinationAccount.getId());
        } catch (SQLException e) {
            connection.rollback();
            log.info("Transfer was automatically rolled back. Reason: {}", e.getMessage());
            throw new SQLTransactionRollbackException(e.getMessage(), e);
        }
    }

    public void withdraw(BigDecimal amount, Long accountSourceId) throws SQLException {
        Account sourceAccount = findById(accountSourceId).get();
        sourceAccount.withdrawBalance(amount);

        Timestamp createdAt = Timestamp.from(Instant.now());
        Transaction transaction = new Transaction(amount, createdAt, accountSourceId, null, TransactionType.WITHDRAW);
        updateWithTransaction(sourceAccount, transaction);
    }

    public void deposit(BigDecimal amount, Long accountDestinationId) throws SQLException {
        Account destinationAccount = findById(accountDestinationId).get();
        destinationAccount.depositBalance(amount);

        Timestamp createdAt = Timestamp.from(Instant.now());
        Transaction transaction = new Transaction(amount, createdAt, null, accountDestinationId, TransactionType.DEPOSIT);
        updateWithTransaction(destinationAccount, transaction);
    }


    private void updateWithTransaction(Account account, Transaction transaction) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        try {
            connection.setAutoCommit(false);
            transactionDao.create(transaction);
            update(account);
            connection.commit();
            log.info(transaction + "was successfully completed");
        } catch (DaoException e) {
            connection.rollback();
            log.info(transaction + "was automatically rolled back. Reason: " + e.getMessage());
            throw new SQLTransactionRollbackException(e.getMessage(), e);
        } finally {
            connection.setAutoCommit(true);
        }
        connection.close();
    }

    @Override
    public void deleteById(Long id) {
        try (PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(SQL_DELETE_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    private Account mapResultSetToAccount(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        long id = resultSet.getLong("id");
        account.setId(id);
        account.setBalance(resultSet.getBigDecimal("balance"));
        account.setBankId(resultSet.getLong("bank_id"));
        account.setUserId(resultSet.getLong("user_id"));
        List<Long> transactions = transactionDao.getTransactionsIdsByAccountId(id);
        account.setTransactionsIds(transactions);
        return account;
    }
}
