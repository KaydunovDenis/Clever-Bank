package com.github.kaydunov.dao.impl;

import com.github.kaydunov.dao.ConnectionManager;
import com.github.kaydunov.entity.Transaction;
import com.github.kaydunov.entity.TransactionType;
import com.github.kaydunov.exception.DaoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class TransactionDaoSapientGeneratedTest {

    @InjectMocks
    private TransactionDao target;

    private Connection connectionMock;

    private PreparedStatement preparedStatementMock;

    private ResultSet resultSetMock;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        connectionMock = mock(Connection.class);
        preparedStatementMock = mock(PreparedStatement.class);
        resultSetMock = mock(ResultSet.class);
        try (var mocked = mockStatic(ConnectionManager.class)) {
            mocked.when(ConnectionManager::getConnection).thenReturn(connectionMock);
        }
        when(connectionMock.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatementMock);
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
    }

    @AfterEach
    void tearDown() throws Exception {
        verifyNoMoreInteractions(connectionMock, preparedStatementMock, resultSetMock);
    }

    @Test
    void createWhenSuccessful() throws SQLException {
        when(preparedStatementMock.executeUpdate()).thenReturn(1);
        when(preparedStatementMock.getGeneratedKeys()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getLong(1)).thenReturn(1L);
        Transaction transaction = new Transaction(BigDecimal.TEN, new Timestamp(System.currentTimeMillis()), 1L, 2L, TransactionType.DEPOSIT);
        Transaction result = target.create(transaction);
        assertThat(result.getId(), is(1L));
        verify(preparedStatementMock).setBigDecimal(1, transaction.getAmount());
        verify(preparedStatementMock).setTimestamp(2, transaction.getCreatedAt());
        verify(preparedStatementMock).setInt(3, transaction.getTransactionType().ordinal() + 1);
        verify(preparedStatementMock).setLong(4, transaction.getSourceAccountId());
        verify(preparedStatementMock).setLong(5, transaction.getDestinationAccountId());
        verify(preparedStatementMock).executeUpdate();
        verify(preparedStatementMock).getGeneratedKeys();
        verify(resultSetMock).next();
        verify(resultSetMock).getLong(1);
    }

    @Test
    void createWhenSQLExceptionThrown() throws SQLException {
        when(preparedStatementMock.executeUpdate()).thenThrow(new SQLException("Test exception"));
        Transaction transaction = new Transaction(BigDecimal.TEN, new Timestamp(System.currentTimeMillis()), 1L, 2L, TransactionType.DEPOSIT);
        assertThrows(DaoException.class, () -> target.create(transaction));
    }

    @Test
    void findByIdWhenTransactionExists() throws SQLException {
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getLong("id")).thenReturn(1L);
        when(resultSetMock.getBigDecimal("amount")).thenReturn(BigDecimal.TEN);
        when(resultSetMock.getTimestamp("created_at")).thenReturn(new Timestamp(System.currentTimeMillis()));
        when(resultSetMock.getInt("transaction_type_id")).thenReturn(1);
        when(resultSetMock.getLong("account_source_id")).thenReturn(1L);
        when(resultSetMock.getLong("account_destination_id")).thenReturn(2L);
        Optional<Transaction> result = target.findById(1L);
        assertTrue(result.isPresent());
        assertThat(result.get().getId(), is(1L));
        verify(preparedStatementMock).setLong(1, 1L);
        verify(preparedStatementMock).executeQuery();
        verify(resultSetMock).next();
    }

    @Test
    void findByIdWhenTransactionDoesNotExist() throws SQLException {
        when(resultSetMock.next()).thenReturn(false);
        Optional<Transaction> result = target.findById(1L);
        assertFalse(result.isPresent());
        verify(preparedStatementMock).setLong(1, 1L);
        verify(preparedStatementMock).executeQuery();
        verify(resultSetMock).next();
    }

    @Test
    void findAllWhenTransactionsExist() throws SQLException {
        when(resultSetMock.next()).thenReturn(true, true, false);
        when(resultSetMock.getLong("id")).thenReturn(1L, 2L);
        when(resultSetMock.getBigDecimal("amount")).thenReturn(BigDecimal.TEN, BigDecimal.ONE);
        when(resultSetMock.getTimestamp("created_at")).thenReturn(new Timestamp(System.currentTimeMillis()));
        when(resultSetMock.getInt("transaction_type_id")).thenReturn(1, 2);
        when(resultSetMock.getLong("account_source_id")).thenReturn(1L, 2L);
        when(resultSetMock.getLong("account_destination_id")).thenReturn(2L, 3L);
        List<Transaction> result = target.findAll();
        assertThat(result, hasSize(2));
        verify(preparedStatementMock).executeQuery();
        verify(resultSetMock, times(3)).next();
    }

    @Test
    void updateThrowsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> target.update(new Transaction()));
    }

    @Test
    void deleteByIdThrowsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> target.deleteById(1L));
    }

    @Test
    void getTransactionsByAccountIdWhenTransactionsExist() throws SQLException {
        when(resultSetMock.next()).thenReturn(true, true, false);
        when(resultSetMock.getLong("id")).thenReturn(1L, 2L);
        when(resultSetMock.getBigDecimal("amount")).thenReturn(BigDecimal.TEN, BigDecimal.ONE);
        when(resultSetMock.getTimestamp("created_at")).thenReturn(new Timestamp(System.currentTimeMillis()));
        when(resultSetMock.getInt("transaction_type_id")).thenReturn(1, 2);
        when(resultSetMock.getLong("account_source_id")).thenReturn(1L, 2L);
        when(resultSetMock.getLong("account_destination_id")).thenReturn(2L, 3L);
        List<Transaction> result = target.getTransactionsByAccountId(1L);
        assertThat(result, hasSize(2));
        verify(preparedStatementMock).setLong(1, 1L);
        verify(preparedStatementMock).setLong(2, 1L);
        verify(preparedStatementMock).executeQuery();
        verify(resultSetMock, times(3)).next();
    }

    @ParameterizedTest
    @CsvSource({ "1,2", "3,4", "5,6" })
    void getTransactionsIdsByAccountIdWhenTransactionsExist(long accountId, int expectedSize) throws SQLException {
        when(resultSetMock.next()).thenReturn(true, true, false);
        when(resultSetMock.getLong("id")).thenReturn(1L, 2L);
        List<Long> result = target.getTransactionsIdsByAccountId(accountId);
        assertThat(result, hasSize(expectedSize));
        verify(preparedStatementMock).setLong(1, accountId);
        verify(preparedStatementMock).setLong(2, accountId);
        verify(preparedStatementMock).executeQuery();
        verify(resultSetMock, times(3)).next();
    }
}
