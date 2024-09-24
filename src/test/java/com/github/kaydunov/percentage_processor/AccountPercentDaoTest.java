package com.github.kaydunov.percentage_processor;

import com.github.kaydunov.dao.ConnectionManager;
import com.github.kaydunov.exception.DaoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AccountPercentDaoTest {

    private static final double PERCENT = 1.0;
    private final Connection connectionMock = mock(Connection.class, "connection");

    private AutoCloseable autoCloseableMocks;

    @InjectMocks
    private AccountPercentDao target;
    @Mock
    private PreparedStatement preparedStatementMock;

    @AfterEach()
    public void afterTest() throws Exception {
        if (autoCloseableMocks != null)
            autoCloseableMocks.close();
    }

    @Test
    void chargePercents() throws SQLException {
        //given

        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            autoCloseableMocks = MockitoAnnotations.openMocks(this);

            //when
            when(connectionMock.prepareStatement(any())).thenReturn(preparedStatementMock);

            //then
            target.chargePercents(PERCENT);

            verify(preparedStatementMock).setDouble(1, PERCENT);
            verify(preparedStatementMock).executeUpdate();
        }
    }

    @Test
    void chargePercents_When_Error() throws SQLException {
        //given

        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            autoCloseableMocks = MockitoAnnotations.openMocks(this);

            //when
            when(connectionMock.prepareStatement(any())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeUpdate()).thenThrow(SQLException.class);

            //then
            assertThrows(DaoException.class, () -> {
                target.chargePercents(PERCENT);
            });

            verify(preparedStatementMock).setDouble(1, PERCENT);
            verify(preparedStatementMock).executeUpdate();
        }
    }

    @Disabled("MANUAL")
    @Test
    void manual_test() throws DaoException {
        target = new AccountPercentDao();
        target.chargePercents(PERCENT);

    }
}
