package com.github.kaydunov.dao.impl;

import org.junit.jupiter.api.Timeout;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.List;
import com.github.kaydunov.dao.ConnectionManager;
import java.sql.ResultSet;
import org.mockito.MockitoAnnotations;
import java.sql.SQLTransactionRollbackException;
import com.github.kaydunov.exception.DaoException;
import com.github.kaydunov.entity.Transaction;
import java.sql.Connection;
import com.github.kaydunov.entity.Account;
import java.sql.PreparedStatement;
import org.mockito.MockedStatic;
import java.util.ArrayList;
import java.util.Optional;
import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Disabled;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class AccountDaoSapientGeneratedTest {

    private final Connection connectionMock = mock(Connection.class, "connection");

    private final TransactionDao transactionDaoMock = mock(TransactionDao.class, "transactionDao");

    private AutoCloseable autoCloseableMocks;

    @InjectMocks()
    private AccountDao target;

    @AfterEach()
    public void afterTest() throws Exception {
        if (autoCloseableMocks != null)
            autoCloseableMocks.close();
    }

    @Test()
    void createWhenDefaultBranchThrowsThrowable() throws SQLException {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = new AccountDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(preparedStatementMock).when(connectionMock).prepareStatement("INSERT INTO account (balance, bank_id, user_id) VALUES (?, ?, ?)");
            doNothing().when(preparedStatementMock).setBigDecimal(eq(1), any());
            doNothing().when(preparedStatementMock).setLong(2, 1L);
            doNothing().when(preparedStatementMock).setLong(3, 1L);
            doThrow(new SQLException()).when(preparedStatementMock).executeQuery();
            doNothing().when(preparedStatementMock).close();
            Account account = new Account();
            account.setBankId(1L);
            account.setBalance(new BigDecimal("0"));
            account.setUserId(1L);
            //Act Statement(s)
            final Throwable result = assertThrows(DaoException.class, () -> {
                target.create(account);
            });
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(connectionMock).prepareStatement("INSERT INTO account (balance, bank_id, user_id) VALUES (?, ?, ?)");
                verify(preparedStatementMock).setBigDecimal(eq(1), any());
                verify(preparedStatementMock).setLong(2, 1L);
                verify(preparedStatementMock).setLong(3, 1L);
                verify(preparedStatementMock).executeQuery();
                verify(preparedStatementMock).close();
                verify(transactionDaoMock, never()).getTransactionsByAccountId(1L);
            });
        }
    }

    //Sapient generated method id: ${createWhenCaughtSQLExceptionThrowsDaoException}, hash: FA48684254D7827801B22D014E1262DE
    @Disabled()
    @Test()
    void createWhenCaughtSQLExceptionThrowsDaoException() throws SQLException {
        /* Branches:
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        ResultSet resultSetMock = mock(ResultSet.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = new AccountDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(preparedStatementMock).when(connectionMock).prepareStatement("INSERT INTO account (balance, bank_id, user_id) VALUES (?, ?, ?)");
            doNothing().when(preparedStatementMock).setBigDecimal(eq(1), any());
            doNothing().when(preparedStatementMock).setLong(2, 1L);
            doNothing().when(preparedStatementMock).setLong(3, 1L);
            doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
            doNothing().when(preparedStatementMock).close();
            List<Transaction> transactionList = new ArrayList<>();
            doReturn(transactionList).when(transactionDaoMock).getTransactionsByAccountId(1L);
            Account account = new Account();
            account.setBankId(1L);
            account.setBalance(new BigDecimal("0"));
            account.setUserId(1L);
            //Act Statement(s)
            final DaoException result = assertThrows(DaoException.class, () -> {
                target.create(account);
            });
            SQLException sQLException = new SQLException("message1", "message1", 0);
            DaoException daoException = new DaoException("message1", sQLException);
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));
                assertThat(result.getMessage(), equalTo(daoException.getMessage()));
                assertThat(result.getCause(), is(instanceOf(sQLException.getClass())));
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(connectionMock).prepareStatement("INSERT INTO account (balance, bank_id, user_id) VALUES (?, ?, ?)");
                verify(preparedStatementMock).setBigDecimal(eq(1), any());
                verify(preparedStatementMock).setLong(2, 1L);
                verify(preparedStatementMock).setLong(3, 1L);
                verify(preparedStatementMock).executeQuery();
                verify(preparedStatementMock).close();
                verify(transactionDaoMock).getTransactionsByAccountId(1L);
            });
        }
    }

    //Sapient generated method id: ${createWhenDefaultBranch}, hash: C9E1616F9603D042717C83F925B8ECCC
    @Disabled()
    @Test()
    void createWhenDefaultBranch() throws SQLException {
        /* Branches:
         * (catch-exception (SQLException)) : false
         * (branch expression (line 46)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        ResultSet resultSetMock = mock(ResultSet.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = new AccountDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(preparedStatementMock).when(connectionMock).prepareStatement("INSERT INTO account (balance, bank_id, user_id) VALUES (?, ?, ?)");
            doNothing().when(preparedStatementMock).setBigDecimal(eq(1), any());
            doNothing().when(preparedStatementMock).setLong(2, 1L);
            doNothing().when(preparedStatementMock).setLong(3, 1L);
            doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
            doNothing().when(preparedStatementMock).close();
            List<Transaction> transactionList = new ArrayList<>();
            doReturn(transactionList).when(transactionDaoMock).getTransactionsByAccountId(1L);
            Account account = new Account();
            account.setBankId(1L);
            account.setBalance(new BigDecimal("0"));
            account.setUserId(1L);
            //Act Statement(s)
            Account result = target.create(account);
            //Assert statement(s)
            //TODO: Please implement equals method in Account for verification of the entire object or you need to adjust respective assertion statements
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));
                assertThat(result.getUserId(), equalTo(1L));
                assertThat(result.getBankId(), equalTo(1L));
                assertThat(result.getTransactionsIds().size(), equalTo(0));
                assertThat(result.getId(), equalTo(1L));
                assertThat(result.getBalance(), equalTo(new BigDecimal("0")));
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(connectionMock).prepareStatement("INSERT INTO account (balance, bank_id, user_id) VALUES (?, ?, ?)");
                verify(preparedStatementMock).setBigDecimal(eq(1), any());
                verify(preparedStatementMock).setLong(2, 1L);
                verify(preparedStatementMock).setLong(3, 1L);
                verify(preparedStatementMock).executeQuery();
                verify(preparedStatementMock).close();
                verify(transactionDaoMock).getTransactionsByAccountId(1L);
            });
        }
    }

    //Sapient generated method id: ${findByIdWhenDefaultBranchThrowsThrowable}, hash: E5EAFFF14470A961879052C0D9E7A8C7
    @Disabled()
    @Test()
    void findByIdWhenDefaultBranchThrowsThrowable() throws SQLException {
        /* Branches:
         * (branch expression (line 64)) : true
         * (branch expression (line 62)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        ResultSet resultSetMock = mock(ResultSet.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = new AccountDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM account WHERE id = ?");
            doNothing().when(preparedStatementMock).setLong(1, 1L);
            doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
            doNothing().when(resultSetMock).close();
            doNothing().when(preparedStatementMock).close();
            List<Transaction> transactionList = new ArrayList<>();
            doReturn(transactionList).when(transactionDaoMock).getTransactionsByAccountId(1L);
            //Act Statement(s)
            final Throwable result = assertThrows(Throwable.class, () -> {
                target.findById(1L);
            });
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(connectionMock).prepareStatement("SELECT * FROM account WHERE id = ?");
                verify(preparedStatementMock).setLong(1, 1L);
                verify(preparedStatementMock).executeQuery();
                verify(resultSetMock).close();
                verify(preparedStatementMock).close();
                verify(transactionDaoMock).getTransactionsByAccountId(1L);
            });
        }
    }

    //Sapient generated method id: ${findByIdWhenCaughtSQLException}, hash: E28FC96FE0EF9C40187267A246687DBB
    @Disabled()
    @Test()
    void findByIdWhenCaughtSQLException() throws SQLException {
        /* Branches:
         * (branch expression (line 66)) : false
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = new AccountDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            List<Transaction> transactionList = new ArrayList<>();
            doReturn(transactionList).when(transactionDaoMock).getTransactionsByAccountId(1L);
            //Act Statement(s)
            Optional<Account> result = target.findById(123L);
            Account account = new Account();
            account.setBankId(1L);
            account.setBalance(new BigDecimal("0"));
            account.setId(1L);
            account.setTransactionsIds(transactionList);
            account.setUserId(1L);
            Optional<Account> accountOptional = Optional.of(account);
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, equalTo(accountOptional));
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(transactionDaoMock).getTransactionsByAccountId(1L);
            });
        }
    }

    //Sapient generated method id: ${findByIdWhenCaughtSQLExceptionThrowsDaoException}, hash: 8C5D0C81F560342FE257B023E287E93F
    @Disabled()
    @Test()
    void findByIdWhenCaughtSQLExceptionThrowsDaoException() throws SQLException {
        /* Branches:
         * (branch expression (line 66)) : false
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        ResultSet resultSetMock = mock(ResultSet.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = new AccountDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM account WHERE id = ?");
            doNothing().when(preparedStatementMock).setLong(1, 1L);
            doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
            doNothing().when(resultSetMock).close();
            doNothing().when(preparedStatementMock).close();
            List<Transaction> transactionList = new ArrayList<>();
            doReturn(transactionList).when(transactionDaoMock).getTransactionsByAccountId(1L);
            //Act Statement(s)
            final DaoException result = assertThrows(DaoException.class, () -> {
                target.findById(1L);
            });
            SQLException sQLException = new SQLException("message1", "message1", 0);
            DaoException daoException = new DaoException("message1", sQLException);
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));
                assertThat(result.getMessage(), equalTo(daoException.getMessage()));
                assertThat(result.getCause(), is(instanceOf(sQLException.getClass())));
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(connectionMock).prepareStatement("SELECT * FROM account WHERE id = ?");
                verify(preparedStatementMock).setLong(1, 1L);
                verify(preparedStatementMock).executeQuery();
                verify(resultSetMock).close();
                verify(preparedStatementMock).close();
                verify(transactionDaoMock).getTransactionsByAccountId(1L);
            });
        }
    }

    //Sapient generated method id: ${findByIdWhenDefaultBranchAndCaughtSQLException}, hash: 5DBD60C04559B76948A86B508592DC39
    @Disabled()
    @Test()
    void findByIdWhenDefaultBranchAndCaughtSQLException() throws SQLException {
        /* Branches:
         * (branch expression (line 66)) : false
         * (branch expression (line 64)) : true
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        ResultSet resultSetMock = mock(ResultSet.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = new AccountDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM account WHERE id = ?");
            doNothing().when(preparedStatementMock).setLong(1, 1L);
            doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
            doNothing().when(resultSetMock).close();
            doNothing().when(preparedStatementMock).close();
            List<Transaction> transactionList = new ArrayList<>();
            doReturn(transactionList).when(transactionDaoMock).getTransactionsByAccountId(1L);
            //Act Statement(s)
            Optional<Account> result = target.findById(1L);
            Account account = new Account();
            account.setBankId(1L);
            account.setBalance(new BigDecimal("0"));
            account.setId(1L);
            account.setTransactionsIds(transactionList);
            account.setUserId(1L);
            Optional<Account> accountOptional = Optional.of(account);
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, equalTo(accountOptional));
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(connectionMock).prepareStatement("SELECT * FROM account WHERE id = ?");
                verify(preparedStatementMock).setLong(1, 1L);
                verify(preparedStatementMock).executeQuery();
                verify(resultSetMock).close();
                verify(preparedStatementMock).close();
                verify(transactionDaoMock).getTransactionsByAccountId(1L);
            });
        }
    }

    //Sapient generated method id: ${findAllWhenDefaultBranchThrowsThrowable}, hash: 8BC34B8EAA30B98D9B28181D8546E743
    @Disabled()
    @Test()
    void findAllWhenDefaultBranchThrowsThrowable() throws SQLException {
        /* Branches:
         * (resultSet.next()) : true
         * (branch expression (line 77)) : true
         * (branch expression (line 76)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        ResultSet resultSetMock = mock(ResultSet.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = new AccountDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM account");
            doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
            doReturn(true, false).when(resultSetMock).next();
            doNothing().when(resultSetMock).close();
            doNothing().when(preparedStatementMock).close();
            List<Transaction> transactionList = new ArrayList<>();
            doReturn(transactionList).when(transactionDaoMock).getTransactionsByAccountId(1L);
            //Act Statement(s)
            final Throwable result = assertThrows(Throwable.class, () -> {
                target.findAll();
            });
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(connectionMock).prepareStatement("SELECT * FROM account");
                verify(preparedStatementMock).executeQuery();
                verify(resultSetMock, times(2)).next();
                verify(resultSetMock).close();
                verify(preparedStatementMock).close();
                verify(transactionDaoMock).getTransactionsByAccountId(1L);
            });
        }
    }

    //Sapient generated method id: ${findAllWhenCaughtSQLException}, hash: 502392B33229904451AEAAFDE3122C2D
    @Disabled()
    @Test()
    void findAllWhenCaughtSQLException() throws SQLException {
        /* Branches:
         * (resultSet.next()) : true
         * (branch expression (line 82)) : false
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = new AccountDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            List<Transaction> transactionList = new ArrayList<>();
            doReturn(transactionList).when(transactionDaoMock).getTransactionsByAccountId(1L);
            //Act Statement(s)
            List<Account> result = target.findAll();
            //Assert statement(s)
            //TODO: Please implement equals method in Account for verification of the entire object or you need to adjust respective assertion statements
            assertAll("result", () -> {
                assertThat(result.size(), equalTo(1));
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(transactionDaoMock).getTransactionsByAccountId(1L);
            });
        }
    }

    //Sapient generated method id: ${findAllWhenCaughtSQLExceptionThrowsDaoException}, hash: 20D83F5E0BFE6CC154D5EC5E861A0747
    @Disabled()
    @Test()
    void findAllWhenCaughtSQLExceptionThrowsDaoException() throws SQLException {
        /* Branches:
         * (resultSet.next()) : true
         * (branch expression (line 82)) : false
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        ResultSet resultSetMock = mock(ResultSet.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = new AccountDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM account");
            doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
            doReturn(true, false).when(resultSetMock).next();
            doNothing().when(resultSetMock).close();
            doNothing().when(preparedStatementMock).close();
            List<Transaction> transactionList = new ArrayList<>();
            doReturn(transactionList).when(transactionDaoMock).getTransactionsByAccountId(1L);
            //Act Statement(s)
            final DaoException result = assertThrows(DaoException.class, () -> {
                target.findAll();
            });
            SQLException sQLException = new SQLException("message1", "message1", 0);
            DaoException daoException = new DaoException("message1", sQLException);
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));
                assertThat(result.getMessage(), equalTo(daoException.getMessage()));
                assertThat(result.getCause(), is(instanceOf(sQLException.getClass())));
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(connectionMock).prepareStatement("SELECT * FROM account");
                verify(preparedStatementMock).executeQuery();
                verify(resultSetMock, times(2)).next();
                verify(resultSetMock).close();
                verify(preparedStatementMock).close();
                verify(transactionDaoMock).getTransactionsByAccountId(1L);
            });
        }
    }

    //Sapient generated method id: ${findAllWhenDefaultBranchAndDefaultBranchAndCaughtSQLException}, hash: 0C072411D5F85283FA9608A4B42F29C0
    @Disabled()
    @Test()
    void findAllWhenDefaultBranchAndDefaultBranchAndCaughtSQLException() throws SQLException {
        /* Branches:
         * (resultSet.next()) : true
         * (branch expression (line 82)) : false
         * (branch expression (line 77)) : true
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        ResultSet resultSetMock = mock(ResultSet.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = new AccountDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM account");
            doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
            doReturn(true, false).when(resultSetMock).next();
            doNothing().when(resultSetMock).close();
            doNothing().when(preparedStatementMock).close();
            List<Transaction> transactionList = new ArrayList<>();
            doReturn(transactionList).when(transactionDaoMock).getTransactionsByAccountId(1L);
            //Act Statement(s)
            List<Account> result = target.findAll();
            //Assert statement(s)
            //TODO: Please implement equals method in Account for verification of the entire object or you need to adjust respective assertion statements
            assertAll("result", () -> {
                assertThat(result.size(), equalTo(1));
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(connectionMock).prepareStatement("SELECT * FROM account");
                verify(preparedStatementMock).executeQuery();
                verify(resultSetMock, times(2)).next();
                verify(resultSetMock).close();
                verify(preparedStatementMock).close();
                verify(transactionDaoMock).getTransactionsByAccountId(1L);
            });
        }
    }

    //Sapient generated method id: ${updateWhenDefaultBranchThrowsThrowable}, hash: F8C4CFFFAF4BD1BE419A050FD4B43185
    @Disabled()
    @Test()
    void updateWhenDefaultBranchThrowsThrowable() throws SQLException {
        /* Branches:
         * (branch expression (line 91)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = new AccountDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(preparedStatementMock).when(connectionMock).prepareStatement("UPDATE account SET balance = ? WHERE id = ?");
            doNothing().when(preparedStatementMock).setBigDecimal(eq(1), any());
            doNothing().when(preparedStatementMock).setLong(2, 1L);
            doReturn(0).when(preparedStatementMock).executeUpdate();
            doNothing().when(preparedStatementMock).close();
            Account account = new Account();
            account.setBalance(new BigDecimal("0"));
            account.setId(1L);
            //Act Statement(s)
            final Throwable result = assertThrows(Throwable.class, () -> {
                target.update(account);
            });
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(connectionMock).prepareStatement("UPDATE account SET balance = ? WHERE id = ?");
                verify(preparedStatementMock).setBigDecimal(eq(1), any());
                verify(preparedStatementMock).setLong(2, 1L);
                verify(preparedStatementMock).executeUpdate();
                verify(preparedStatementMock).close();
            });
        }
    }

    //Sapient generated method id: ${updateWhenCaughtSQLException}, hash: 7405FA883BD11DEC26B20B9A3774FDBC
    @Disabled()
    @Test()
    void updateWhenCaughtSQLException() throws SQLException {
        /* Branches:
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = new AccountDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            Account account = new Account();
            account.setBalance(new BigDecimal("0"));
            account.setId(1L);
            //Act Statement(s)
            target.update(account);
            //Assert statement(s)
            assertAll("result", () -> connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1)));
        }
    }

    //Sapient generated method id: ${updateWhenCaughtSQLExceptionThrowsDaoException}, hash: 65A6B7B7A59E09ED0EDFA3D8FA584E00
    @Disabled()
    @Test()
    void updateWhenCaughtSQLExceptionThrowsDaoException() throws SQLException {
        /* Branches:
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = new AccountDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(preparedStatementMock).when(connectionMock).prepareStatement("UPDATE account SET balance = ? WHERE id = ?");
            doNothing().when(preparedStatementMock).setBigDecimal(eq(1), any());
            doNothing().when(preparedStatementMock).setLong(2, 1L);
            doReturn(0).when(preparedStatementMock).executeUpdate();
            doNothing().when(preparedStatementMock).close();
            Account account = new Account();
            account.setBalance(new BigDecimal("0"));
            account.setId(1L);
            //Act Statement(s)
            final DaoException result = assertThrows(DaoException.class, () -> {
                target.update(account);
            });
            SQLException sQLException = new SQLException("message1", "message1", 0);
            DaoException daoException = new DaoException("message1", sQLException);
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));
                assertThat(result.getMessage(), equalTo(daoException.getMessage()));
                assertThat(result.getCause(), is(instanceOf(sQLException.getClass())));
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(connectionMock).prepareStatement("UPDATE account SET balance = ? WHERE id = ?");
                verify(preparedStatementMock).setBigDecimal(eq(1), any());
                verify(preparedStatementMock).setLong(2, 1L);
                verify(preparedStatementMock).executeUpdate();
                verify(preparedStatementMock).close();
            });
        }
    }

    //Sapient generated method id: ${updateWhenDefaultBranch}, hash: 325ECF463ED62B06CA0F2A2C3B31A1A8
    @Test()
    void updateWhenDefaultBranch() throws SQLException {
        /* Branches:
         * (catch-exception (SQLException)) : false
         * (branch expression (line 91)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = new AccountDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(preparedStatementMock).when(connectionMock).prepareStatement("UPDATE account SET balance = ? WHERE id = ?");
            doNothing().when(preparedStatementMock).setBigDecimal(eq(1), any());
            doNothing().when(preparedStatementMock).setLong(2, 1L);
            doReturn(0).when(preparedStatementMock).executeUpdate();
            doNothing().when(preparedStatementMock).close();
            Account account = new Account();
            account.setBalance(new BigDecimal("0"));
            account.setId(1L);
            //Act Statement(s)
            target.update(account);
            //Assert statement(s)
            assertAll("result", () -> {
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(connectionMock).prepareStatement("UPDATE account SET balance = ? WHERE id = ?");
                verify(preparedStatementMock).setBigDecimal(eq(1), any());
                verify(preparedStatementMock).setLong(2, 1L);
                verify(preparedStatementMock).executeUpdate();
                verify(preparedStatementMock).close();
            });
        }
    }

    //Sapient generated method id: ${transferTest}, hash: BD7272EC4163291BFCB50124D778528C
    @Test()
    void transferTest() throws SQLException {
        /*
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        Account accountMock = mock(Account.class);
        Account accountMock2 = mock(Account.class);
        Transaction transactionMock = mock(Transaction.class);
        Transaction transactionMock2 = mock(Transaction.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = spy(new AccountDao());
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(Optional.of(accountMock)).when(target).findById(123456789L);
            doNothing().when(accountMock).withdrawBalance(any());
            doReturn(Optional.of(accountMock2)).when(target).findById(987654321L);
            doNothing().when(accountMock2).depositBalance(any());
            doNothing().when(target).update(accountMock);
            doNothing().when(target).update(accountMock2);
            doReturn(transactionMock, transactionMock2).when(transactionDaoMock).create((Transaction) any());
            //Act Statement(s)
            target.transfer(new BigDecimal("100.0"), 123456789L, 987654321L);
            //Assert statement(s)
            assertAll("result", () -> {
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(target).findById(123456789L);
                verify(accountMock).withdrawBalance(any());
                verify(target).findById(987654321L);
                verify(accountMock2).depositBalance(any());
                verify(target).update(accountMock);
                verify(target).update(accountMock2);
                verify(transactionDaoMock, atLeast(2)).create((Transaction) any());
            });
        }
    }

    //Sapient generated method id: ${transferWhenCaughtSQLExceptionThrowsSQLTransactionRollbackException}, hash: 5A61A18428D1C8B381608B4F41BE62AE
    @Disabled()
    @Test()
    void transferWhenCaughtSQLExceptionThrowsSQLTransactionRollbackException() throws SQLException {
        /* Branches:
         * (catch-exception (SQLException)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        Account accountMock = mock(Account.class);
        Account accountMock2 = mock(Account.class);
        Transaction transactionMock = mock(Transaction.class);
        Transaction transactionMock2 = mock(Transaction.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = spy(new AccountDao());
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doNothing().when(accountMock).withdrawBalance(any());
            doReturn(Optional.of(accountMock), Optional.of(accountMock2)).when(target).findById(0L);
            doNothing().when(accountMock2).depositBalance(any());
            doNothing().when(target).update(accountMock);
            doNothing().when(target).update(accountMock2);
            doNothing().when(connectionMock).setAutoCommit(false);
            doReturn(transactionMock, transactionMock2).when(transactionDaoMock).create((Transaction) any());
            doNothing().when(connectionMock).commit();
            doNothing().when(connectionMock).rollback();
            //Act Statement(s)
            final SQLTransactionRollbackException result = assertThrows(SQLTransactionRollbackException.class, () -> {
                target.transfer(new BigDecimal("0"), 0L, 0L);
            });
            SQLException sQLException = new SQLException("C", "C", 0);
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));
                assertThat(result.getCause(), is(instanceOf(sQLException.getClass())));
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(target, times(2)).findById(0L);
                verify(accountMock).withdrawBalance(any());
                verify(accountMock2).depositBalance(any());
                verify(target).update(accountMock);
                verify(target).update(accountMock2);
                verify(connectionMock).setAutoCommit(false);
                verify(transactionDaoMock, atLeast(2)).create((Transaction) any());
                verify(connectionMock).commit();
                verify(connectionMock).rollback();
            });
        }
    }

    //Sapient generated method id: ${transferWhenCaughtSQLException2ThrowsSQLTransactionRollbackException}, hash: C4C039652C1CA5EE707B19359614A091
    @Disabled()
    @Test()
    void transferWhenCaughtSQLException2ThrowsSQLTransactionRollbackException() throws SQLException {
        /* Branches:
         * (catch-exception (SQLException)) : true  #  inside updateWithTransaction method
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        Account accountMock = mock(Account.class);
        Account accountMock2 = mock(Account.class);
        Transaction transactionMock = mock(Transaction.class);
        Transaction transactionMock2 = mock(Transaction.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = spy(new AccountDao());
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doNothing().when(accountMock).withdrawBalance(any());
            doReturn(Optional.of(accountMock), Optional.of(accountMock2)).when(target).findById(0L);
            doNothing().when(accountMock2).depositBalance(any());
            doNothing().when(target).update(accountMock);
            doNothing().when(target).update(accountMock2);
            doNothing().when(connectionMock).setAutoCommit(false);
            doReturn(transactionMock, transactionMock2).when(transactionDaoMock).create((Transaction) any());
            doNothing().when(connectionMock).commit();
            doNothing().when(connectionMock).rollback();
            //Act Statement(s)
            final SQLTransactionRollbackException result = assertThrows(SQLTransactionRollbackException.class, () -> {
                target.transfer(new BigDecimal("0"), 0L, 0L);
            });
            SQLException sQLException = new SQLException("D", "D", 0);
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));
                assertThat(result.getCause(), is(instanceOf(sQLException.getClass())));
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(target, times(2)).findById(0L);
                verify(accountMock).withdrawBalance(any());
                verify(accountMock2).depositBalance(any());
                verify(target).update(accountMock);
                verify(target).update(accountMock2);
                verify(transactionDaoMock, atLeast(2)).create((Transaction) any());
                verify(connectionMock).setAutoCommit(false);
                verify(connectionMock).commit();
                verify(connectionMock).rollback();
            });
        }
    }

    //Sapient generated method id: ${transferWhenCaughtSQLException3ThrowsSQLTransactionRollbackException}, hash: 66C046573A9D31A23237AB65779688E4
    @Disabled()
    @Test()
    void transferWhenCaughtSQLException3ThrowsSQLTransactionRollbackException() throws SQLException {
        /* Branches:
         * (catch-exception (SQLException)) : true  #  inside updateWithTransaction method
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        Account accountMock = mock(Account.class);
        Account accountMock2 = mock(Account.class);
        Transaction transactionMock = mock(Transaction.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = spy(new AccountDao());
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doNothing().when(accountMock).withdrawBalance(any());
            doReturn(Optional.of(accountMock), Optional.of(accountMock2)).when(target).findById(0L);
            doNothing().when(accountMock2).depositBalance(any());
            doNothing().when(target).update(accountMock);
            doNothing().when(connectionMock).setAutoCommit(false);
            doReturn(transactionMock).when(transactionDaoMock).create((Transaction) any());
            doNothing().when(connectionMock).commit();
            doNothing().when(connectionMock).rollback();
            //Act Statement(s)
            final SQLTransactionRollbackException result = assertThrows(SQLTransactionRollbackException.class, () -> {
                target.transfer(new BigDecimal("0"), 0L, 0L);
            });
            SQLException sQLException = new SQLException("C", "C", 0);
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));
                assertThat(result.getCause(), is(instanceOf(sQLException.getClass())));
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(target, times(2)).findById(0L);
                verify(accountMock).withdrawBalance(any());
                verify(accountMock2).depositBalance(any());
                verify(target).update(accountMock);
                verify(connectionMock).setAutoCommit(false);
                verify(transactionDaoMock).create((Transaction) any());
                verify(connectionMock).commit();
                verify(connectionMock).rollback();
            });
        }
    }

    //Sapient generated method id: ${withdrawTest}, hash: EB515DD11F9CE2942B9F487A58C293AF
    @Test()
    void withdrawTest() throws SQLException {
        /*
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        Account accountMock = mock(Account.class);
        Transaction transactionMock = mock(Transaction.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = spy(new AccountDao());
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(Optional.of(accountMock)).when(target).findById(123456789L);
            doNothing().when(accountMock).withdrawBalance(any());
            doNothing().when(target).update(accountMock);
            doReturn(transactionMock).when(transactionDaoMock).create((Transaction) any());
            //Act Statement(s)
            target.withdraw(new BigDecimal("100.0"), 123456789L);
            //Assert statement(s)
            assertAll("result", () -> {
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(target).findById(123456789L);
                verify(accountMock).withdrawBalance(any());
                verify(target).update(accountMock);
                verify(transactionDaoMock).create((Transaction) any());
            });
        }
    }

    //Sapient generated method id: ${withdrawWhenCaughtSQLExceptionThrowsSQLTransactionRollbackException}, hash: A621F74A64B3418D564EF1D2726459CC
    @Test()
    void withdrawWhenCaughtSQLExceptionThrowsSQLTransactionRollbackException() throws SQLException {
        /* Branches:
         * (catch-exception (SQLException)) : true  #  inside updateWithTransaction method
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        Account accountMock = mock(Account.class);
        Transaction transactionMock = mock(Transaction.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = spy(new AccountDao());
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(Optional.of(accountMock)).when(target).findById(0L);
            doNothing().when(accountMock).withdrawBalance(any());
            doThrow(new DaoException()).when(target).update(accountMock);
            doNothing().when(connectionMock).setAutoCommit(false);
            doReturn(transactionMock).when(transactionDaoMock).create((Transaction) any());
            doNothing().when(connectionMock).commit();
            doNothing().when(connectionMock).rollback();
            //Act Statement(s)
            final SQLTransactionRollbackException result = assertThrows(SQLTransactionRollbackException.class, () -> {
                target.withdraw(new BigDecimal("0"), 0L);
            });
            DaoException daoException = new DaoException("B");
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));
                assertThat(result.getCause(), is(instanceOf(daoException.getClass())));
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(target).findById(0L);
                verify(accountMock).withdrawBalance(any());
                verify(target).update(accountMock);
                verify(connectionMock).setAutoCommit(false);
                verify(transactionDaoMock).create((Transaction) any());
                verify(connectionMock, never()).commit();
                verify(connectionMock).rollback();
            });
        }
    }

    //Sapient generated method id: ${depositTest}, hash: 11A650AC5F20456F471D815D6943B284
    @Test()
    void depositTest() throws SQLException {
        /*
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        Account accountMock = mock(Account.class);
        Transaction transactionMock = mock(Transaction.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = spy(new AccountDao());
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(Optional.of(accountMock)).when(target).findById(1234567890L);
            doNothing().when(accountMock).depositBalance(any());
            doNothing().when(target).update(accountMock);
            doReturn(transactionMock).when(transactionDaoMock).create((Transaction) any());
            //Act Statement(s)
            target.deposit(new BigDecimal("100.0"), 1234567890L);
            //Assert statement(s)
            assertAll("result", () -> {
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(target).findById(1234567890L);
                verify(accountMock).depositBalance(any());
                verify(target).update(accountMock);
                verify(transactionDaoMock).create((Transaction) any());
            });
        }
    }

    //Sapient generated method id: ${depositWhenCaughtSQLExceptionThrowsSQLTransactionRollbackException}, hash: 43804DD31B040619FB97130D9524DBD1
    @Disabled()
    @Test()
    void depositWhenCaughtSQLExceptionThrowsSQLTransactionRollbackException() throws SQLException {
        /* Branches:
         * (catch-exception (SQLException)) : true  #  inside updateWithTransaction method
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        Account accountMock = mock(Account.class);
        Transaction transactionMock = mock(Transaction.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = spy(new AccountDao());
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(Optional.of(accountMock)).when(target).findById(0L);
            doNothing().when(accountMock).depositBalance(any());
            doNothing().when(target).update(accountMock);
            doNothing().when(connectionMock).setAutoCommit(false);
            doReturn(transactionMock).when(transactionDaoMock).create((Transaction) any());
            doNothing().when(connectionMock).commit();
            doNothing().when(connectionMock).rollback();
            //Act Statement(s)
            final SQLTransactionRollbackException result = assertThrows(SQLTransactionRollbackException.class, () -> {
                target.deposit(new BigDecimal("0"), 0L);
            });
            SQLException sQLException = new SQLException("B", "B", 0);
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));
                assertThat(result.getCause(), is(instanceOf(sQLException.getClass())));
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(target).findById(0L);
                verify(accountMock).depositBalance(any());
                verify(target).update(accountMock);
                verify(connectionMock).setAutoCommit(false);
                verify(transactionDaoMock).create((Transaction) any());
                verify(connectionMock).commit();
                verify(connectionMock).rollback();
            });
        }
    }

    //Sapient generated method id: ${deleteByIdWhenDefaultBranchThrowsThrowable}, hash: FB23BE20C42A3A2C917C1F7F0D4EFA85
    @Disabled()
    @Test()
    void deleteByIdWhenDefaultBranchThrowsThrowable() throws SQLException {
        /* Branches:
         * (branch expression (line 164)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = new AccountDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(preparedStatementMock).when(connectionMock).prepareStatement("DELETE FROM account WHERE id = ?");
            doNothing().when(preparedStatementMock).setLong(1, 1L);
            doReturn(0).when(preparedStatementMock).executeUpdate();
            doNothing().when(preparedStatementMock).close();
            //Act Statement(s)
            final Throwable result = assertThrows(Throwable.class, () -> {
                target.deleteById(1L);
            });
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(connectionMock).prepareStatement("DELETE FROM account WHERE id = ?");
                verify(preparedStatementMock).setLong(1, 1L);
                verify(preparedStatementMock).executeUpdate();
                verify(preparedStatementMock).close();
            });
        }
    }

    //Sapient generated method id: ${deleteByIdWhenCaughtSQLException}, hash: EA692A2BC816BCA2DA5DE05642579FD0
    @Disabled()
    @Test()
    void deleteByIdWhenCaughtSQLException() throws SQLException {
        /* Branches:
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = new AccountDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            //Act Statement(s)
            target.deleteById(12345L);
            //Assert statement(s)
            assertAll("result", () -> connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1)));
        }
    }

    //Sapient generated method id: ${deleteByIdWhenCaughtSQLExceptionThrowsDaoException}, hash: 26C3438871ABFCB7B7786539EC23A878
    @Disabled()
    @Test()
    void deleteByIdWhenCaughtSQLExceptionThrowsDaoException() throws SQLException {
        /* Branches:
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = new AccountDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(preparedStatementMock).when(connectionMock).prepareStatement("DELETE FROM account WHERE id = ?");
            doNothing().when(preparedStatementMock).setLong(1, 1L);
            doReturn(0).when(preparedStatementMock).executeUpdate();
            doNothing().when(preparedStatementMock).close();
            //Act Statement(s)
            final DaoException result = assertThrows(DaoException.class, () -> {
                target.deleteById(1L);
            });
            SQLException sQLException = new SQLException("message1", "message1", 0);
            DaoException daoException = new DaoException("message1", sQLException);
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));
                assertThat(result.getMessage(), equalTo(daoException.getMessage()));
                assertThat(result.getCause(), is(instanceOf(sQLException.getClass())));
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(connectionMock).prepareStatement("DELETE FROM account WHERE id = ?");
                verify(preparedStatementMock).setLong(1, 1L);
                verify(preparedStatementMock).executeUpdate();
                verify(preparedStatementMock).close();
            });
        }
    }

    //Sapient generated method id: ${deleteByIdWhenDefaultBranch}, hash: FD3283812D1B3C8DF65EE70996AA75D2
    @Test()
    void deleteByIdWhenDefaultBranch() throws SQLException {
        /* Branches:
         * (catch-exception (SQLException)) : false
         * (branch expression (line 164)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(() -> ConnectionManager.getConnection()).thenReturn(connectionMock);
            target = new AccountDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(preparedStatementMock).when(connectionMock).prepareStatement("DELETE FROM account WHERE id = ?");
            doNothing().when(preparedStatementMock).setLong(1, 1L);
            doReturn(0).when(preparedStatementMock).executeUpdate();
            doNothing().when(preparedStatementMock).close();
            //Act Statement(s)
            target.deleteById(1L);
            //Assert statement(s)
            assertAll("result", () -> {
                connectionManager.verify(() -> ConnectionManager.getConnection(), atLeast(1));
                verify(connectionMock).prepareStatement("DELETE FROM account WHERE id = ?");
                verify(preparedStatementMock).setLong(1, 1L);
                verify(preparedStatementMock).executeUpdate();
                verify(preparedStatementMock).close();
            });
        }
    }
}
