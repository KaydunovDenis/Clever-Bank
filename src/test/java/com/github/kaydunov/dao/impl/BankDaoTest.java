package com.github.kaydunov.dao.impl;

import com.github.kaydunov.dao.ConnectionManager;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import org.mockito.MockedStatic;
import com.github.kaydunov.exception.DaoException;

import java.util.Optional;

import com.github.kaydunov.entity.Bank;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class BankDaoTest {

    private Connection connectionMock;

    private PreparedStatement preparedStatementMock;
    private BankDao target;

    @BeforeEach
    void setUp() throws SQLException {
        // Mocking Connection and PreparedStatement


        // Use Mockito's static mocking to mock the static method `ConnectionManager.getConnection()`
        try (MockedStatic<ConnectionManager> mockedConnectionManager = mockStatic(ConnectionManager.class)) {
            preparedStatementMock = mock(PreparedStatement.class);
            connectionMock = mock(Connection.class);
            mockedConnectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);

            // Stubbing connection.prepareStatement to return the mocked PreparedStatement
            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(connectionMock.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatementMock);

            // Creating the instance of AccountPercentDao (this should pick up the mocked connection)
            target = new BankDao();
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        preparedStatementMock.close();
        connectionMock.close();
    }

    @Test()
    void createWhenDefaultBranchThrowsDaoException() {
        Bank bank = new Bank();
        bank.setName("name1");
        SQLException sQLExceptionMock = mock(SQLException.class);
        DaoException daoException = new DaoException("Creating bank failed, no rows affected.", sQLExceptionMock);
        SQLException sQLException = new SQLException("Creating bank failed, no rows affected.");
        //Act Statement(s)
        final DaoException result = assertThrows(DaoException.class, () -> {
            target.create(bank);
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            assertThat(result.getMessage(), equalTo(daoException.getMessage()));
            assertThat(result.getCause(), is(instanceOf(sQLException.getClass())));
        });
    }

    @Test()
    void createWhenAffectedRowsEquals0AndDefaultBranchThrowsDaoException() throws SQLException {

        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("INSERT INTO bank (name) VALUES (?)", 1);
        doNothing().when(preparedStatementMock).setString(1, "name1");
        doReturn(0).when(preparedStatementMock).executeUpdate();
        doNothing().when(preparedStatementMock).close();
        Bank bank = new Bank();
        bank.setName("name1");
        SQLException sQLExceptionMock = mock(SQLException.class);
        DaoException daoException = new DaoException("Creating bank failed, no rows affected.", sQLExceptionMock);
        SQLException sQLException = new SQLException("Creating bank failed, no rows affected.");
        //Act Statement(s)
        final DaoException result = assertThrows(DaoException.class, () -> {
            target.create(bank);
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            assertThat(result.getMessage(), equalTo(daoException.getMessage()));
            assertThat(result.getCause(), is(instanceOf(sQLException.getClass())));
            verify(connectionMock).prepareStatement("INSERT INTO bank (name) VALUES (?)", 1);
            verify(preparedStatementMock).setString(1, "name1");
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).close();
        });
    }

    @Test()
    void createWhenGeneratedKeysNotNextAndDefaultBranchAndDefaultBranchThrowsDaoException() throws SQLException {
        Bank bank = new Bank();
        bank.setName("name1");
        SQLException sQLExceptionMock = mock(SQLException.class);
        DaoException daoException = new DaoException("Creating bank failed, no ID obtained.", sQLExceptionMock);
        SQLException sQLException = new SQLException("Creating bank failed, no ID obtained.");

        doNothing().when(preparedStatementMock).setString(1, "name1");
        doReturn(-1).when(preparedStatementMock).executeUpdate();
        ResultSet resultSetMock = mock(ResultSet.class);
        doThrow(daoException).when(preparedStatementMock).getGeneratedKeys();
        //Act Statement(s)
        final DaoException result = assertThrows(DaoException.class, () -> {
            target.create(bank);
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            assertThat(result.getMessage(), equalTo(daoException.getMessage()));
            assertThat(result.getCause(), is(instanceOf(sQLException.getClass())));
        });
    }

    @Test()
    void createWhenAffectedRowsNotEquals0AndGeneratedKeysNotNextAndDefaultBranchAndDefaultBranchThrowsDaoException() throws SQLException {
        SQLException sQLExceptionMock = mock(SQLException.class);
        DaoException daoException = new DaoException("Creating bank failed, no ID obtained.", sQLExceptionMock);
        SQLException sQLException = new SQLException("Creating bank failed, no ID obtained.");

        doNothing().when(preparedStatementMock).setString(1, "name1");
        doReturn(-1).when(preparedStatementMock).executeUpdate();
        ResultSet resultSetMock = mock(ResultSet.class);
        doReturn(resultSetMock).when(preparedStatementMock).getGeneratedKeys();
        doReturn(false).when(resultSetMock).next();
        doNothing().when(resultSetMock).close();
        doThrow(sQLExceptionMock).when(preparedStatementMock).close();
        Bank bank = new Bank();
        bank.setName("name1");
        //Act Statement(s)
        final DaoException result = assertThrows(DaoException.class, () -> {
            target.create(bank);
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            assertThat(result.getMessage(), equalTo(daoException.getMessage()));
            assertThat(result.getCause(), is(instanceOf(sQLException.getClass())));
            verify(connectionMock).prepareStatement("INSERT INTO bank (name) VALUES (?)", 1);
            verify(preparedStatementMock).setString(1, "name1");
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).getGeneratedKeys();
            verify(resultSetMock).next();
            verify(resultSetMock).close();
            verify(preparedStatementMock).close();
        });
    }

    @Test()
    void createWhenAffectedRowsNotEquals0AndGeneratedKeysNotNextAndDefaultBranchAndDefaultBranch5ThrowsDaoException() throws SQLException {
        SQLException sQLExceptionMock = mock(SQLException.class);
        DaoException daoException = new DaoException("Creating bank failed, no ID obtained.", sQLExceptionMock);
        SQLException sQLException = new SQLException("Creating bank failed, no ID obtained.");

        doNothing().when(preparedStatementMock).setString(1, "name1");
        doReturn(-1).when(preparedStatementMock).executeUpdate();
        ResultSet resultSetMock = mock(ResultSet.class);
        doReturn(resultSetMock).when(preparedStatementMock).getGeneratedKeys();
        doReturn(false).when(resultSetMock).next();
        doNothing().when(resultSetMock).close();
        doThrow(sQLExceptionMock).when(preparedStatementMock).close();
        Bank bank = new Bank();
        bank.setName("name1");

        //Act Statement(s)
        final DaoException result = assertThrows(DaoException.class, () -> {
            target.create(bank);
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            assertThat(result.getMessage(), equalTo(daoException.getMessage()));
            assertThat(result.getCause(), is(instanceOf(sQLException.getClass())));
            verify(connectionMock).prepareStatement("INSERT INTO bank (name) VALUES (?)", 1);
            verify(preparedStatementMock).setString(1, "name1");
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).getGeneratedKeys();
            verify(resultSetMock).next();
            verify(resultSetMock).close();
            verify(preparedStatementMock).close();
        });
    }

    @Test()
    void createWhenDefaultBranchAndCaughtSQLException() throws SQLException {
        doNothing().when(preparedStatementMock).setString(1, "name1");
        doReturn(1).when(preparedStatementMock).executeUpdate();
        ResultSet resultSetMock = mock(ResultSet.class);
        doReturn(resultSetMock).when(preparedStatementMock).getGeneratedKeys();
        doReturn(true).when(resultSetMock).next();
        doReturn(1L).when(resultSetMock).getLong(1);
        doNothing().when(resultSetMock).close();
        doNothing().when(preparedStatementMock).close();

        Bank bank = new Bank();
        bank.setName("name1");
        bank.setId(1L);
        //Act Statement(s)
        Bank result = target.create(bank);
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, equalTo(bank));
            verify(connectionMock).prepareStatement("INSERT INTO bank (name) VALUES (?)", 1);
            verify(preparedStatementMock).setString(1, "name1");
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).getGeneratedKeys();
            verify(resultSetMock).next();
            verify(resultSetMock).getLong(1);
            verify(resultSetMock).close();
            verify(preparedStatementMock).close();
        });
    }

    @Test()
    void findByIdWhenCaughtSQLExceptionThrowsDaoException() throws SQLException {
        doNothing().when(preparedStatementMock).setLong(1, 1L);
        ResultSet resultSetMock = mock(ResultSet.class);
        doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
        doReturn(false).when(resultSetMock).next();
        SQLException sqlException = new SQLException("message1", "message1", 0);
        doThrow(sqlException).when(preparedStatementMock).close();
        DaoException daoException = new DaoException("message1", sqlException);
        //Act Statement(s)
        final DaoException result = assertThrows(DaoException.class, () -> {
            target.findById(1L);
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            assertThat(result.getMessage(), equalTo(daoException.getMessage()));
            assertThat(result.getCause(), is(instanceOf(sqlException.getClass())));
            verify(connectionMock).prepareStatement("SELECT * FROM bank WHERE id = ?");
            verify(preparedStatementMock).setLong(1, 1L);
            verify(preparedStatementMock).executeQuery();
            verify(resultSetMock).next();
            verify(preparedStatementMock).close();
        });
    }

    @Test()
    void findByIdWhenDefaultBranch() throws SQLException {
        doNothing().when(preparedStatementMock).setLong(1, 1L);
        ResultSet resultSetMock = mock(ResultSet.class);
        doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
        doReturn(false).when(resultSetMock).next();
        doNothing().when(preparedStatementMock).close();
        //Act Statement(s)
        Optional<Bank> result = target.findById(1L);
        Optional<Bank> bankOptional = Optional.empty();
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, equalTo(bankOptional));
            verify(connectionMock).prepareStatement("SELECT * FROM bank WHERE id = ?");
            verify(preparedStatementMock).setLong(1, 1L);
            verify(preparedStatementMock).executeQuery();
            verify(resultSetMock).next();
            verify(preparedStatementMock).close();
        });
    }

    @Test()
    void findAllWhenCaughtSQLExceptionThrowsDaoException() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);

        doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
        doReturn(true, false).when(resultSetMock).next();
        SQLException sqlException = new SQLException("message1", "message1", 0);
        doThrow(sqlException).when(preparedStatementMock).close();
        DaoException daoException = new DaoException("message1", sqlException);
        //Act Statement(s)
        final DaoException result = assertThrows(DaoException.class, () -> {
            target.findAll();
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            assertThat(result.getMessage(), equalTo(daoException.getMessage()));
            assertThat(result.getCause(), is(instanceOf(sqlException.getClass())));
            verify(connectionMock).prepareStatement("SELECT * FROM bank");
            verify(preparedStatementMock).executeQuery();
            verify(resultSetMock, times(2)).next();
            verify(preparedStatementMock).close();
        });
    }

    @Test()
    void findAllWhenDefaultBranch() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);
        doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
        doReturn(true, false).when(resultSetMock).next();
        doNothing().when(preparedStatementMock).close();
        //Act Statement(s)
        List<Bank> result = target.findAll();
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result.size(), equalTo(1));
            assertThat(result.get(0), is(instanceOf(Bank.class)));
            verify(connectionMock).prepareStatement("SELECT * FROM bank");
            verify(preparedStatementMock).executeQuery();
            verify(resultSetMock, times(2)).next();
            verify(preparedStatementMock).close();
        });
    }

    @Test()
    void updateWhenCaughtSQLExceptionThrowsDaoException() throws SQLException {
        doNothing().when(preparedStatementMock).setString(1, "name1");
        doNothing().when(preparedStatementMock).setLong(2, 1L);
        SQLException sqlException = new SQLException("message1", "message1", 0);
        doThrow(sqlException).when(preparedStatementMock).executeUpdate();
        doNothing().when(preparedStatementMock).close();
        Bank bank = new Bank();
        bank.setName("name1");
        bank.setId(1L);
        DaoException daoException = new DaoException("message1", sqlException);
        //Act Statement(s)
        final DaoException result = assertThrows(DaoException.class, () -> {
            target.update(bank);
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            assertThat(result.getMessage(), equalTo(daoException.getMessage()));
            assertThat(result.getCause(), is(instanceOf(sqlException.getClass())));
            verify(connectionMock).prepareStatement("UPDATE bank SET name = ? WHERE id = ?");
            verify(preparedStatementMock).setString(1, "name1");
            verify(preparedStatementMock).setLong(2, 1L);
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).close();
        });
    }

    @Test()
    void updateWhenDefaultBranch() throws SQLException {
        doNothing().when(preparedStatementMock).setString(1, "name1");
        doNothing().when(preparedStatementMock).setLong(2, 1L);
        doReturn(0).when(preparedStatementMock).executeUpdate();
        doNothing().when(preparedStatementMock).close();
        Bank bank = new Bank();
        bank.setName("name1");
        bank.setId(1L);
        //Act Statement(s)
        target.update(bank);
        //Assert statement(s)
        assertAll("result", () -> {
            verify(connectionMock).prepareStatement("UPDATE bank SET name = ? WHERE id = ?");
            verify(preparedStatementMock).setString(1, "name1");
            verify(preparedStatementMock).setLong(2, 1L);
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).close();
        });
    }

    @Test
    void deleteByIdWhenCaughtSQLExceptionThrowsDaoException() throws SQLException {
        doNothing().when(preparedStatementMock).setLong(1, 1L);

        SQLException sqlException = new SQLException("message1", "message1", 0);
        doThrow(sqlException).when(preparedStatementMock).executeUpdate();
        doNothing().when(preparedStatementMock).close();

        DaoException daoException = new DaoException("message1", sqlException);
        //Act Statement(s)
        final DaoException result = assertThrows(DaoException.class, () -> {
            target.deleteById(1L);
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            assertThat(result.getMessage(), equalTo(daoException.getMessage()));
            assertThat(result.getCause(), is(instanceOf(sqlException.getClass())));
            verify(connectionMock).prepareStatement("DELETE FROM bank WHERE id = ?");
            verify(preparedStatementMock).setLong(1, 1L);
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).close();
        });
    }

    @Test
    void deleteByIdWhenDefaultBranch() throws SQLException {
        //Arrange Statement(s)
        doNothing().when(preparedStatementMock).setLong(1, 1L);
        doNothing().when(preparedStatementMock).close();
        //Act Statement(s)
        target.deleteById(1L);
        //Assert statement(s)
        assertAll("result", () -> {
            verify(connectionMock).prepareStatement(anyString());
            verify(preparedStatementMock).setLong(1, 1L);
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).close();
        });
    }
}
