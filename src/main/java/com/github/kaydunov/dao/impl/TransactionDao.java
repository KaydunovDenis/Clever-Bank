package com.github.kaydunov.dao.impl;

import com.github.kaydunov.dao.ConnectionManager;
import com.github.kaydunov.dao.CrudRepository;
import com.github.kaydunov.entity.Transaction;
import com.github.kaydunov.entity.TransactionType;
import com.github.kaydunov.exception.DaoException;
import com.github.kaydunov.spring.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TransactionDao implements CrudRepository<Transaction, Long> {

    private static final String SQL_CREATE = "INSERT INTO transaction (amount, created_at, transaction_type_id, account_source_id, account_destination_id) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM transaction WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM transaction";
    private static final String SQL_SELECT_BY_ACCOUNT_ID = "SELECT * FROM transaction WHERE account_source_id = ? OR account_destination_id = ?";

    private static Connection connection = ConnectionManager.getConnection();

    @Override
    public Transaction create(Transaction transaction) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setBigDecimal(1, transaction.getAmount());
            statement.setTimestamp(2, transaction.getCreatedAt());
            statement.setInt(3, transaction.getTransactionType().ordinal() + 1); // Assuming the ordinal corresponds to the transaction_type_id
            statement.setLong(4, transaction.getAccountSourceId());
            statement.setLong(5, transaction.getAccountDestinationId());
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        transaction.setId(generatedKeys.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return transaction;
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        Transaction transaction = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                transaction = mapResultSetToOperation(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return Optional.ofNullable(transaction);
    }

    @Override
    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                transactions.add(mapResultSetToOperation(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return transactions;
    }

    @Override
    public void update(Transaction transaction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Long id) {
        throw new UnsupportedOperationException();
    }

    public List<Transaction> getOperationsByAccountId(Long accountId) {
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ACCOUNT_ID)) {
            statement.setLong(1, accountId);
            statement.setLong(2, accountId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                transactions.add(mapResultSetToOperation(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return transactions;
    }

    private Transaction mapResultSetToOperation(ResultSet resultSet) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(resultSet.getLong("id"));
        transaction.setAmount(resultSet.getBigDecimal("amount"));
        transaction.setCreatedAt(resultSet.getTimestamp("created_at"));
        transaction.setTransactionType(TransactionType.values()[resultSet.getInt("transaction_type_id") - 1]); // Assuming the transaction_type_id corresponds to the ordinal
        transaction.setAccountSourceId(resultSet.getLong("account_source_id"));
        transaction.setAccountDestinationId(resultSet.getLong("account_destination_id"));
        return transaction;
    }
}

