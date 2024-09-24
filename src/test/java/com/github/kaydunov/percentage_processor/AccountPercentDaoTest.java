package com.github.kaydunov.percentage_processor;

import com.github.kaydunov.dao.ConnectionManager;
import com.github.kaydunov.exception.DaoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AccountPercentDaoTest {

    private final Connection connectionMock = mock(Connection.class, "connection");

    private AutoCloseable autoCloseableMocks;

    @InjectMocks()
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
        double percent = 1.0;

        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            autoCloseableMocks = MockitoAnnotations.openMocks(this);

            //when
            when(connectionMock.prepareStatement(any())).thenReturn(preparedStatementMock);

            //then
            target.chargePercents(percent);

            verify(preparedStatementMock).setDouble(1, percent);
            verify(preparedStatementMock).executeUpdate();
        }
    }

    @Test
    void chargePercents_When_Error() throws SQLException {
        //given
        double percent = 1.0;

        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            autoCloseableMocks = MockitoAnnotations.openMocks(this);

            //when
            when(connectionMock.prepareStatement(any())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeUpdate()).thenThrow(SQLException.class);

            //then
            final Throwable result = assertThrows(DaoException.class, () -> {
                target.chargePercents(percent);
            });

            verify(preparedStatementMock).setDouble(1, percent);
            verify(preparedStatementMock).executeUpdate();
        }
    }
}
