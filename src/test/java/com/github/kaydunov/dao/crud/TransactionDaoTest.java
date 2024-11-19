package com.github.kaydunov.dao.crud;

import com.github.kaydunov.dao.ConnectionManager;
import com.github.kaydunov.entity.Transaction;
import com.github.kaydunov.exception.DaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

import static com.github.kaydunov.entity.TransactionType.DEPOSIT;
import static com.github.kaydunov.entity.TransactionType.WITHDRAW;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
public class TransactionDaoTest {

    private static final String ACCOUNT_SOURCE_1_ID = "1L";
    private static final String ACCOUNT_SOURCE_2_ID = "2L";
    private static final String ACCOUNT_DESTINATION_1_ID = "3L";
    private static final String ACCOUNT_DESTINATION_2_ID = "4L";
    private static final long TRANSACTION_1_ID = 5L;
    private static final long TRANSACTION_2_ID = 6L;
    @Mock
    private Connection connectionMock;
    @Mock
    private PreparedStatement preparedStatementMock;
    @Mock
    private ResultSet resultSetMock;
    @InjectMocks
    private TransactionDao target;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void createWhenSuccessful() throws SQLException {
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            //Arrange
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            when(connectionMock.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
            when(resultSetMock.next()).thenReturn(true);
            when(preparedStatementMock.executeUpdate()).thenReturn(1);
            when(preparedStatementMock.getGeneratedKeys()).thenReturn(resultSetMock);
            when(resultSetMock.next()).thenReturn(true);
            when(resultSetMock.getString(1)).thenReturn(ACCOUNT_SOURCE_1_ID);
            Transaction transaction = new Transaction(BigDecimal.TEN, new Timestamp(System.currentTimeMillis()), ACCOUNT_SOURCE_1_ID, ACCOUNT_DESTINATION_1_ID, DEPOSIT, "BYN");
            //Act
            Transaction result = target.create(transaction);
            //Assert
            assertEquals(result.getId(), 0L);
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
    }

    @Test
    void create_whenThrowSQLException() throws SQLException {
        when(preparedStatementMock.executeUpdate()).thenThrow(new SQLException("Test exception"));
        Transaction transaction = new Transaction(BigDecimal.TEN, new Timestamp(System.currentTimeMillis()), "1L", "2L", DEPOSIT, "BYN");
        assertThrows(DaoException.class, () -> target.create(transaction));
    }

    @Test
    void findByIdWhenTransactionExists() throws SQLException {
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            //Arrange Statement(s)
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
            when(resultSetMock.next()).thenReturn(true);
            final String accountSourceId = "2L";
            final String accountDestinationId = "3L";
            when(resultSetMock.getLong("id")).thenReturn(TRANSACTION_1_ID);
            when(resultSetMock.getBigDecimal("amount")).thenReturn(BigDecimal.TEN);
            when(resultSetMock.getTimestamp("created_at")).thenReturn(new Timestamp(System.currentTimeMillis()));
            when(resultSetMock.getInt("transaction_type_id")).thenReturn(1);
            when(resultSetMock.getString("account_source_id")).thenReturn(accountSourceId);
            when(resultSetMock.getString("account_destination_id")).thenReturn(accountDestinationId);
            //Act
            Optional<Transaction> result = target.findById(TRANSACTION_1_ID);
            //Arrange
            assertTrue(result.isPresent());
            assertThat(result.get().getId(), is(TRANSACTION_1_ID));
            verify(preparedStatementMock).setLong(1, TRANSACTION_1_ID);
            verify(preparedStatementMock).executeQuery();
            verify(resultSetMock).next();
        }
    }

    @Test
    void findByIdWhenTransactionDoesNotExist() throws SQLException {
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            //Arrange Statement(s)
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
            when(resultSetMock.next()).thenReturn(false);
            Optional<Transaction> result = target.findById(TRANSACTION_1_ID);
            assertFalse(result.isPresent());
            verify(preparedStatementMock).setLong(1, TRANSACTION_1_ID);
            verify(preparedStatementMock).executeQuery();
            verify(resultSetMock).next();
        }
    }

    @Test
    void findAllWhenTransactionsExist() throws SQLException {
        when(resultSetMock.next()).thenReturn(true, true, false);
        when(resultSetMock.getLong("id")).thenReturn(TRANSACTION_1_ID, TRANSACTION_2_ID);
        when(resultSetMock.getBigDecimal("amount")).thenReturn(BigDecimal.TEN, BigDecimal.ONE);
        when(resultSetMock.getTimestamp("created_at")).thenReturn(new Timestamp(System.currentTimeMillis()));
        when(resultSetMock.getInt("transaction_type_id")).thenReturn(1, 2);
        when(resultSetMock.getString("account_source_id")).thenReturn(ACCOUNT_SOURCE_1_ID, ACCOUNT_SOURCE_2_ID);
        when(resultSetMock.getString("account_destination_id")).thenReturn(ACCOUNT_DESTINATION_1_ID, ACCOUNT_DESTINATION_2_ID);
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
        assertThrows(UnsupportedOperationException.class, () -> target.deleteById(TRANSACTION_1_ID));
    }

    @Test
    void getTransactionsByAccountIdWhenTransactionsExist() throws SQLException {
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            //Arrange Statement(s)
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
            //Generate two transactions
            when(resultSetMock.next()).thenReturn(true, true, false);
            when(resultSetMock.getLong("id")).thenReturn(TRANSACTION_1_ID, TRANSACTION_2_ID);
            when(resultSetMock.getBigDecimal("amount")).thenReturn(BigDecimal.TEN, BigDecimal.ONE);
            when(resultSetMock.getTimestamp("created_at")).thenReturn(new Timestamp(System.currentTimeMillis()));
            when(resultSetMock.getInt("transaction_type_id")).thenReturn(WITHDRAW.ordinal(), DEPOSIT.ordinal());
            when(resultSetMock.getString("account_source_id")).thenReturn(ACCOUNT_SOURCE_1_ID, ACCOUNT_SOURCE_1_ID);
            when(resultSetMock.getString("account_destination_id")).thenReturn(ACCOUNT_SOURCE_1_ID, ACCOUNT_DESTINATION_1_ID);

            //Action
            List<Transaction> result = target.getTransactionsByAccountId(ACCOUNT_SOURCE_1_ID);

            //Assert
            assertThat(result, hasSize(2));
            verify(preparedStatementMock).setString(1, ACCOUNT_SOURCE_1_ID);
            verify(preparedStatementMock).setString(2, ACCOUNT_SOURCE_1_ID);
            verify(preparedStatementMock).executeQuery();
            verify(resultSetMock, times(3)).next();
        }
    }

    @ParameterizedTest
    @CsvSource({"1,2", "3,4", "5,6"})
    void getTransactionsIdsByAccountId_When_TransactionsExist(String accountId, int expectedSize) throws SQLException {
        when(resultSetMock.next()).thenReturn(true, true, false);
        when(resultSetMock.getLong("id")).thenReturn(TRANSACTION_1_ID, TRANSACTION_2_ID);
        List<Long> result = target.getTransactionsIdsByAccountId(accountId);
        assertThat(result, hasSize(expectedSize));
        verify(preparedStatementMock).setString(1, accountId);
        verify(preparedStatementMock).setString(2, accountId);
        verify(preparedStatementMock).executeQuery();
        verify(resultSetMock, times(3)).next();
    }

    @Test
    void findByAccountIdAndDateRange() throws SQLException {
        //Arrange
        final Timestamp startDate = Timestamp.valueOf("2024-01-01 00:00:00");
        final Timestamp endDate = Timestamp.valueOf("2025-01-01 00:00:00");
        final Transaction expectedTransaction = createTransaction(); // создаем тестовую транзакцию
        List<Transaction> result;

        try (var mocked = mockStatic(ConnectionManager.class)) {
            mocked.when(ConnectionManager::getConnection).thenReturn(connectionMock);

            when(connectionMock.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatementMock);
            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);

            when(resultSetMock.next()).thenReturn(true, false);

            when(resultSetMock.getLong("id")).thenReturn(expectedTransaction.getId());
            when(resultSetMock.getBigDecimal("amount")).thenReturn(expectedTransaction.getAmount());
            when(resultSetMock.getTimestamp("created_at")).thenReturn(expectedTransaction.getCreatedAt());
            when(resultSetMock.getString("account_source_id")).thenReturn(expectedTransaction.getSourceAccountId());
            when(resultSetMock.getString("account_destination_id")).thenReturn(expectedTransaction.getDestinationAccountId());

            int transactionTypeOrdinal = expectedTransaction.getTransactionType().ordinal();
            when(resultSetMock.getInt("transaction_type_id")).thenReturn(transactionTypeOrdinal);


            //Act
            result = target.findByAccountIdAndDateRange(ACCOUNT_DESTINATION_1_ID, startDate, endDate);
        }
        //Assert
        verify(connectionMock).prepareStatement(anyString());
        verify(preparedStatementMock).setString(1, ACCOUNT_DESTINATION_1_ID);
        verify(preparedStatementMock).setString(2, ACCOUNT_DESTINATION_1_ID);
        verify(preparedStatementMock).setTimestamp(3, startDate);
        verify(preparedStatementMock).setTimestamp(4, endDate);
        verify(preparedStatementMock).executeQuery();
        verify(resultSetMock, times(2)).next();
        assertNotNull(result);
        verifyCorrectMapping(result.getFirst(), expectedTransaction);

    }

    private void verifyCorrectMapping(Transaction expected, Transaction result) {
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getAmount(), result.getAmount());
        assertEquals(expected.getCreatedAt(), result.getCreatedAt());
        assertEquals(expected.getSourceAccountId(), result.getSourceAccountId());
        assertEquals(expected.getDestinationAccountId(), result.getDestinationAccountId());
        assertEquals(expected.getTransactionType(), result.getTransactionType());
        assertEquals(expected.getCurrency(), result.getCurrency());
    }

    public static Transaction createTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(new BigDecimal("100.23"));
        transaction.setCreatedAt(Timestamp.valueOf("2024-11-20 12:00:00"));
        transaction.setSourceAccountId("BY03KLMNO34567890123456789012");
        transaction.setTransactionType(DEPOSIT);
        transaction.setCurrency("BYN");
        return transaction;
    }

}
