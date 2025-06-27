package com.github.kaydunov.exception;

import java.sql.SQLException;

public class DatabaseConfigurationException extends RuntimeException {
    public DatabaseConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseConfigurationException(SQLException e) {
        super(e);
    }
}
