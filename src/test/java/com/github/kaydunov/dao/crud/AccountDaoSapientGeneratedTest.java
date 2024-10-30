package com.github.kaydunov.dao.crud;

import javassist.NotFoundException;
import org.junit.jupiter.api.Timeout;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

import com.github.kaydunov.dao.ConnectionManager;
import org.mockito.MockitoAnnotations;
import java.sql.SQLTransactionRollbackException;
import com.github.kaydunov.exception.DAOException;
import com.github.kaydunov.entity.Transaction;
import java.sql.Connection;
import com.github.kaydunov.entity.Account;
import java.sql.PreparedStatement;
import org.mockito.MockedStatic;

import java.util.Optional;
import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.*;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class AccountDaoSapientGeneratedTest {

    private final Connection connectionMock = mock(Connection.class, "connection");

    private final TransactionDao transactionDaoMock = mock(TransactionDao.class, "transactionDao");

    private AutoCloseable autoCloseableMocks;

    @InjectMocks
    private AccountDao target;

    @AfterEach
    public void afterTest() throws Exception {
        if (autoCloseableMocks != null)
            autoCloseableMocks.close();
    }

    @Test
    void createWhenDefaultBranchThrowsThrowable() throws SQLException {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            target = new AccountDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(preparedStatementMock).when(connectionMock).prepareStatement("INSERT INTO account (balance, bank_id, user_id) VALUES (?, ?, ?)");
            doNothing().when(preparedStatementMock).setBigDecimal(eq(1), any());
            long bankId = 1L;
            long userId = 2L;
            doNothing().when(preparedStatementMock).setLong(2, bankId);
            doNothing().when(preparedStatementMock).setLong(3, userId);
            doThrow(new SQLException()).when(preparedStatementMock).executeQuery();
            doNothing().when(preparedStatementMock).close();
            Account account = new Account();
            account.setBankId(bankId);
            account.setBalance(new BigDecimal("0"));
            account.setUserId(userId);
            //Act Statement(s)
            final Throwable result = assertThrows(DAOException.class, () -> {
                target.create(account);
            });
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));
                connectionManager.verify(ConnectionManager::getConnection, atLeast(1));
                verify(connectionMock).prepareStatement("INSERT INTO account (balance, bank_id, user_id) VALUES (?, ?, ?)");
                verify(preparedStatementMock).setBigDecimal(eq(1), any());
                verify(preparedStatementMock).setString(2, "1");
                verify(preparedStatementMock).setString(3, "1");
                verify(preparedStatementMock).executeQuery();
                verify(preparedStatementMock).close();
                verify(transactionDaoMock, never()).getTransactionsByAccountId("1L");
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
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            target = new AccountDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(preparedStatementMock).when(connectionMock).prepareStatement("UPDATE account SET balance = ? WHERE id = ?");
            doNothing().when(preparedStatementMock).setBigDecimal(eq(1), any());
            String accountId = "1";
            doNothing().when(preparedStatementMock).setString(2, accountId);
            doReturn(0).when(preparedStatementMock).executeUpdate();
            doNothing().when(preparedStatementMock).close();
            Account account = new Account();
            account.setBalance(new BigDecimal("0"));
            account.setId(accountId);
            //Act Statement(s)
            target.update(account);
            //Assert statement(s)
            assertAll("result", () -> {
                connectionManager.verify(ConnectionManager::getConnection, atLeast(1));
                verify(connectionMock).prepareStatement("UPDATE account SET balance = ? WHERE id = ?");
                verify(preparedStatementMock).setBigDecimal(eq(1), any());
                verify(preparedStatementMock).setString(2, accountId);
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
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            target = spy(new AccountDao());
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(Optional.of(accountMock)).when(target).findById("1");
            doNothing().when(accountMock).withdrawBalance(any());
            doReturn(Optional.of(accountMock2)).when(target).findById("987654321");
            doNothing().when(accountMock2).depositBalance(any());
            doNothing().when(target).update(accountMock);
            doNothing().when(target).update(accountMock2);
            doReturn(transactionMock, transactionMock2).when(transactionDaoMock).create((Transaction) any());
            //Act Statement(s)
            target.transfer(new BigDecimal("100.0"), "1", "987654321");
            //Assert statement(s)
            assertAll("result", () -> {
                connectionManager.verify(ConnectionManager::getConnection, atLeast(1));
                verify(target).findById("1");
                verify(accountMock).withdrawBalance(any());
                verify(target).findById("987654321");
                verify(accountMock2).depositBalance(any());
                verify(target).update(accountMock);
                verify(target).update(accountMock2);
                verify(transactionDaoMock, atLeast(2)).create((Transaction) any());
            });
        }
    }

    //Sapient generated method id: ${withdrawTest}, hash: EB515DD11F9CE2942B9F487A58C293AF
    @Test()
    void withdrawTest() throws SQLException, NotFoundException {
        /*
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        Account accountMock = mock(Account.class);
        Transaction transactionMock = mock(Transaction.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            target = spy(new AccountDao());
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(Optional.of(accountMock)).when(target).findById("1");
            doNothing().when(accountMock).withdrawBalance(any());
            doNothing().when(target).update(accountMock);
            doReturn(transactionMock).when(transactionDaoMock).create((Transaction) any());
            //Act Statement(s)
            target.withdraw(new BigDecimal("100.0"), "1");
            //Assert statement(s)
            assertAll("result", () -> {
                connectionManager.verify(ConnectionManager::getConnection, atLeast(1));
                verify(target).findById("1");
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
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            target = spy(new AccountDao());
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            String accountId = "0L";
            doReturn(Optional.of(accountMock)).when(target).findById(accountId);
            doNothing().when(accountMock).withdrawBalance(any());
            doThrow(new DAOException()).when(target).update(accountMock);
            doNothing().when(connectionMock).setAutoCommit(false);
            doReturn(transactionMock).when(transactionDaoMock).create((Transaction) any());
            doNothing().when(connectionMock).commit();
            doNothing().when(connectionMock).rollback();
            //Act Statement(s)
            final SQLTransactionRollbackException result = assertThrows(SQLTransactionRollbackException.class, () -> {
                target.withdraw(new BigDecimal("0"), accountId);
            });
            DAOException daoException = new DAOException("B");
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));
                assertThat(result.getCause(), is(instanceOf(daoException.getClass())));
                connectionManager.verify(ConnectionManager::getConnection, atLeast(1));
                verify(target).findById(accountId);
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
    void depositTest() throws SQLException, NotFoundException {
        /*
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        Account accountMock = mock(Account.class);
        Transaction transactionMock = mock(Transaction.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            target = spy(new AccountDao());
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(Optional.of(accountMock)).when(target).findById("2");
            doNothing().when(accountMock).depositBalance(any());
            doNothing().when(target).update(accountMock);
            doReturn(transactionMock).when(transactionDaoMock).create((Transaction) any());
            //Act Statement(s)
            target.deposit(new BigDecimal("100.0"), "2");
            //Assert statement(s)
            assertAll("result", () -> {
                connectionManager.verify(ConnectionManager::getConnection, atLeast(1));
                verify(target).findById("2");
                verify(accountMock).depositBalance(any());
                verify(target).update(accountMock);
                verify(transactionDaoMock).create((Transaction) any());
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
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            target = new AccountDao();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            doReturn(preparedStatementMock).when(connectionMock).prepareStatement("DELETE FROM account WHERE id = ?");
            doNothing().when(preparedStatementMock).setString(1, "1");
            doReturn(0).when(preparedStatementMock).executeUpdate();
            doNothing().when(preparedStatementMock).close();
            //Act Statement(s)
            target.deleteById("1");
            //Assert statement(s)
            assertAll("result", () -> {
                connectionManager.verify(ConnectionManager::getConnection, atLeast(1));
                verify(connectionMock).prepareStatement("DELETE FROM account WHERE id = ?");
                verify(preparedStatementMock).setString(1, "1");
                verify(preparedStatementMock).executeUpdate();
                verify(preparedStatementMock).close();
            });
        }
    }
}
