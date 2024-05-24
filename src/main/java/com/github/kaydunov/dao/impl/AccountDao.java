package com.github.kaydunov.dao.impl;

import com.github.kaydunov.dao.ConnectionManager;
import com.github.kaydunov.dao.CrudRepository;
import com.github.kaydunov.entity.Account;
import com.github.kaydunov.entity.Operation;
import com.github.kaydunov.exception.DaoException;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AccountDao implements CrudRepository<Account, Long> {

    private static final String SQL_CREATE = "INSERT INTO account (balance, bank_id, user_id) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM account WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM account";
    private static final String SQL_UPDATE = "UPDATE account SET balance = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM account WHERE id = ?";
    @Autowired
    OperationDao operationDao;

    private static Connection connection;

    static {
        try {
            connection = ConnectionManager.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Account save(Account account) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE)) {
            statement.setBigDecimal(1, account.getBalance());
            statement.setLong(2, account.getBankId());
            statement.setLong(3, account.getUserId());
            ResultSet resultSet = statement.executeQuery();
            account = mapResultSetToAccount(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return account;
    }

    @Override
    public Optional<Account> findById(Long id) {
        Optional<Account> account;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                account = Optional.of(mapResultSetToAccount(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return account;
    }

    @Override
    public List<Account> readAll() {
        List<Account> accounts = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Account account = mapResultSetToAccount(resultSet);
                    accounts.add(account);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return accounts;
    }

    @Override
    public void update(Account account) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setBigDecimal(1, account.getBalance());
            statement.setLong(2, account.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(Long id)  {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
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
        List<Operation> operations = operationDao.getOperationsByAccountId(id);
        account.setOperations(operations);
        return account;
    }
}
