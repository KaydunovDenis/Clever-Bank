package com.github.kaydunov.dao.crud;

import com.github.kaydunov.dao.ConnectionManager;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.mockito.Mock;
import com.github.kaydunov.exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;
import com.github.kaydunov.entity.User;
import org.mockito.MockedStatic;

import static org.mockito.ArgumentMatchers.anyString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.*;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class UserDaoTest {
    final String errorMessage = "message1";

    @Mock
    private Connection connectionMock;
    @Mock
    private PreparedStatement preparedStatementMock;
    @Mock
    private ResultSet resultSetMock;
    @InjectMocks
    private UserDao target;

    private AutoCloseable autoCloseableMocks;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @AfterEach()
    public void afterTest() throws Exception {
        if (autoCloseableMocks != null)
            autoCloseableMocks.close();
    }

    @Test
    void createWhenCaughtSQLException() throws SQLException {
        //Arrange Statement(s)
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            //Arrange Statement(s)
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
            when(resultSetMock.next()).thenReturn(true, false);
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
    }

    @Test
    void create_whenThrowsException() throws SQLException {
        //Arrange Statement(s)
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            //Arrange Statement(s)
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
            when(resultSetMock.next()).thenReturn(true, false);
            doReturn(preparedStatementMock).when(connectionMock).prepareStatement("INSERT INTO user_ (name, email) VALUES (?, ?)", 1);
            doNothing().when(preparedStatementMock).setString(1, "name1");
            doNothing().when(preparedStatementMock).setString(2, "email1");
            doReturn(1).when(preparedStatementMock).executeUpdate();
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
                verify(preparedStatementMock).close();
            });
        }
    }

    @Test
    void createWhenGeneratedKeysNextAndDefaultBranchAndCaughtSQLException() throws SQLException {
        //Arrange Statement(s)
        User user = new User();
        user.setName("name1");
        user.setId(1L);
        user.setEmail("email1");
        //Act Statement(s)
        User result;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(anyString())) {
            result = target.create(user);
        }
        //Assert statement(s)
        assertAll("result", () -> assertThat(result, equalTo(user)));
    }

    @Test()
    void create_whenTHrowException() throws SQLException {
        //Arrange Statement(s)
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            //Arrange Statement(s)
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
            doNothing().when(preparedStatementMock).setString(1, "name1");
            doNothing().when(preparedStatementMock).setString(2, "email1");
            SQLException sQLException = new SQLException("message1", "message1", 0);
            when(preparedStatementMock.executeUpdate()).thenThrow(sQLException);
            doReturn(resultSetMock).when(preparedStatementMock).getGeneratedKeys();
            doNothing().when(preparedStatementMock).close();

            User user = new User();
            user.setName("name1");
            user.setId(1L);
            user.setEmail("email1");

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
                verify(preparedStatementMock).close();
            });
        }
    }

    @Disabled()
    @Test
    void findByIdWhenDefaultBranchThrowsThrowable() throws SQLException {
        //Arrange Statement(s)
          
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM user_ WHERE id = ?");
        doNothing().when(preparedStatementMock).setLong(1, 1L);
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

    @Test()
    void findByIdWhenCaughtSQLException() {
        //Arrange Statement(s)
        target = new UserDao();
        autoCloseableMocks = openMocks(this);
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
        //Arrange Statement(s)
          
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM user_ WHERE id = ?");
        doNothing().when(preparedStatementMock).setLong(1, 1L);
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
         * (resultSetMock.next()) : false
         * (catch-exception (SQLException)) : false
         * (branch expression (line 54)) : true
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
        //Arrange Statement(s)
          
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM user_ WHERE id = ?");
        doNothing().when(preparedStatementMock).setLong(1, 1L);
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

    @Disabled()
    @Test()
    void findAllWhenDefaultBranchThrowsThrowable() throws SQLException {
        //Arrange Statement(s)
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM user_");
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
        //Arrange Statement(s)

        //Act Statement(s)
        List<User> result = target.findAll();
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result.size(), equalTo(1));
            assertThat(result.get(0), is(instanceOf(User.class)));
        });
    }

    @Test()
    void findAllWhenCaughtSQLExceptionThrowsDaoException() throws SQLException {
        //Arrange Statement(s)
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM user_");
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

    @Test()
    void findAllWhenDefaultBranch() throws SQLException {
        //Arrange Statement(s)
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        doReturn(preparedStatementMock).when(connectionMock).prepareStatement("SELECT * FROM user_");
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

    @Test
    void updateWhenDefaultBranchThrowsThrowable() throws SQLException {
        //Arrange Statement(s)
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

    @Test
    void updateWhenCaughtSQLException() {
        //Arrange Statement(s)
        target = new UserDao();
        autoCloseableMocks = openMocks(this);
        User user = new User();
        user.setName("name1");
        user.setId(1L);
        user.setEmail("email1");
        //Act Statement(s)
        target.update(user);
    }

    @Test()
    void updateWhenCaughtSQLExceptionThrowsDaoException() throws SQLException {
        //Arrange Statement(s)
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

    @Test
    void updateWhenDefaultBranch() throws SQLException {
        //Arrange Statement(s)
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

    @Test
    void deleteByIdWhenDefaultBranchThrowsThrowable() throws SQLException {
        //Arrange Statement(s)
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            //Arrange Statement(s)
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
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
    }

    @Test
    void deleteById_whenThrowException() throws SQLException {
        //Arrange Statement(s)
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            //Arrange Statement(s)
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
            when(resultSetMock.next()).thenReturn(true, false);
            doNothing().when(preparedStatementMock).setLong(1, 1L);
            SQLException sqlException = new SQLException(errorMessage);
            when(preparedStatementMock.executeUpdate()).thenThrow(sqlException);
            doNothing().when(preparedStatementMock).close();
            //Act Statement(s)
            final DaoException result = assertThrows(DaoException.class, () -> {
                target.deleteById(1L);
            });
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));

                DaoException daoException = new DaoException(errorMessage, sqlException);
                assertThat(result.getMessage(), equalTo(daoException.getMessage()));
                assertThat(result.getCause(), is(instanceOf(sqlException.getClass())));
                verify(connectionMock).prepareStatement("DELETE FROM user_ WHERE id = ?");
                verify(preparedStatementMock).setLong(1, 1L);
                verify(preparedStatementMock).executeUpdate();
                verify(preparedStatementMock).close();
            });
        }
    }

    @Test()
    void deleteById() throws SQLException {
        //Arrange Statement(s)
        try (MockedStatic<ConnectionManager> connectionManager = mockStatic(ConnectionManager.class)) {
            //Arrange Statement(s)
            connectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
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
}
