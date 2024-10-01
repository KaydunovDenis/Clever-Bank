package com.github.kaydunov.dao.impl;

import com.github.kaydunov.dao.ConnectionManager;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;
import java.sql.ResultSet;

import org.mockito.MockedStatic;
import com.github.kaydunov.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    private final Connection connectionMock = mock(Connection.class, "connection");

    private PreparedStatement preparedStatementMock;
    private BankDao target;

    @BeforeEach
    void setUp() throws SQLException {
        // Mocking Connection and PreparedStatement
        preparedStatementMock = mock(PreparedStatement.class);

        // Use Mockito's static mocking to mock the static method `ConnectionManager.getConnection()`
        try (MockedStatic<ConnectionManager> mockedConnectionManager = mockStatic(ConnectionManager.class)) {
            mockedConnectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);

            // Stubbing connection.prepareStatement to return the mocked PreparedStatement
            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);

            // Creating the instance of AccountPercentDao (this should pick up the mocked connection)
            target = new BankDao();
        }
    }

    //Sapient generated method id: ${createWhenDefaultBranchThrowsDaoException}, hash: EB1BD0B4FF69B41C567AD6ECAE14244C
    @Disabled()
    @Test()
    void createWhenDefaultBranchThrowsDaoException() {
        /* Branches:
         * (affectedRows == 0) : true
         * (branch expression (line 33)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new BankDao();

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

    //Sapient generated method id: ${createWhenAffectedRowsEquals0AndDefaultBranchThrowsDaoException}, hash: 01C96164EC224E6C4152990B19D77C0A
    @Disabled()
    @Test()
    void createWhenAffectedRowsEquals0AndDefaultBranchThrowsDaoException() throws SQLException {
        /* Branches:
         * (affectedRows == 0) : true
         * (branch expression (line 33)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new BankDao();

        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
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

    //Sapient generated method id: ${createWhenCaughtSQLException}, hash: 8B8F15772E035B175008871646CBF2B2
    @Test()
    void createWhenCaughtSQLException() {
        /* Branches:
         * (affectedRows == 0) : false
         * (generatedKeys.next()) : true
         * (branch expression (line 47)) : false
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new BankDao();

        Bank bank = new Bank();
        bank.setName("name1");
        bank.setId(1L);
        //Act Statement(s)
        Bank result = target.create(bank);
        //Assert statement(s)
        assertAll("result", () -> assertThat(result, equalTo(bank)));
    }

    //Sapient generated method id: ${createWhenGeneratedKeysNotNextAndDefaultBranchAndDefaultBranchThrowsDaoException}, hash: 0341EB131BCD4945446BCD7FD7980173
    @Disabled()
    @Test()
    void createWhenGeneratedKeysNotNextAndDefaultBranchAndDefaultBranchThrowsDaoException() {
        /* Branches:
         * (affectedRows == 0) : false
         * (generatedKeys.next()) : false
         * (branch expression (line 41)) : false
         * (branch expression (line 33)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new BankDao();

        Bank bank = new Bank();
        bank.setName("name1");
        SQLException sQLExceptionMock = mock(SQLException.class);
        DaoException daoException = new DaoException("Creating bank failed, no ID obtained.", sQLExceptionMock);
        SQLException sQLException = new SQLException("Creating bank failed, no ID obtained.");
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

    //Sapient generated method id: ${createWhenAffectedRowsNotEquals0AndGeneratedKeysNotNextAndDefaultBranchAndDefaultBranchThrowsDaoException}, hash: D094292B07520426DC7B7DA4D8619AB8
    @Disabled()
    @Test()
    void createWhenAffectedRowsNotEquals0AndGeneratedKeysNotNextAndDefaultBranchAndDefaultBranchThrowsDaoException() throws SQLException {
        /* Branches:
         * (affectedRows == 0) : false
         * (generatedKeys.next()) : false
         * (branch expression (line 41)) : false
         * (branch expression (line 33)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new BankDao();

        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("INSERT INTO bank (name) VALUES (?)", 1);
        doNothing().when(preparedStatementMock).setString(1, "name1");
        doReturn(-1).when(preparedStatementMock).executeUpdate();
        ResultSet resultSetMock = mock(ResultSet.class);
        doReturn(resultSetMock).when(preparedStatementMock).getGeneratedKeys();
        doReturn(false).when(resultSetMock).next();
        doNothing().when(resultSetMock).close();
        doNothing().when(preparedStatementMock).close();
        Bank bank = new Bank();
        bank.setName("name1");
        SQLException sQLExceptionMock = mock(SQLException.class);
        DaoException daoException = new DaoException("Creating bank failed, no ID obtained.", sQLExceptionMock);
        SQLException sQLException = new SQLException("Creating bank failed, no ID obtained.");
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

    //Sapient generated method id: ${createWhenAffectedRowsNotEquals0AndGeneratedKeysNotNextAndDefaultBranchAndDefaultBranch5ThrowsDaoException}, hash: FEF3EE9710356F07BD8C80114E14E94E
    @Disabled()
    @Test()
    void createWhenAffectedRowsNotEquals0AndGeneratedKeysNotNextAndDefaultBranchAndDefaultBranch5ThrowsDaoException() throws SQLException {
        /* Branches:
         * (affectedRows == 0) : false
         * (generatedKeys.next()) : false
         * (branch expression (line 41)) : false
         * (branch expression (line 33)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new BankDao();

        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("INSERT INTO bank (name) VALUES (?)", 1);
        doNothing().when(preparedStatementMock).setString(1, "name1");
        doReturn(-1).when(preparedStatementMock).executeUpdate();
        ResultSet resultSetMock = mock(ResultSet.class);
        doReturn(resultSetMock).when(preparedStatementMock).getGeneratedKeys();
        doReturn(false).when(resultSetMock).next();
        doNothing().when(resultSetMock).close();
        doNothing().when(preparedStatementMock).close();
        Bank bank = new Bank();
        bank.setName("name1");
        SQLException sQLExceptionMock = mock(SQLException.class);
        DaoException daoException = new DaoException("Creating bank failed, no ID obtained.", sQLExceptionMock);
        SQLException sQLException = new SQLException("Creating bank failed, no ID obtained.");
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

    //Sapient generated method id: ${findAllWhenCaughtSQLExceptionThrowsDaoException}, hash: 1EE1B213110CBA4623F6D9CA8F2F9268
    @Disabled()
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

    //Sapient generated method id: ${findAllWhenDefaultBranch}, hash: D8A18D48E627195500B527C40A992785
    @Disabled()
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
