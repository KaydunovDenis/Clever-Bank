package com.github.kaydunov.dao.crud;

import com.github.kaydunov.dao.ConnectionManager;
import com.github.kaydunov.entity.Bank;
import com.github.kaydunov.exception.DaoException;
import com.github.kaydunov.spring.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BankDao implements CrudRepository<Bank, Long> {

    public static final String SQL_CREATE = "INSERT INTO bank (name) VALUES (?)";
    public static final String SQL_SELECT_BY_ID = "SELECT * FROM bank WHERE id = ?";
    public static final String SQL_SELECT_ALL = "SELECT * FROM bank";
    public static final String SQL_UPDATE = "UPDATE bank SET name = ? WHERE id = ?";
    public static final String SQL_DELETE_BY_ID = "DELETE FROM bank WHERE id = ?";

    @Override
    public Bank create(Bank bank) {
        try (PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, bank.getName());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating bank failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    bank.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating bank failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return bank;
    }

    @Override
    public Optional<Bank> findById(Long id) {
        Bank bank = null;
        try (PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                bank = mapResultSetToBank(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return Optional.ofNullable(bank);
    }

    @Override
    public List<Bank> findAll() {
        List<Bank> banks = new ArrayList<>();
        try (PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(SQL_SELECT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                banks.add(mapResultSetToBank(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return banks;
    }

    @Override
    public void update(Bank bank) {
        try (PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(SQL_UPDATE)) {
            statement.setString(1, bank.getName());
            statement.setLong(2, bank.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
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

    private Bank mapResultSetToBank(ResultSet resultSet) throws SQLException {
        Bank bank = new Bank();
        bank.setId(resultSet.getLong("id"));
        bank.setName(resultSet.getString("name"));
        return bank;
    }
}

