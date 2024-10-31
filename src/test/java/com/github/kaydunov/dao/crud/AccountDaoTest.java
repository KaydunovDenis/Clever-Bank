package com.github.kaydunov.dao.crud;

import com.github.kaydunov.entity.AccountTest;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Timeout;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

import com.github.kaydunov.dao.ConnectionManager;
import org.mockito.Mock;

import java.sql.SQLTransactionRollbackException;
import com.github.kaydunov.exception.DaoException;
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
import static org.mockito.MockitoAnnotations.*;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class AccountDaoTest {

    @Mock
    private Connection connectionMock;
    @Mock
    private TransactionDao transactionDao;
    @Mock
    private PreparedStatement preparedStatementMock;
    @InjectMocks
    private AccountDao target;
    private AutoCloseable autoCloseableMocks;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @AfterEach
    public void afterTest() throws Exception {
        if (autoCloseableMocks != null)
            autoCloseableMocks.close();
    }

    @Test
    void create_whenThrowsThrowable() throws SQLException {
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            when(connectionMock.prepareStatement(anyString())).thenThrow(new SQLException());
            final Account account = AccountTest.createAccount();
            //Act Statement(s)
            DaoException result = assertThrows(DaoException.class, () -> {
                target.create(account);
            });
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));
                verify(connectionMock).prepareStatement(anyString());
            });
        }
    }


    @Test
    void update() throws SQLException {
        //Arrange Statement(s)
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            doReturn(preparedStatementMock).when(connectionMock).prepareStatement("UPDATE account SET balance = ? WHERE id = ?");
            doReturn(0).when(preparedStatementMock).executeUpdate();
            doNothing().when(preparedStatementMock).close();
            Account account = AccountTest.createAccount();
            //Act Statement(s)
            target.update(account);
            //Assert statement(s)
            assertAll("result", () -> {
                connectionManager.verify(ConnectionManager::getConnection, atLeast(1));
                verify(connectionMock).prepareStatement("UPDATE account SET balance = ? WHERE id = ?");
                verify(preparedStatementMock).setBigDecimal(eq(1), any());
                verify(preparedStatementMock).setString(2, "0L");
                verify(preparedStatementMock).executeUpdate();
                verify(preparedStatementMock).close();
            });
        }
    }

    @Test
    void transferTest() throws SQLException {
        //Arrange Statement(s)
        Account accountMock = mock(Account.class);
        Account accountMock2 = mock(Account.class);
        Transaction transactionMock = mock(Transaction.class);
        Transaction transactionMock2 = mock(Transaction.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            target = spy(new AccountDao());
            autoCloseableMocks = openMocks(this);
            doReturn(Optional.of(accountMock)).when(target).findById("1");
            doNothing().when(accountMock).withdrawBalance(any());
            doReturn(Optional.of(accountMock2)).when(target).findById("987654321");
            doNothing().when(accountMock2).depositBalance(any());
            doNothing().when(target).update(accountMock);
            doNothing().when(target).update(accountMock2);
            doReturn(transactionMock, transactionMock2).when(transactionDao).create((Transaction) any());
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
                verify(transactionDao, atLeast(2)).create((Transaction) any());
            });
        }
    }

    @Test
    void withdrawTest() throws SQLException, NotFoundException {
        Account accountMock = mock(Account.class);
        Transaction transactionMock = mock(Transaction.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            target = spy(new AccountDao());
            autoCloseableMocks = openMocks(this);
            doReturn(Optional.of(accountMock)).when(target).findById("1");
            doNothing().when(accountMock).withdrawBalance(any());
            doNothing().when(target).update(accountMock);
            doReturn(transactionMock).when(transactionDao).create((Transaction) any());
            //Act Statement(s)
            target.withdraw(new BigDecimal("100.0"), "1");
            //Assert statement(s)
            assertAll("result", () -> {
                connectionManager.verify(ConnectionManager::getConnection, atLeast(1));
                verify(target).findById("1");
                verify(accountMock).withdrawBalance(any());
                verify(target).update(accountMock);
                verify(transactionDao).create((Transaction) any());
            });
        }
    }

    @Test
    void withdraw_whenThrowsSQLTransactionRollbackException() throws SQLException {

        //Arrange Statement(s)
        Account accountMock = mock(Account.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            target = spy(new AccountDao());
            String accountId = "0L";
            doReturn(Optional.of(accountMock)).when(target).findById(accountId);
            doNothing().when(accountMock).withdrawBalance(any());
            DaoException daoException = new DaoException("B");
            doThrow(daoException).when(connectionMock).setAutoCommit(false);
            doNothing().when(connectionMock).rollback();
            //Act Statement(s)
            final SQLTransactionRollbackException result = assertThrows(SQLTransactionRollbackException.class, () -> {
                target.withdraw(new BigDecimal("0"), accountId);
            });
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));
                assertThat(result.getCause(), is(instanceOf(daoException.getClass())));
                connectionManager.verify(ConnectionManager::getConnection, atLeast(1));
                verify(target).findById(accountId);
                verify(accountMock).withdrawBalance(any());
                verify(connectionMock).setAutoCommit(false);
                verify(transactionDao, never()).create((Transaction) any());
                verify(connectionMock, never()).commit();
                verify(connectionMock).rollback();
            });
        }
    }

    @Test
    void depositTest() throws SQLException, NotFoundException {
        //Arrange Statement(s)
        Account accountMock = mock(Account.class);
        Transaction transactionMock = mock(Transaction.class);
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            target = spy(new AccountDao());
            autoCloseableMocks = openMocks(this);
            doReturn(Optional.of(accountMock)).when(target).findById("2");
            doNothing().when(accountMock).depositBalance(any());
            doNothing().when(target).update(accountMock);
            doReturn(transactionMock).when(transactionDao).create((Transaction) any());
            //Act Statement(s)
            target.deposit(new BigDecimal("100.0"), "2");
            //Assert statement(s)
            assertAll("result", () -> {
                connectionManager.verify(ConnectionManager::getConnection, atLeast(1));
                verify(target).findById("2");
                verify(accountMock).depositBalance(any());
                verify(target).update(accountMock);
                verify(transactionDao).create((Transaction) any());
            });
        }
    }

    @Test()
    void deleteByIdWhenDefaultBranch() throws SQLException {
        //Arrange Statement(s)
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
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
