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
import com.github.kaydunov.entity.User;
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
class UserDaoSapientGeneratedTest {

    private final Connection connectionMock = mock(Connection.class, "connection");

    private AutoCloseable autoCloseableMocks;

    @InjectMocks()
    private UserDao target;

    @AfterEach()
    public void afterTest() throws Exception {
        if (autoCloseableMocks != null)
            autoCloseableMocks.close();
    }

    //Sapient generated method id: ${createWhenCaughtSQLException}, hash: 0C7FFFA106B23834DA9CCA08EB6F3B9E
    @Disabled()
    @Test()
    void createWhenCaughtSQLException() throws SQLException {
        /* Branches:
         * (affectedRows > 0) : false
         * (branch expression (line 39)) : true
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new UserDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("INSERT INTO user_ (name, email) VALUES (?, ?)", 1);
        doNothing().when(preparedStatementMock).setString(1, "name1");
        doNothing().when(preparedStatementMock).setString(2, "email1");
        doReturn(-1).when(preparedStatementMock).executeUpdate();
        doNothing().when(preparedStatementMock).close();
        User user = new User();
        user.setName("name1");
        user.setEmail("email1");
        //Act Statement(s)
        User result = target.create(user);
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, equalTo(user));
            verify(connectionMock).prepareStatement("INSERT INTO user_ (name, email) VALUES (?, ?)", 1);
            verify(preparedStatementMock).setString(1, "name1");
            verify(preparedStatementMock).setString(2, "email1");
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${createWhenDefaultBranchThrowsThrowable}, hash: DB72026DBADC17B3163B8FC64FDD0338
    @Disabled()
    @Test()
    void createWhenDefaultBranchThrowsThrowable() throws SQLException {
        /* Branches:
         * (affectedRows > 0) : true
         * (generatedKeys.next()) : true
         * (branch expression (line 39)) : true
         * (branch expression (line 33)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new UserDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("INSERT INTO user_ (name, email) VALUES (?, ?)", 1);
        doNothing().when(preparedStatementMock).setString(1, "name1");
        doNothing().when(preparedStatementMock).setString(2, "email1");
        doReturn(1).when(preparedStatementMock).executeUpdate();
        ResultSet resultSetMock = mock(ResultSet.class);
        doReturn(resultSetMock).when(preparedStatementMock).getGeneratedKeys();
        doReturn(true).when(resultSetMock).next();
        doReturn(1L).when(resultSetMock).getLong(1);
        doNothing().when(resultSetMock).close();
        doNothing().when(preparedStatementMock).close();
        User user = new User();
        user.setName("name1");
        user.setId(1L);
        user.setEmail("email1");
        //Act Statement(s)
        final Throwable result = assertThrows(Throwable.class, () -> {
            target.create(user);
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            verify(connectionMock).prepareStatement("INSERT INTO user_ (name, email) VALUES (?, ?)", 1);
            verify(preparedStatementMock).setString(1, "name1");
            verify(preparedStatementMock).setString(2, "email1");
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).getGeneratedKeys();
            verify(resultSetMock).next();
            verify(resultSetMock).getLong(1);
            verify(resultSetMock).close();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${createWhenGeneratedKeysNextAndDefaultBranchAndCaughtSQLException}, hash: 99E555ABECDAEC993CB91EBEAC908E07
    @Test()
    void createWhenGeneratedKeysNextAndDefaultBranchAndCaughtSQLException() {
        /* Branches:
         * (affectedRows > 0) : true
         * (generatedKeys.next()) : true
         * (branch expression (line 43)) : false
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new UserDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        User user = new User();
        user.setName("name1");
        user.setId(1L);
        user.setEmail("email1");
        //Act Statement(s)
        User result = target.create(user);
        //Assert statement(s)
        assertAll("result", () -> assertThat(result, equalTo(user)));
    }

    //Sapient generated method id: ${createWhenDefaultBranchAndCaughtSQLExceptionThrowsDaoException}, hash: AC0BDF6BE7377DA6D60D29117B01D564
    @Disabled()
    @Test()
    void createWhenDefaultBranchAndCaughtSQLExceptionThrowsDaoException() throws SQLException {
        /* Branches:
         * (affectedRows > 0) : true
         * (generatedKeys.next()) : true
         * (branch expression (line 43)) : false
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new UserDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("INSERT INTO user_ (name, email) VALUES (?, ?)", 1);
        doNothing().when(preparedStatementMock).setString(1, "name1");
        doNothing().when(preparedStatementMock).setString(2, "email1");
        doReturn(1).when(preparedStatementMock).executeUpdate();
        ResultSet resultSetMock = mock(ResultSet.class);
        doReturn(resultSetMock).when(preparedStatementMock).getGeneratedKeys();
        doReturn(true).when(resultSetMock).next();
        doReturn(1L).when(resultSetMock).getLong(1);
        doNothing().when(resultSetMock).close();
        doNothing().when(preparedStatementMock).close();
        User user = new User();
        user.setName("name1");
        user.setId(1L);
        user.setEmail("email1");
        SQLException sQLException = new SQLException("message1", "message1", 0);
        DaoException daoException = new DaoException("message1", sQLException);
        //Act Statement(s)
        final DaoException result = assertThrows(DaoException.class, () -> {
            target.create(user);
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            assertThat(result.getMessage(), equalTo(daoException.getMessage()));
            assertThat(result.getCause(), is(instanceOf(sQLException.getClass())));
            verify(connectionMock).prepareStatement("INSERT INTO user_ (name, email) VALUES (?, ?)", 1);
            verify(preparedStatementMock).setString(1, "name1");
            verify(preparedStatementMock).setString(2, "email1");
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).getGeneratedKeys();
            verify(resultSetMock).next();
            verify(resultSetMock).getLong(1);
            verify(resultSetMock).close();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${findByIdWhenDefaultBranchThrowsThrowable}, hash: C46B2E75B2FE42CE8DB990306A41CE3A
    @Disabled()
    @Test()
    void findByIdWhenDefaultBranchThrowsThrowable() throws SQLException {
        /* Branches:
         * (resultSet.next()) : false
         * (branch expression (line 54)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new UserDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM user_ WHERE id = ?");
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
            verify(connectionMock).prepareStatement("SELECT * FROM user_ WHERE id = ?");
            verify(preparedStatementMock).setLong(1, 1L);
            verify(preparedStatementMock).executeQuery();
            verify(resultSetMock).next();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${findByIdWhenCaughtSQLException}, hash: A7B11843F5209BD560BE4D0BE68936B4
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
        target = new UserDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        //Act Statement(s)
        Optional<User> result = target.findById(12345L);
        User user = new User();
        user.setName("name1");
        user.setId(1L);
        user.setEmail("email1");
        Optional<User> userOptional = Optional.of(user);
        //Assert statement(s)
        assertAll("result", () -> assertThat(result, equalTo(userOptional)));
    }

    //Sapient generated method id: ${findByIdWhenCaughtSQLExceptionThrowsDaoException}, hash: 8F8674C18B579EADA7FC22EFD1B7762E
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
        target = new UserDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM user_ WHERE id = ?");
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
            verify(connectionMock).prepareStatement("SELECT * FROM user_ WHERE id = ?");
            verify(preparedStatementMock).setLong(1, 1L);
            verify(preparedStatementMock).executeQuery();
            verify(resultSetMock).next();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${findByIdWhenDefaultBranch}, hash: 245A91099CD454C3983DE21C9497199D
    @Disabled()
    @Test()
    void findByIdWhenDefaultBranch() throws SQLException {
        /* Branches:
         * (resultSet.next()) : false
         * (catch-exception (SQLException)) : false
         * (branch expression (line 54)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new UserDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM user_ WHERE id = ?");
        doNothing().when(preparedStatementMock).setLong(1, 1L);
        ResultSet resultSetMock = mock(ResultSet.class);
        doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
        doReturn(false).when(resultSetMock).next();
        doNothing().when(preparedStatementMock).close();
        //Act Statement(s)
        Optional<User> result = target.findById(1L);
        Optional<User> userOptional = Optional.empty();
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, equalTo(userOptional));
            verify(connectionMock).prepareStatement("SELECT * FROM user_ WHERE id = ?");
            verify(preparedStatementMock).setLong(1, 1L);
            verify(preparedStatementMock).executeQuery();
            verify(resultSetMock).next();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${findAllWhenDefaultBranchThrowsThrowable}, hash: 52944C6565198F8C7342FC5E788BB082
    @Disabled()
    @Test()
    void findAllWhenDefaultBranchThrowsThrowable() throws SQLException {
        /* Branches:
         * (resultSet.next()) : true
         * (branch expression (line 69)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new UserDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM user_");
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
            verify(connectionMock).prepareStatement("SELECT * FROM user_");
            verify(preparedStatementMock).executeQuery();
            verify(resultSetMock, times(2)).next();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${findAllWhenCaughtSQLException}, hash: 8F5D43615508366837CE4DEC0775039D
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
        target = new UserDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        //Act Statement(s)
        List<User> result = target.findAll();
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result.size(), equalTo(1));
            assertThat(result.get(0), is(instanceOf(User.class)));
        });
    }

    //Sapient generated method id: ${findAllWhenCaughtSQLExceptionThrowsDaoException}, hash: 4947947B4C417637F53EE0991AC98611
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
        target = new UserDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM user_");
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
            verify(connectionMock).prepareStatement("SELECT * FROM user_");
            verify(preparedStatementMock).executeQuery();
            verify(resultSetMock, times(2)).next();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${findAllWhenDefaultBranch}, hash: 4A6B8121D51D605F08DD06EF15BEA5AE
    @Disabled()
    @Test()
    void findAllWhenDefaultBranch() throws SQLException {
        /* Branches:
         * (resultSet.next()) : true
         * (catch-exception (SQLException)) : false
         * (branch expression (line 69)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new UserDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM user_");
        ResultSet resultSetMock = mock(ResultSet.class);
        doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
        doReturn(true, false).when(resultSetMock).next();
        doNothing().when(preparedStatementMock).close();
        //Act Statement(s)
        List<User> result = target.findAll();
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result.size(), equalTo(1));
            assertThat(result.get(0), is(instanceOf(User.class)));
            verify(connectionMock).prepareStatement("SELECT * FROM user_");
            verify(preparedStatementMock).executeQuery();
            verify(resultSetMock, times(2)).next();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${updateWhenDefaultBranchThrowsThrowable}, hash: EAF1E5F470E9932B646942A8F88415EA
    @Disabled()
    @Test()
    void updateWhenDefaultBranchThrowsThrowable() throws SQLException {
        /* Branches:
         * (branch expression (line 82)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new UserDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("UPDATE user_ SET name = ?, email = ? WHERE id = ?");
        doNothing().when(preparedStatementMock).setString(1, "name1");
        doNothing().when(preparedStatementMock).setString(2, "email1");
        doNothing().when(preparedStatementMock).setLong(3, 1L);
        doReturn(0).when(preparedStatementMock).executeUpdate();
        doNothing().when(preparedStatementMock).close();
        User user = new User();
        user.setName("name1");
        user.setId(1L);
        user.setEmail("email1");
        //Act Statement(s)
        final Throwable result = assertThrows(Throwable.class, () -> {
            target.update(user);
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            verify(connectionMock).prepareStatement("UPDATE user_ SET name = ?, email = ? WHERE id = ?");
            verify(preparedStatementMock).setString(1, "name1");
            verify(preparedStatementMock).setString(2, "email1");
            verify(preparedStatementMock).setLong(3, 1L);
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${updateWhenCaughtSQLException}, hash: 63F8BF58D3C243BACAF8F20BD1399643
    @Test()
    void updateWhenCaughtSQLException() {
        /* Branches:
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new UserDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        User user = new User();
        user.setName("name1");
        user.setId(1L);
        user.setEmail("email1");
        //Act Statement(s)
        target.update(user);
    }

    //Sapient generated method id: ${updateWhenCaughtSQLExceptionThrowsDaoException}, hash: 15FB8E539B19EDAFE06CAEE29D623262
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
        target = new UserDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("UPDATE user_ SET name = ?, email = ? WHERE id = ?");
        doNothing().when(preparedStatementMock).setString(1, "name1");
        doNothing().when(preparedStatementMock).setString(2, "email1");
        doNothing().when(preparedStatementMock).setLong(3, 1L);
        doReturn(0).when(preparedStatementMock).executeUpdate();
        doNothing().when(preparedStatementMock).close();
        User user = new User();
        user.setName("name1");
        user.setId(1L);
        user.setEmail("email1");
        SQLException sQLException = new SQLException("message1", "message1", 0);
        DaoException daoException = new DaoException("message1", sQLException);
        //Act Statement(s)
        final DaoException result = assertThrows(DaoException.class, () -> {
            target.update(user);
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            assertThat(result.getMessage(), equalTo(daoException.getMessage()));
            assertThat(result.getCause(), is(instanceOf(sQLException.getClass())));
            verify(connectionMock).prepareStatement("UPDATE user_ SET name = ?, email = ? WHERE id = ?");
            verify(preparedStatementMock).setString(1, "name1");
            verify(preparedStatementMock).setString(2, "email1");
            verify(preparedStatementMock).setLong(3, 1L);
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${updateWhenDefaultBranch}, hash: CA6100334FF53145BD3CD5EE926FF472
    @Disabled()
    @Test()
    void updateWhenDefaultBranch() throws SQLException {
        /* Branches:
         * (catch-exception (SQLException)) : false
         * (branch expression (line 82)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new UserDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("UPDATE user_ SET name = ?, email = ? WHERE id = ?");
        doNothing().when(preparedStatementMock).setString(1, "name1");
        doNothing().when(preparedStatementMock).setString(2, "email1");
        doNothing().when(preparedStatementMock).setLong(3, 1L);
        doReturn(0).when(preparedStatementMock).executeUpdate();
        doNothing().when(preparedStatementMock).close();
        User user = new User();
        user.setName("name1");
        user.setId(1L);
        user.setEmail("email1");
        //Act Statement(s)
        target.update(user);
        //Assert statement(s)
        assertAll("result", () -> {
            verify(connectionMock).prepareStatement("UPDATE user_ SET name = ?, email = ? WHERE id = ?");
            verify(preparedStatementMock).setString(1, "name1");
            verify(preparedStatementMock).setString(2, "email1");
            verify(preparedStatementMock).setLong(3, 1L);
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${deleteByIdWhenDefaultBranchThrowsThrowable}, hash: B56D1BD2EFE7194634B6F12C5DE52C9D
    @Disabled()
    @Test()
    void deleteByIdWhenDefaultBranchThrowsThrowable() throws SQLException {
        /* Branches:
         * (branch expression (line 94)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new UserDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("DELETE FROM user_ WHERE id = ?");
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
            verify(connectionMock).prepareStatement("DELETE FROM user_ WHERE id = ?");
            verify(preparedStatementMock).setLong(1, 1L);
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${deleteByIdWhenCaughtSQLException}, hash: EE46573160DD9DC018973B5D16FAD3E2
    @Test()
    void deleteByIdWhenCaughtSQLException() {
        /* Branches:
         * (catch-exception (SQLException)) : false
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new UserDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        //Act Statement(s)
        target.deleteById(12345L);
    }

    //Sapient generated method id: ${deleteByIdWhenCaughtSQLExceptionThrowsDaoException}, hash: 2288083F2A0B140A0378A0171B494A6F
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
        target = new UserDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("DELETE FROM user_ WHERE id = ?");
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
            verify(connectionMock).prepareStatement("DELETE FROM user_ WHERE id = ?");
            verify(preparedStatementMock).setLong(1, 1L);
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).close();
        });
    }

    //Sapient generated method id: ${deleteByIdWhenDefaultBranch}, hash: 854CDCF0B270B655D65551ECA63913E8
    @Disabled()
    @Test()
    void deleteByIdWhenDefaultBranch() throws SQLException {
        /* Branches:
         * (catch-exception (SQLException)) : false
         * (branch expression (line 94)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
        target = new UserDao();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("DELETE FROM user_ WHERE id = ?");
        doNothing().when(preparedStatementMock).setLong(1, 1L);
        doReturn(0).when(preparedStatementMock).executeUpdate();
        doNothing().when(preparedStatementMock).close();
        //Act Statement(s)
        target.deleteById(1L);
        //Assert statement(s)
        assertAll("result", () -> {
            verify(connectionMock).prepareStatement("DELETE FROM user_ WHERE id = ?");
            verify(preparedStatementMock).setLong(1, 1L);
            verify(preparedStatementMock).executeUpdate();
            verify(preparedStatementMock).close();
        });
    }
}
