package com.github.kaydunov.dao;

import com.github.kaydunov.entity.Account;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    private Connection connection;

    public AccountDao() {
        try {
            this.connection = ConnectionManager.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (id, number, balance) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, account.getId());
            statement.setLong(2, account.getNumber());
            statement.setBigDecimal(3, account.getBalance());
            statement.executeUpdate();
        }
    }

    public Account findById(Long id) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToAccount(resultSet);
                } else {
                    return null; // Account with this id not found
                }
            }
        }
    }

    public List<Account> findAll() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Account account = mapResultSetToAccount(resultSet);
                    accounts.add(account);
                }
            }
        }
        return accounts;
    }

    public void update(Account account) throws SQLException {
        String sql = "UPDATE accounts SET number = ?, balance = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, account.getNumber());
            statement.setBigDecimal(2, account.getBalance());
            statement.setLong(3, account.getId());
            statement.executeUpdate();
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM accounts WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    private Account mapResultSetToAccount(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Long number = resultSet.getLong("number");
        BigDecimal balance = resultSet.getBigDecimal("balance");
        return new Account(id, number, balance);
    }
}
