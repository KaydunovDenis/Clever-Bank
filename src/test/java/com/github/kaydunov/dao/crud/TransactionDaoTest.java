package com.github.kaydunov.dao.crud;

import com.github.kaydunov.dao.ConnectionManager;
import com.github.kaydunov.entity.Transaction;
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

import static com.github.kaydunov.entity.TransactionType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class TransactionDaoTest {

    private final String accountSource1Id = "1L";
    private final String accountSource2Id = "2L";
    private final String accountDestination1Id = "3L";
    private final String accountDestination2Id = "4L";
    private final long transaction1Id = 5L;
    private final long transaction2Id = 6L;
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
    void tearDown() {
        verifyNoMoreInteractions(connectionMock, preparedStatementMock, resultSetMock);
    }

    @Test
    void createWhenSuccessful() throws SQLException {
        when(preparedStatementMock.executeUpdate()).thenReturn(1);
        when(preparedStatementMock.getGeneratedKeys()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getString(1)).thenReturn(accountSource1Id);
        Transaction transaction = new Transaction(BigDecimal.TEN, new Timestamp(System.currentTimeMillis()), accountSource1Id, accountDestination1Id, DEPOSIT);
        Transaction result = target.create(transaction);
        assertThat(result.getId(), is(transaction1Id));
        verify(preparedStatementMock).setBigDecimal(1, transaction.getAmount());
        verify(preparedStatementMock).setTimestamp(2, transaction.getCreatedAt());
        verify(preparedStatementMock).setInt(3, transaction.getTransactionType().ordinal() + 1);
        verify(preparedStatementMock).setString(4, transaction.getSourceAccountId());
        verify(preparedStatementMock).setString(5, transaction.getDestinationAccountId());
        verify(preparedStatementMock).executeUpdate();
        verify(preparedStatementMock).getGeneratedKeys();
        verify(resultSetMock).next();
        verify(resultSetMock).getLong(1);
    }

    @Test
    void createWhenSQLExceptionThrown() throws SQLException {
        when(preparedStatementMock.executeUpdate()).thenThrow(new SQLException("Test exception"));
        Transaction transaction = new Transaction(BigDecimal.TEN, new Timestamp(System.currentTimeMillis()), "1L", "2L", DEPOSIT);
        assertThrows(DaoException.class, () -> target.create(transaction));
    }

    @Test
    void findByIdWhenTransactionExists() throws SQLException {
        when(resultSetMock.next()).thenReturn(true);
        final String accountSourceId = "2L";
        final String accountDestinationId = "3L";
        when(resultSetMock.getLong("id")).thenReturn(transaction1Id);
        when(resultSetMock.getBigDecimal("amount")).thenReturn(BigDecimal.TEN);
        when(resultSetMock.getTimestamp("created_at")).thenReturn(new Timestamp(System.currentTimeMillis()));
        when(resultSetMock.getInt("transaction_type_id")).thenReturn(1);
        when(resultSetMock.getString("account_source_id")).thenReturn(accountSourceId);
        when(resultSetMock.getString("account_destination_id")).thenReturn(accountDestinationId);
        Optional<Transaction> result = target.findById(transaction1Id);
        assertTrue(result.isPresent());
        assertThat(result.get().getId(), is(transaction1Id));
        verify(preparedStatementMock).setLong(1, transaction1Id);
        verify(preparedStatementMock).executeQuery();
        verify(resultSetMock).next();
    }

    @Test
    void findByIdWhenTransactionDoesNotExist() throws SQLException {
        when(resultSetMock.next()).thenReturn(false);
        Optional<Transaction> result = target.findById(transaction1Id);
        assertFalse(result.isPresent());
        verify(preparedStatementMock).setLong(1, transaction1Id);
        verify(preparedStatementMock).executeQuery();
        verify(resultSetMock).next();
    }

    @Test
    void findAllWhenTransactionsExist() throws SQLException {
        when(resultSetMock.next()).thenReturn(true, true, false);
        when(resultSetMock.getLong("id")).thenReturn(transaction1Id, transaction2Id);
        when(resultSetMock.getBigDecimal("amount")).thenReturn(BigDecimal.TEN, BigDecimal.ONE);
        when(resultSetMock.getTimestamp("created_at")).thenReturn(new Timestamp(System.currentTimeMillis()));
        when(resultSetMock.getInt("transaction_type_id")).thenReturn(1, 2);
        when(resultSetMock.getString("account_source_id")).thenReturn(accountSource1Id, accountSource2Id);
        when(resultSetMock.getString("account_destination_id")).thenReturn(accountDestination1Id, accountDestination2Id);
        List<Transaction> result = target.findAll();
        assertThat(result, hasSize(2));
        verify(preparedStatementMock).executeQuery();
        verify(resultSetMock, times(3)).next();
    }

    @Test
    void updateThrowsUnsupportedOperationException() {
        Transaction transaction = new Transaction();
        assertThrows(UnsupportedOperationException.class, () -> target.update(transaction));
    }

    @Test
    void deleteByIdThrowsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> target.deleteById(transaction1Id));
    }

    @Test
    void getTransactionsByAccountIdWhenTransactionsExist() throws SQLException {
        //When
        //Generate two transactions
        when(resultSetMock.next()).thenReturn(true, true, false);
        when(resultSetMock.getLong("id")).thenReturn(transaction1Id, transaction2Id);
        when(resultSetMock.getBigDecimal("amount")).thenReturn(BigDecimal.TEN, BigDecimal.ONE);
        when(resultSetMock.getTimestamp("created_at")).thenReturn(new Timestamp(System.currentTimeMillis()));
        when(resultSetMock.getInt("transaction_type_id")).thenReturn(WITHDRAW.ordinal(), DEPOSIT.ordinal());
        when(resultSetMock.getString("account_source_id")).thenReturn(accountSource1Id, accountSource1Id);
        when(resultSetMock.getString("account_destination_id")).thenReturn(accountSource1Id, accountDestination1Id);

        //Action
        List<Transaction> result = target.getTransactionsByAccountId(accountSource1Id);

        //Assert
        assertThat(result, hasSize(2));
        verify(preparedStatementMock).setString(1, accountSource1Id);
        verify(preparedStatementMock).setString(2, accountSource1Id);
        verify(preparedStatementMock).executeQuery();
        verify(resultSetMock, times(3)).next();
    }

    @ParameterizedTest
    @CsvSource({ "1,2", "3,4", "5,6" })
    void getTransactionsIdsByAccountId_When_TransactionsExist(String accountId, int expectedSize) throws SQLException {
        when(resultSetMock.next()).thenReturn(true, true, false);
        when(resultSetMock.getLong("id")).thenReturn(transaction1Id, transaction2Id);
        List<Long> result = target.getTransactionsIdsByAccountId(accountId);
        assertThat(result, hasSize(expectedSize));
        verify(preparedStatementMock).setString(1, accountId);
        verify(preparedStatementMock).setString(2, accountId);
        verify(preparedStatementMock).executeQuery();
        verify(resultSetMock, times(3)).next();
    }
}