package com.github.kaydunov.dao.impl;

import org.junit.jupiter.api.Timeout;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.List;
import java.sql.ResultSet;
import org.mockito.MockitoAnnotations;
import com.github.kaydunov.exception.DaoException;
import com.github.kaydunov.entity.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.mockito.MockedStatic;
import java.util.Optional;
import com.github.kaydunov.entity.TransactionType;
import java.sql.Timestamp;
import java.math.BigDecimal;
import static org.mockito.Mockito.doNothing;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.atLeast;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.mockStatic;
import org.junit.jupiter.api.Disabled;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class TransactionDaoSapientGeneratedTest {

    private final Connection connectionMock = mock(Connection.class, "connection");

    private AutoCloseable autoCloseableMocks;

    @InjectMocks()
    private TransactionDao target;

    @AfterEach()
    public void afterTest() throws Exception {
        if (autoCloseableMocks != null)
            autoCloseableMocks.close();
    }

    //Sapient generated method id: ${createWhenCaughtSQLException}, hash: E84DA609C95750CA2D2B53FCC907735B
    @Disabled()
    @Test()
    void createWhenCaughtSQLException() throws SQLException {
        /* Branches:
         * (affectedRows > 0) : false
         * (branch expression (line 44)) : true
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new TransactionDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("INSERT INTO transaction (amount, created_at, transaction_type_id, account_source_id, account_destination_id) VALUES (?, ?, ?, ?, ?)", 1);
        doNothing().when(preparedStatementMock).setBigDecimal(eq(1), any());
        Timestamp timestamp = new Timestamp(0L);
        doNothing().when(preparedStatementMock).setTimestamp(2, timestamp);
        doNothing().when(preparedStatementMock).setInt(3, 1);
        doNothing().when(preparedStatementMock).setLong(4, 0L);
        doNothing().when(preparedStatementMock).setLong(5, 0L);
        doReturn(0).when(preparedStatementMock).executeUpdate();
        doNothing().when(preparedStatementMock).close();
        Transaction transaction = new Transaction(new BigDecimal("0"), timestamp, 0L, 0L, TransactionType.DEPOSIT);
        //Act Statement(s)
        Transaction result = target.create(transaction);
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, equalTo(transaction));
            verify(connectionMock).prepareStatement("INSERT INTO transaction (amount, created_at, transaction_type_id, account_source_id, account_destination_id) VALUES (?, ?, ?, ?, ?)", 1);
            verify(preparedStatementMock).setBigDecimal(eq(1), any());
            verify(preparedStatementMock).setTimestamp(2, timestamp);
            verify(preparedStatementMock).setInt(3, 1);
            verify(preparedStatementMock).setLong(4, 0L);
            verify(preparedStatementMock).setLong(5, 0L);
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${createWhenDefaultBranchThrowsThrowable}, hash: 4A05DEFCDE53A7BA0540175D11825151
    @Disabled()
    @Test()
    void createWhenDefaultBranchThrowsThrowable() throws SQLException {
        /* Branches:
         * (affectedRows > 0) : true
         * (generatedKeys.next()) : true
         * (branch expression (line 44)) : true
         * (branch expression (line 35)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new TransactionDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("INSERT INTO transaction (amount, created_at, transaction_type_id, account_source_id, account_destination_id) VALUES (?, ?, ?, ?, ?)", 1);
        doNothing().when(preparedStatementMock).setBigDecimal(eq(1), any());
        Timestamp timestamp = new Timestamp(0L);
        doNothing().when(preparedStatementMock).setTimestamp(2, timestamp);
        doNothing().when(preparedStatementMock).setInt(3, 1);
        doNothing().when(preparedStatementMock).setLong(4, 0L);
        doNothing().when(preparedStatementMock).setLong(5, 0L);
        doReturn(1).when(preparedStatementMock).executeUpdate();
        ResultSet resultSetMock = mock(ResultSet.class);
        doReturn(resultSetMock).when(preparedStatementMock).getGeneratedKeys();
        doReturn(true).when(resultSetMock).next();
        doReturn(0L).when(resultSetMock).getLong(1);
        doNothing().when(resultSetMock).close();
        doNothing().when(preparedStatementMock).close();
        Transaction transaction = new Transaction(new BigDecimal("0"), timestamp, 0L, 0L, TransactionType.DEPOSIT);
        transaction.setId(0L);
        //Act Statement(s)
        final Throwable result = assertThrows(Throwable.class, () -> {
            target.create(transaction);
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            verify(connectionMock).prepareStatement("INSERT INTO transaction (amount, created_at, transaction_type_id, account_source_id, account_destination_id) VALUES (?, ?, ?, ?, ?)", 1);
            verify(preparedStatementMock).setBigDecimal(eq(1), any());
            verify(preparedStatementMock).setTimestamp(2, timestamp);
            verify(preparedStatementMock).setInt(3, 1);
            verify(preparedStatementMock).setLong(4, 0L);
            verify(preparedStatementMock).setLong(5, 0L);
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).getGeneratedKeys();
            verify(resultSetMock).next();
            verify(resultSetMock).getLong(1);
            verify(resultSetMock).close();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${createWhenGeneratedKeysNextAndDefaultBranchAndCaughtSQLException}, hash: 349414D64DEC1E9D425C16CA3928B5D9
    @Disabled()
    @Test()
    void createWhenGeneratedKeysNextAndDefaultBranchAndCaughtSQLException() {
        /* Branches:
         * (affectedRows > 0) : true
         * (generatedKeys.next()) : true
         * (branch expression (line 48)) : false
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new TransactionDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        Timestamp timestamp = new Timestamp(0L);
        Transaction transaction = new Transaction(new BigDecimal("0"), timestamp, 0L, 0L, TransactionType.DEPOSIT);
        transaction.setId(0L);
        //Act Statement(s)
        Transaction result = target.create(transaction);
        //Assert statement(s)
        assertAll("result", () -> assertThat(result, equalTo(transaction)));
    }

    //Sapient generated method id: ${createWhenDefaultBranchAndCaughtSQLExceptionThrowsDaoException}, hash: D465C8501DBEB3E9EB9E9409DF6C359B
    @Disabled()
    @Test()
    void createWhenDefaultBranchAndCaughtSQLExceptionThrowsDaoException() throws SQLException {
        /* Branches:
         * (affectedRows > 0) : true
         * (generatedKeys.next()) : true
         * (branch expression (line 48)) : false
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new TransactionDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("INSERT INTO transaction (amount, created_at, transaction_type_id, account_source_id, account_destination_id) VALUES (?, ?, ?, ?, ?)", 1);
        doNothing().when(preparedStatementMock).setBigDecimal(eq(1), any());
        Timestamp timestamp = new Timestamp(0L);
        doNothing().when(preparedStatementMock).setTimestamp(2, timestamp);
        doNothing().when(preparedStatementMock).setInt(3, 1);
        doNothing().when(preparedStatementMock).setLong(4, 0L);
        doNothing().when(preparedStatementMock).setLong(5, 0L);
        doReturn(1).when(preparedStatementMock).executeUpdate();
        ResultSet resultSetMock = mock(ResultSet.class);
        doReturn(resultSetMock).when(preparedStatementMock).getGeneratedKeys();
        doReturn(true).when(resultSetMock).next();
        doReturn(0L).when(resultSetMock).getLong(1);
        doNothing().when(resultSetMock).close();
        doNothing().when(preparedStatementMock).close();
        Transaction transaction = new Transaction(new BigDecimal("0"), timestamp, 0L, 0L, TransactionType.DEPOSIT);
        transaction.setId(0L);
        SQLException sQLException = new SQLException("message1", "message1", 0);
        DaoException daoException = new DaoException("message1", sQLException);
        //Act Statement(s)
        final DaoException result = assertThrows(DaoException.class, () -> {
            target.create(transaction);
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            assertThat(result.getMessage(), equalTo(daoException.getMessage()));
            assertThat(result.getCause(), is(instanceOf(sQLException.getClass())));
            verify(connectionMock).prepareStatement("INSERT INTO transaction (amount, created_at, transaction_type_id, account_source_id, account_destination_id) VALUES (?, ?, ?, ?, ?)", 1);
            verify(preparedStatementMock).setBigDecimal(eq(1), any());
            verify(preparedStatementMock).setTimestamp(2, timestamp);
            verify(preparedStatementMock).setInt(3, 1);
            verify(preparedStatementMock).setLong(4, 0L);
            verify(preparedStatementMock).setLong(5, 0L);
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).getGeneratedKeys();
            verify(resultSetMock).next();
            verify(resultSetMock).getLong(1);
            verify(resultSetMock).close();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${findByIdWhenDefaultBranchThrowsThrowable}, hash: 9CA8F6FEFB1B5FD79ADE71805B034803
    @Disabled()
    @Test()
    void findByIdWhenDefaultBranchThrowsThrowable() throws SQLException {
        /* Branches:
         * (resultSet.next()) : false
         * (branch expression (line 59)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new TransactionDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM transaction WHERE id = ?");
        doNothing().when(preparedStatementMock).setLong(1, 1L);
        ResultSet resultSetMock = mock(ResultSet.class);
        doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
        doReturn(false).when(resultSetMock).next();
        doNothing().when(preparedStatementMock).close();
        //Act Statement(s)
        final Throwable result = assertThrows(Throwable.class, () -> {
            target.findById(1L);
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            verify(connectionMock).prepareStatement("SELECT * FROM transaction WHERE id = ?");
            verify(preparedStatementMock).setLong(1, 1L);
            verify(preparedStatementMock).executeQuery();
            verify(resultSetMock).next();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${findByIdWhenCaughtSQLException}, hash: 87CF4DC36C7072314D8FA8285F97F212
    @Disabled()
    @Test()
    void findByIdWhenCaughtSQLException() {
        /* Branches:
         * (resultSet.next()) : true
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        try (MockedStatic<TransactionType> transactionType = mockStatic(TransactionType.class, CALLS_REAL_METHODS)) {
            TransactionType[] transactionTypeArray = new TransactionType[] { TransactionType.DEPOSIT };
            transactionType.when(() -> TransactionType.values()).thenReturn(transactionTypeArray);
            target = new TransactionDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            //Act Statement(s)
            Optional<Transaction> result = target.findById(1L);
            Timestamp timestamp = new Timestamp(0L);
            Transaction transaction = new Transaction();
            transaction.setTransactionType(TransactionType.DEPOSIT);
            transaction.setCreatedAt(timestamp);
            transaction.setAccountSourceId(1L);
            transaction.setAccountDestinationId(1L);
            transaction.setAmount(new BigDecimal("0"));
            transaction.setId(1L);
            Optional<Transaction> transactionOptional = Optional.of(transaction);
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, equalTo(transactionOptional));
                transactionType.verify(() -> TransactionType.values(), atLeast(1));
            });
        }
    }

    //Sapient generated method id: ${findByIdWhenCaughtSQLExceptionThrowsDaoException}, hash: FA89DC32F072C7A23F72D002BF4A79CE
    @Disabled()
    @Test()
    void findByIdWhenCaughtSQLExceptionThrowsDaoException() throws SQLException {
        /* Branches:
         * (resultSet.next()) : false
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new TransactionDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM transaction WHERE id = ?");
        doNothing().when(preparedStatementMock).setLong(1, 1L);
        ResultSet resultSetMock = mock(ResultSet.class);
        doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
        doReturn(false).when(resultSetMock).next();
        doNothing().when(preparedStatementMock).close();
        SQLException sQLException = new SQLException("message1", "message1", 0);
        DaoException daoException = new DaoException("message1", sQLException);
        //Act Statement(s)
        final DaoException result = assertThrows(DaoException.class, () -> {
            target.findById(1L);
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            assertThat(result.getMessage(), equalTo(daoException.getMessage()));
            assertThat(result.getCause(), is(instanceOf(sQLException.getClass())));
            verify(connectionMock).prepareStatement("SELECT * FROM transaction WHERE id = ?");
            verify(preparedStatementMock).setLong(1, 1L);
            verify(preparedStatementMock).executeQuery();
            verify(resultSetMock).next();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${findByIdWhenDefaultBranch}, hash: 6A01A0D1B41B1A8429350B178E9623DF
    @Disabled()
    @Test()
    void findByIdWhenDefaultBranch() throws SQLException {
        /* Branches:
         * (resultSet.next()) : false
         * (catch-exception (SQLException)) : false
         * (branch expression (line 59)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new TransactionDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM transaction WHERE id = ?");
        doNothing().when(preparedStatementMock).setLong(1, 1L);
        ResultSet resultSetMock = mock(ResultSet.class);
        doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
        doReturn(false).when(resultSetMock).next();
        doNothing().when(preparedStatementMock).close();
        //Act Statement(s)
        Optional<Transaction> result = target.findById(1L);
        Optional<Transaction> transactionOptional = Optional.empty();
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, equalTo(transactionOptional));
            verify(connectionMock).prepareStatement("SELECT * FROM transaction WHERE id = ?");
            verify(preparedStatementMock).setLong(1, 1L);
            verify(preparedStatementMock).executeQuery();
            verify(resultSetMock).next();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${findAllWhenDefaultBranchThrowsThrowable}, hash: 5228AFD73A5706F53F155A2F6B0F1D94
    @Disabled()
    @Test()
    void findAllWhenDefaultBranchThrowsThrowable() throws SQLException {
        /* Branches:
         * (resultSet.next()) : true
         * (branch expression (line 74)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new TransactionDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM transaction");
        ResultSet resultSetMock = mock(ResultSet.class);
        doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
        doReturn(true, false).when(resultSetMock).next();
        doNothing().when(preparedStatementMock).close();
        //Act Statement(s)
        final Throwable result = assertThrows(Throwable.class, () -> {
            target.findAll();
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            verify(connectionMock).prepareStatement("SELECT * FROM transaction");
            verify(preparedStatementMock).executeQuery();
            verify(resultSetMock, times(2)).next();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${findAllWhenCaughtSQLException}, hash: D4A1D94B2676149F515E3EC672235B0C
    @Disabled()
    @Test()
    void findAllWhenCaughtSQLException() {
        /* Branches:
         * (resultSet.next()) : true
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new TransactionDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        //Act Statement(s)
        List<Transaction> result = target.findAll();
        //Assert statement(s)
        //TODO: Please implement equals method in Transaction for verification of the entire object or you need to adjust respective assertion statements
        assertAll("result", () -> assertThat(result.size(), equalTo(1)));
    }

    //Sapient generated method id: ${findAllWhenCaughtSQLExceptionThrowsDaoException}, hash: 00D0937E01E6CBE7F7825F79C762372E
    @Disabled()
    @Test()
    void findAllWhenCaughtSQLExceptionThrowsDaoException() throws SQLException {
        /* Branches:
         * (resultSet.next()) : true
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new TransactionDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM transaction");
        ResultSet resultSetMock = mock(ResultSet.class);
        doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
        doReturn(true, false).when(resultSetMock).next();
        doNothing().when(preparedStatementMock).close();
        SQLException sQLException = new SQLException("message1", "message1", 0);
        DaoException daoException = new DaoException("message1", sQLException);
        //Act Statement(s)
        final DaoException result = assertThrows(DaoException.class, () -> {
            target.findAll();
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            assertThat(result.getMessage(), equalTo(daoException.getMessage()));
            assertThat(result.getCause(), is(instanceOf(sQLException.getClass())));
            verify(connectionMock).prepareStatement("SELECT * FROM transaction");
            verify(preparedStatementMock).executeQuery();
            verify(resultSetMock, times(2)).next();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${findAllWhenDefaultBranch}, hash: 7235E1F22F7622779B32454158987D80
    @Disabled()
    @Test()
    void findAllWhenDefaultBranch() throws SQLException {
        /* Branches:
         * (resultSet.next()) : true
         * (catch-exception (SQLException)) : false
         * (branch expression (line 74)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new TransactionDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM transaction");
        ResultSet resultSetMock = mock(ResultSet.class);
        doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
        doReturn(true, false).when(resultSetMock).next();
        doNothing().when(preparedStatementMock).close();
        //Act Statement(s)
        List<Transaction> result = target.findAll();
        //Assert statement(s)
        //TODO: Please implement equals method in Transaction for verification of the entire object or you need to adjust respective assertion statements
        assertAll("result", () -> {
            assertThat(result.size(), equalTo(1));
            verify(connectionMock).prepareStatement("SELECT * FROM transaction");
            verify(preparedStatementMock).executeQuery();
            verify(resultSetMock, times(2)).next();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${updateThrowsUnsupportedOperationException}, hash: BE5C18D8E5DFC67427373C89FDAF9C43
    @Test()
    void updateThrowsUnsupportedOperationException() {
        //Arrange Statement(s)
        target = new TransactionDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        Transaction transactionMock = mock(Transaction.class);
        //Act Statement(s)
        final UnsupportedOperationException result = assertThrows(UnsupportedOperationException.class, () -> {
            target.update(transactionMock);
        });
        //Assert statement(s)
        assertAll("result", () -> assertThat(result, is(notNullValue())));
    }

    //Sapient generated method id: ${deleteByIdThrowsUnsupportedOperationException}, hash: B0C617DC943F03CC4FB8EAE2B57E80D9
    @Test()
    void deleteByIdThrowsUnsupportedOperationException() {
        //Arrange Statement(s)
        target = new TransactionDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        //Act Statement(s)
        final UnsupportedOperationException result = assertThrows(UnsupportedOperationException.class, () -> {
            target.deleteById(0L);
        });
        //Assert statement(s)
        assertAll("result", () -> assertThat(result, is(notNullValue())));
    }

    //Sapient generated method id: ${getTransactionsByAccountIdWhenDefaultBranchThrowsThrowable}, hash: F82F2450A41ABBCF5E8E198D079897CB
    @Disabled()
    @Test()
    void getTransactionsByAccountIdWhenDefaultBranchThrowsThrowable() throws SQLException {
        /* Branches:
         * (resultSet.next()) : true
         * (branch expression (line 97)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        ResultSet resultSetMock = mock(ResultSet.class);
        try (MockedStatic<TransactionType> transactionType = mockStatic(TransactionType.class, CALLS_REAL_METHODS)) {
            TransactionType[] transactionTypeArray = new TransactionType[] { TransactionType.DEPOSIT };
            transactionType.when(() -> TransactionType.values()).thenReturn(transactionTypeArray);
            target = new TransactionDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM transaction WHERE account_source_id = ? OR account_destination_id = ?");
            doNothing().when(preparedStatementMock).setLong(1, 1L);
            doNothing().when(preparedStatementMock).setLong(2, 1L);
            doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
            doReturn(true, false).when(resultSetMock).next();
            doNothing().when(preparedStatementMock).close();
            //Act Statement(s)
            final Throwable result = assertThrows(Throwable.class, () -> {
                target.getTransactionsByAccountId(1L);
            });
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));
                transactionType.verify(() -> TransactionType.values(), atLeast(1));
                verify(connectionMock, atLeast(1)).prepareStatement("SELECT * FROM transaction WHERE account_source_id = ? OR account_destination_id = ?");
                verify(preparedStatementMock, atLeast(1)).setLong(1, 1L);
                verify(preparedStatementMock, atLeast(1)).setLong(2, 1L);
                verify(preparedStatementMock, atLeast(1)).executeQuery();
                verify(resultSetMock, times(2)).next();
                verify(preparedStatementMock, atLeast(1)).close();
            });
        }
    }

    //Sapient generated method id: ${getTransactionsByAccountIdWhenCaughtSQLException}, hash: D1AB4D1C5F6148067F5A2BDBFF04C657
    @Disabled()
    @Test()
    void getTransactionsByAccountIdWhenCaughtSQLException() {
        /* Branches:
         * (resultSet.next()) : true
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        try (MockedStatic<TransactionType> transactionType = mockStatic(TransactionType.class, CALLS_REAL_METHODS)) {
            TransactionType[] transactionTypeArray = new TransactionType[] { TransactionType.DEPOSIT, TransactionType.DEPOSIT, TransactionType.DEPOSIT, TransactionType.DEPOSIT, TransactionType.DEPOSIT, TransactionType.DEPOSIT, TransactionType.DEPOSIT, TransactionType.DEPOSIT, TransactionType.DEPOSIT, TransactionType.DEPOSIT };
            transactionType.when(() -> TransactionType.values()).thenReturn(transactionTypeArray);
            target = new TransactionDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            //Act Statement(s)
            List<Transaction> result = target.getTransactionsByAccountId(123456789L);
            //Assert statement(s)
            //TODO: Please implement equals method in Transaction for verification of the entire object or you need to adjust respective assertion statements
            assertAll("result", () -> {
                assertThat(result.size(), equalTo(1));
                transactionType.verify(() -> TransactionType.values(), atLeast(1));
            });
        }
    }

    //Sapient generated method id: ${getTransactionsByAccountIdWhenCaughtSQLExceptionThrowsDaoException}, hash: DF2F1F262BD832B389E4D2F0D26F0D67
    @Disabled()
    @Test()
    void getTransactionsByAccountIdWhenCaughtSQLExceptionThrowsDaoException() throws SQLException {
        /* Branches:
         * (resultSet.next()) : true
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        ResultSet resultSetMock = mock(ResultSet.class);
        try (MockedStatic<TransactionType> transactionType = mockStatic(TransactionType.class, CALLS_REAL_METHODS)) {
            TransactionType[] transactionTypeArray = new TransactionType[] { TransactionType.DEPOSIT };
            transactionType.when(() -> TransactionType.values()).thenReturn(transactionTypeArray);
            target = new TransactionDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM transaction WHERE account_source_id = ? OR account_destination_id = ?");
            doNothing().when(preparedStatementMock).setLong(1, 1L);
            doNothing().when(preparedStatementMock).setLong(2, 1L);
            doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
            doReturn(true, false).when(resultSetMock).next();
            doNothing().when(preparedStatementMock).close();
            //Act Statement(s)
            final DaoException result = assertThrows(DaoException.class, () -> {
                target.getTransactionsByAccountId(1L);
            });
            SQLException sQLException = new SQLException("message1", "message1", 0);
            DaoException daoException = new DaoException("message1", sQLException);
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));
                assertThat(result.getMessage(), equalTo(daoException.getMessage()));
                assertThat(result.getCause(), is(instanceOf(sQLException.getClass())));
                transactionType.verify(() -> TransactionType.values(), atLeast(1));
                verify(connectionMock, atLeast(1)).prepareStatement("SELECT * FROM transaction WHERE account_source_id = ? OR account_destination_id = ?");
                verify(preparedStatementMock, atLeast(1)).setLong(1, 1L);
                verify(preparedStatementMock, atLeast(1)).setLong(2, 1L);
                verify(preparedStatementMock, atLeast(1)).executeQuery();
                verify(resultSetMock, times(2)).next();
                verify(preparedStatementMock, atLeast(1)).close();
            });
        }
    }

    //Sapient generated method id: ${getTransactionsByAccountIdWhenDefaultBranch}, hash: 1FD417628E6D76F0E53CEA6A33791CFF
    @Disabled()
    @Test()
    void getTransactionsByAccountIdWhenDefaultBranch() throws SQLException {
        /* Branches:
         * (resultSet.next()) : true
         * (catch-exception (SQLException)) : false
         * (branch expression (line 97)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        ResultSet resultSetMock = mock(ResultSet.class);
        try (MockedStatic<TransactionType> transactionType = mockStatic(TransactionType.class, CALLS_REAL_METHODS)) {
            TransactionType[] transactionTypeArray = new TransactionType[] { TransactionType.DEPOSIT };
            transactionType.when(() -> TransactionType.values()).thenReturn(transactionTypeArray);
            target = new TransactionDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM transaction WHERE account_source_id = ? OR account_destination_id = ?");
            doNothing().when(preparedStatementMock).setLong(1, 1L);
            doNothing().when(preparedStatementMock).setLong(2, 1L);
            doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
            doReturn(true, false).when(resultSetMock).next();
            doNothing().when(preparedStatementMock).close();
            //Act Statement(s)
            List<Transaction> result = target.getTransactionsByAccountId(1L);
            //Assert statement(s)
            //TODO: Please implement equals method in Transaction for verification of the entire object or you need to adjust respective assertion statements
            assertAll("result", () -> {
                assertThat(result.size(), equalTo(1));
                transactionType.verify(() -> TransactionType.values(), atLeast(1));
                verify(connectionMock, atLeast(1)).prepareStatement("SELECT * FROM transaction WHERE account_source_id = ? OR account_destination_id = ?");
                verify(preparedStatementMock, atLeast(1)).setLong(1, 1L);
                verify(preparedStatementMock, atLeast(1)).setLong(2, 1L);
                verify(preparedStatementMock, atLeast(1)).executeQuery();
                verify(resultSetMock, times(2)).next();
                verify(preparedStatementMock, atLeast(1)).close();
            });
        }
    }
}
