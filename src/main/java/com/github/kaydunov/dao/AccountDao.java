package com.github.kaydunov.dao;

import com.github.kaydunov.entity.Account;
import com.github.kaydunov.entity.Bank;
import com.github.kaydunov.entity.User;
import com.github.kaydunov.exception.DaoException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDao implements CrudRepository<Account, Long> {
    private Connection connection;
    private BankDao bankDao;
    private UserDao userDao;

    public AccountDao(BankDao bankDao, UserDao userDao) {
        try {
            this.connection = ConnectionManager.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.bankDao = bankDao;
        this.userDao = userDao;
    }


    @Override
    public Account save(Account account) {
        final String sql = "INSERT INTO account (balance, bank_id, user_id) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, account.getBalance());
            statement.setLong(2, account.getBank().getId());
            statement.setLong(3, account.getUser().getId());
            ResultSet resultSet = statement.executeQuery();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return  new Account();
    }

    public Optional<Account> findById(Long id) {
        Optional<Account> account;
        final String sql = "SELECT * FROM account WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                account = mapResultSetToAccount(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return account;

    }

    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        final String sql = "SELECT * FROM account";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Account account = mapResultSetToAccount(resultSet).get();
                    accounts.add(account);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return accounts;
    }

    public void update(Account account) {
        final String sql = "UPDATE account SET balance = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, account.getBalance());
            statement.setLong(2, account.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(Long aLong) {

    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM account WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    private Optional<Account> mapResultSetToAccount(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        BigDecimal balance = resultSet.getBigDecimal("balance");
        Long bankId = resultSet.getLong("bank_id");
        Bank bank = bankDao.findById(bankId).get();
        Long userId = resultSet.getLong("user_id");
        User user = userDao.findById(userId).get();
        return Optional.of(new Account());//todo builder
    }
}
