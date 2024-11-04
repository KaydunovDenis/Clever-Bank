package com.github.kaydunov.dao.crud;

import com.github.kaydunov.dao.AccountPercentDao;
import com.github.kaydunov.dao.ConnectionManager;
import com.github.kaydunov.exception.DaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class AccountPercentDaoTest {

    private static final double PERCENT = 1.0;
    @Mock
    private Connection connectionMock;
    @Mock
    private PreparedStatement preparedStatementMock;
    @InjectMocks
    private AccountPercentDao target;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }


    @Test
    void testChargePercents_Success() throws SQLException {
        try (MockedStatic<ConnectionManager> mockedConnectionManager = mockStatic(ConnectionManager.class)) {
            mockedConnectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            when(connectionMock.prepareStatement(AccountPercentDao.SQL_CHARGE_PERCENTS)).thenReturn(preparedStatementMock);

            //Act
            target.chargePercents(PERCENT);

            //Asserts
            verify(connectionMock).prepareStatement(AccountPercentDao.SQL_CHARGE_PERCENTS);
            verify(preparedStatementMock).setDouble(1, PERCENT);
            verify(preparedStatementMock).executeUpdate();
        }
    }

    @Test
    void testChargePercents_ThrowsDaoExceptionOnSQLException() throws SQLException {
        try (MockedStatic<ConnectionManager> mockedConnectionManager = mockStatic(ConnectionManager.class)) {
            mockedConnectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            when(connectionMock.prepareStatement(AccountPercentDao.SQL_CHARGE_PERCENTS)).thenReturn(preparedStatementMock);
            doThrow(new SQLException()).when(preparedStatementMock).setDouble(1, PERCENT);

            // Assert that the method throws DaoException
            assertThrows(DaoException.class, () -> target.chargePercents(PERCENT));
        }
    }
}
