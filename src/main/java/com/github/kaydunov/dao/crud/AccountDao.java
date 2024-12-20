package com.github.kaydunov.dao.crud;

import com.github.kaydunov.dao.ConnectionManager;
import com.github.kaydunov.entity.Account;
import com.github.kaydunov.entity.Transaction;
import com.github.kaydunov.entity.TransactionType;
import com.github.kaydunov.exception.DaoException;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class AccountDao implements CrudRepository<Account, String> {

    public static final String SQL_CREATE = "INSERT INTO account (balance, currency, bank_id, user_id, is_saving_account) VALUES (?, ?, ?, ?, ?)";
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
            statement.setString(2, account.getCurrency());
            statement.setLong(3, account.getBankId());
            statement.setLong(4, account.getUserId());
            statement.setBoolean(5, account.isSavingAccount());
            ResultSet resultSet = statement.executeQuery();
            account = mapResultSetToAccount(resultSet);
            log.info(SQL_CREATE);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return account;
    }

    @Override
    public Optional<Account> findById(String id) {
        Optional<Account> account;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    account = Optional.of(mapResultSetToAccount(resultSet));
                } else {
                    throw new NotFoundException("Could not find account");
                }
            }
            log.info(SQL_SELECT_BY_ID + ", where id = " + id);
        } catch (SQLException | NotFoundException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return account;
    }

    @Override
    public List<Account> findAll() {
        return findAllAccordingSqlQuery(SQL_SELECT_ALL);
    }

    public List<Account> findAllSavingAccounts() {
        return findAllAccordingSqlQuery(SQL_SELECT_ALL_SAVING_ACCOUNTS);
    }

    private List<Account> findAllAccordingSqlQuery(String sqlQuery) {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery);
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

    @Override
    public void update(Account account) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_BALANCE)) {
            statement.setBigDecimal(1, account.getBalance());
            statement.setString(2, account.getId());
            statement.executeUpdate();
            log.info(SQL_UPDATE_BALANCE);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }



    public Transaction withdraw(BigDecimal amount, String accountSourceId) throws SQLException, NotFoundException {
        Account account = findById(accountSourceId).orElseThrow(() -> new NotFoundException("Account not found: " + accountSourceId));
        account.withdrawBalance(amount);

        Timestamp createdAt = Timestamp.from(Instant.now());
        Transaction transaction = new Transaction(amount, createdAt, accountSourceId, null, TransactionType.WITHDRAW, account.getCurrency());
        updateWithTransaction(account, transaction);
        return transaction;
    }

    public Transaction deposit(BigDecimal amount, String accountDestinationId) throws SQLException, NotFoundException {
        Account destinationAccount = findById(accountDestinationId).orElseThrow(() -> new NotFoundException("Account now found"));
        destinationAccount.depositBalance(amount);

        Timestamp createdAt = Timestamp.from(Instant.now());
        Transaction transaction = new Transaction(amount, createdAt, null, accountDestinationId, TransactionType.DEPOSIT, destinationAccount.getCurrency());
        updateWithTransaction(destinationAccount, transaction);
        return transaction;
    }


    public void updateWithTransaction(Account account, Transaction transaction) throws SQLException {
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
    public void deleteById(String id) {
        try (PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(SQL_DELETE_BY_ID)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    private Account mapResultSetToAccount(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        String id = resultSet.getString("id");
        account.setId(id);
        account.setBalance(resultSet.getBigDecimal("balance"));
        account.setCurrency(resultSet.getString("currency"));
        account.setBankId(resultSet.getLong("bank_id"));
        account.setUserId(resultSet.getLong("user_id"));
        account.setSavingAccount(resultSet.getBoolean("is_saving_account"));
        account.setCreatedAt(resultSet.getTimestamp("created_at"));
        List<Long> transactions = transactionDao.getTransactionsIdsByAccountId(id);
        account.setTransactionsIds(transactions);
        return account;
    }
}
