package com.github.kaydunov.dao.crud;

import com.github.kaydunov.dao.AccountPercentDao;
import com.github.kaydunov.dao.ConnectionManager;
import com.github.kaydunov.exception.DaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AccountPercentDaoTest {

    private static final double PERCENT = 1.0;

    private AccountPercentDao target;
    private Connection connectionMock;
    private PreparedStatement preparedStatementMock;

    @BeforeEach
    void setUp() throws SQLException {
        // Mocking Connection and PreparedStatement
        connectionMock = mock(Connection.class);
        preparedStatementMock = mock(PreparedStatement.class);

        // Use Mockito's static mocking to mock the static method `ConnectionManager.getConnection()`
        try (MockedStatic<ConnectionManager> mockedConnectionManager = mockStatic(ConnectionManager.class)) {
            mockedConnectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);

            // Stubbing connection.prepareStatement to return the mocked PreparedStatement
            when(connectionMock.prepareStatement(AccountPercentDao.SQL_CHARGE_PERCENTS)).thenReturn(preparedStatementMock);

            // Creating the instance of AccountPercentDao (this should pick up the mocked connection)
            target = new AccountPercentDao();
        }
    }

    @Test
    void testChargePercents_Success() throws SQLException {
        // Perform the method under test
        target.chargePercents(PERCENT);

        // Verify that the correct SQL query was prepared
        verify(connectionMock).prepareStatement(AccountPercentDao.SQL_CHARGE_PERCENTS);

        // Verify the percent was set as a parameter in the PreparedStatement
        verify(preparedStatementMock).setDouble(1, PERCENT);

        // Verify the statement was executed
        verify(preparedStatementMock).executeUpdate();
    }

    @Test
    void testChargePercents_ThrowsDaoExceptionOnSQLException() throws SQLException {
        // Simulate a SQLException when preparing the statement
        when(connectionMock.prepareStatement(AccountPercentDao.SQL_CHARGE_PERCENTS))
                .thenThrow(new SQLException("Test SQL Exception"));

        // Assert that the method throws DaoException
        assertThrows(DaoException.class, () -> target.chargePercents(PERCENT));
    }
}
