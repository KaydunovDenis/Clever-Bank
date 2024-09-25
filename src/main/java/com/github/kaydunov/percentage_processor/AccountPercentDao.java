package com.github.kaydunov.percentage_processor;

import com.github.kaydunov.dao.ConnectionManager;
import com.github.kaydunov.exception.DaoException;
import com.github.kaydunov.spring.Component;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
@Component
public class AccountPercentDao {

    public static final String SQL_CHARGE_PERCENTS = """
            UPDATE account SET balance = balance + (? * balance/100)
                WHERE is_saving_account = true AND balance > 0
            """;

    private final Connection connection;

    // Default constructor uses ConnectionManager for production
    public AccountPercentDao() {
        try {
            this.connection = ConnectionManager.getConnection();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void chargePercents(double percent) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_CHARGE_PERCENTS)) {
            statement.setDouble(1, percent);
            statement.executeUpdate();
            log.info("Percents accrued on the accounts with savings status.");
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }
}
