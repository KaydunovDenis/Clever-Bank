package com.github.kaydunov.dao.impl;

import com.github.kaydunov.dao.ConnectionManager;
import com.github.kaydunov.dao.CrudRepository;
import com.github.kaydunov.entity.User;
import com.github.kaydunov.exception.DaoException;
import com.github.kaydunov.spring.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserDao implements CrudRepository<User, Long> {

    private static final String SQL_CREATE = "INSERT INTO user_ (name, email) VALUES (?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM user_ WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM user_";
    private static final String SQL_UPDATE = "UPDATE user_ SET name = ?, email = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM user_ WHERE id = ?";

    private static Connection connection = ConnectionManager.getConnection();

    @Override
    public User create(User user) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = mapResultSetToUser(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return users;
    }

    @Override
    public void update(User user) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setLong(3, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        return user;
    }
}

