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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;
import com.github.kaydunov.entity.Bank;
import static org.mockito.Mockito.doNothing;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Disabled;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class BankDaoSapientGeneratedTest {

    private final Connection connectionMock = mock(Connection.class, "connection");

    private AutoCloseable autoCloseableMocks;

    @InjectMocks()
    private BankDao target;

    @AfterEach()
    public void afterTest() throws Exception {
        if (autoCloseableMocks != null)
            autoCloseableMocks.close();
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
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
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
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
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
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
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
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
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
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
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
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
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

    //Sapient generated method id: ${createWhenDefaultBranchAndCaughtSQLException}, hash: 6A3E4773659FEFD85BF9A686303A6BA5
    @Disabled()
    @Test()
    void createWhenDefaultBranchAndCaughtSQLException() throws SQLException {
        /* Branches:
         * (affectedRows == 0) : false
         * (generatedKeys.next()) : true
         * (branch expression (line 47)) : false
         * (branch expression (line 41)) : true
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new BankDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("INSERT INTO bank (name) VALUES (?)", 1);
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

    //Sapient generated method id: ${findByIdWhenDefaultBranchThrowsThrowable}, hash: A76CAA9891BF7BDA3FD641A6B7CC4F97
    @Disabled()
    @Test()
    void findByIdWhenDefaultBranchThrowsThrowable() throws SQLException {
        /* Branches:
         * (resultSet.next()) : false
         * (branch expression (line 57)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new BankDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM bank WHERE id = ?");
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
            verify(connectionMock).prepareStatement("SELECT * FROM bank WHERE id = ?");
            verify(preparedStatementMock).setLong(1, 1L);
            verify(preparedStatementMock).executeQuery();
            verify(resultSetMock).next();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${findByIdWhenCaughtSQLException}, hash: 23430A4257C60EA2015E1B06C8051391
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
        target = new BankDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        //Act Statement(s)
        Optional<Bank> result = target.findById(12345L);
        Bank bank = new Bank();
        bank.setName("name1");
        bank.setId(1L);
        Optional<Bank> bankOptional = Optional.of(bank);
        //Assert statement(s)
        assertAll("result", () -> assertThat(result, equalTo(bankOptional)));
    }

    //Sapient generated method id: ${findByIdWhenCaughtSQLExceptionThrowsDaoException}, hash: C67028D9D20F62E5204F992D59DEE9E5
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
        target = new BankDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM bank WHERE id = ?");
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
            verify(connectionMock).prepareStatement("SELECT * FROM bank WHERE id = ?");
            verify(preparedStatementMock).setLong(1, 1L);
            verify(preparedStatementMock).executeQuery();
            verify(resultSetMock).next();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${findByIdWhenDefaultBranch}, hash: D75A1EEAF767AC695ABF97E27C68712E
    @Disabled()
    @Test()
    void findByIdWhenDefaultBranch() throws SQLException {
        /* Branches:
         * (resultSet.next()) : false
         * (catch-exception (SQLException)) : false
         * (branch expression (line 57)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new BankDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM bank WHERE id = ?");
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

    //Sapient generated method id: ${findAllWhenDefaultBranchThrowsThrowable}, hash: 1196E6F27DB43C4C8C56BCEA9C8B82A2
    @Disabled()
    @Test()
    void findAllWhenDefaultBranchThrowsThrowable() throws SQLException {
        /* Branches:
         * (resultSet.next()) : true
         * (branch expression (line 72)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new BankDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM bank");
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
            verify(connectionMock).prepareStatement("SELECT * FROM bank");
            verify(preparedStatementMock).executeQuery();
            verify(resultSetMock, times(2)).next();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${findAllWhenCaughtSQLException}, hash: 686BDD13719ECAF9F388BEB704655BEB
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
        target = new BankDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        //Act Statement(s)
        List<Bank> result = target.findAll();
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result.size(), equalTo(1));
            assertThat(result.get(0), is(instanceOf(Bank.class)));
        });
    }

    //Sapient generated method id: ${findAllWhenCaughtSQLExceptionThrowsDaoException}, hash: 1EE1B213110CBA4623F6D9CA8F2F9268
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
        target = new BankDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM bank");
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
        /* Branches:
         * (resultSet.next()) : true
         * (catch-exception (SQLException)) : false
         * (branch expression (line 72)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new BankDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM bank");
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

    //Sapient generated method id: ${updateWhenDefaultBranchThrowsThrowable}, hash: D2B3437B73F73E455B676789C3105091
    @Disabled()
    @Test()
    void updateWhenDefaultBranchThrowsThrowable() throws SQLException {
        /* Branches:
         * (branch expression (line 85)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new BankDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("UPDATE bank SET name = ? WHERE id = ?");
        doNothing().when(preparedStatementMock).setString(1, "name1");
        doNothing().when(preparedStatementMock).setLong(2, 1L);
        doReturn(0).when(preparedStatementMock).executeUpdate();
        doNothing().when(preparedStatementMock).close();
        Bank bank = new Bank();
        bank.setName("name1");
        bank.setId(1L);
        //Act Statement(s)
        final Throwable result = assertThrows(Throwable.class, () -> {
            target.update(bank);
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            verify(connectionMock).prepareStatement("UPDATE bank SET name = ? WHERE id = ?");
            verify(preparedStatementMock).setString(1, "name1");
            verify(preparedStatementMock).setLong(2, 1L);
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${updateWhenCaughtSQLException}, hash: D519DCA16C24F19CEEE335DC7BC917AD
    @Test()
    void updateWhenCaughtSQLException() {
        /* Branches:
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new BankDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        Bank bank = new Bank();
        bank.setName("name1");
        bank.setId(1L);
        //Act Statement(s)
        target.update(bank);
    }

    //Sapient generated method id: ${updateWhenCaughtSQLExceptionThrowsDaoException}, hash: BE9F76A9012055DB1ED48C309BAE2276
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
        target = new BankDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("UPDATE bank SET name = ? WHERE id = ?");
        doNothing().when(preparedStatementMock).setString(1, "name1");
        doNothing().when(preparedStatementMock).setLong(2, 1L);
        doReturn(0).when(preparedStatementMock).executeUpdate();
        doNothing().when(preparedStatementMock).close();
        Bank bank = new Bank();
        bank.setName("name1");
        bank.setId(1L);
        SQLException sQLException = new SQLException("message1", "message1", 0);
        DaoException daoException = new DaoException("message1", sQLException);
        //Act Statement(s)
        final DaoException result = assertThrows(DaoException.class, () -> {
            target.update(bank);
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            assertThat(result.getMessage(), equalTo(daoException.getMessage()));
            assertThat(result.getCause(), is(instanceOf(sQLException.getClass())));
            verify(connectionMock).prepareStatement("UPDATE bank SET name = ? WHERE id = ?");
            verify(preparedStatementMock).setString(1, "name1");
            verify(preparedStatementMock).setLong(2, 1L);
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${updateWhenDefaultBranch}, hash: E6D34B19887CB44E18B6CAF84BA219EF
    @Disabled()
    @Test()
    void updateWhenDefaultBranch() throws SQLException {
        /* Branches:
         * (catch-exception (SQLException)) : false
         * (branch expression (line 85)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new BankDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("UPDATE bank SET name = ? WHERE id = ?");
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

    //Sapient generated method id: ${deleteByIdWhenDefaultBranchThrowsThrowable}, hash: 3A0747A3E0C225D781CAB25AE93A14A4
    @Disabled()
    @Test()
    void deleteByIdWhenDefaultBranchThrowsThrowable() throws SQLException {
        /* Branches:
         * (branch expression (line 96)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new BankDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("DELETE FROM bank WHERE id = ?");
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
            verify(connectionMock).prepareStatement("DELETE FROM bank WHERE id = ?");
            verify(preparedStatementMock).setLong(1, 1L);
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${deleteByIdWhenCaughtSQLException}, hash: B97C97023EA4444E889F411B78989057
    @Test()
    void deleteByIdWhenCaughtSQLException() {
        /* Branches:
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new BankDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        //Act Statement(s)
        target.deleteById(12345L);
    }

    //Sapient generated method id: ${deleteByIdWhenCaughtSQLExceptionThrowsDaoException}, hash: 4457717A00AFE2C44005CD086F8796C5
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
        target = new BankDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("DELETE FROM bank WHERE id = ?");
        doNothing().when(preparedStatementMock).setLong(1, 1L);
        doReturn(0).when(preparedStatementMock).executeUpdate();
        doNothing().when(preparedStatementMock).close();
        SQLException sQLException = new SQLException("message1", "message1", 0);
        DaoException daoException = new DaoException("message1", sQLException);
        //Act Statement(s)
        final DaoException result = assertThrows(DaoException.class, () -> {
            target.deleteById(1L);
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            assertThat(result.getMessage(), equalTo(daoException.getMessage()));
            assertThat(result.getCause(), is(instanceOf(sQLException.getClass())));
            verify(connectionMock).prepareStatement("DELETE FROM bank WHERE id = ?");
            verify(preparedStatementMock).setLong(1, 1L);
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${deleteByIdWhenDefaultBranch}, hash: 7E7257E523A74788A03246A3DA2E73A9
    @Disabled()
    @Test()
    void deleteByIdWhenDefaultBranch() throws SQLException {
        /* Branches:
         * (catch-exception (SQLException)) : false
         * (branch expression (line 96)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new BankDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("DELETE FROM bank WHERE id = ?");
        doNothing().when(preparedStatementMock).setLong(1, 1L);
        doReturn(0).when(preparedStatementMock).executeUpdate();
        doNothing().when(preparedStatementMock).close();
        //Act Statement(s)
        target.deleteById(1L);
        //Assert statement(s)
        assertAll("result", () -> {
            verify(connectionMock).prepareStatement("DELETE FROM bank WHERE id = ?");
            verify(preparedStatementMock).setLong(1, 1L);
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).close();
        });
    }
}
