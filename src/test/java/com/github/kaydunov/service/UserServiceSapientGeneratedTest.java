package com.github.kaydunov.service;

import org.junit.jupiter.api.Timeout;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import javassist.NotFoundException;
import java.util.List;
import org.mockito.MockitoAnnotations;
import com.github.kaydunov.dao.crud.UserDao;
import java.util.Optional;
import java.util.ArrayList;
import com.github.kaydunov.entity.User;
import static org.mockito.Mockito.doNothing;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doReturn;
import static org.hamcrest.Matchers.is;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class UserServiceSapientGeneratedTest {

    private final UserDao userDaoMock = mock(UserDao.class, "userDao");

    private AutoCloseable autoCloseableMocks;

    @InjectMocks()
    private UserService target;

    @AfterEach()
    public void afterTest() throws Exception {
        if (autoCloseableMocks != null)
            autoCloseableMocks.close();
    }

    //Sapient generated method id: ${createTest}, hash: E2152174C98B3D2A48D7A22187DF261C
    @Test()
    void createTest() {
        //Arrange Statement(s)
        User userMock = mock(User.class);
        User userMock2 = mock(User.class);
        doReturn(userMock).when(userDaoMock).create(userMock2);
        target = new UserService();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        
        //Act Statement(s)
        User result = target.create(userMock2);
        
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, equalTo(userMock));
            verify(userDaoMock).create(userMock2);
        });
    }

    //Sapient generated method id: ${updateTest}, hash: A7646BC8D654984C154312E04EE75BD3
    @Test()
    void updateTest() {
        //Arrange Statement(s)
        User userMock = mock(User.class);
        doNothing().when(userDaoMock).update(userMock);
        target = new UserService();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        
        //Act Statement(s)
        target.update(userMock);
        
        //Assert statement(s)
        assertAll("result", () -> verify(userDaoMock).update(userMock));
    }

    //Sapient generated method id: ${findByIdThrowsNotFoundException}, hash: 6E321DA1E6B1C36BBFB71BB7EAEA62E9
    @Test()
    void findByIdThrowsNotFoundException() throws NotFoundException {
        //Arrange Statement(s)
        doReturn(Optional.empty()).when(userDaoMock).findById(0L);
        target = new UserService();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        //Act Statement(s)
        final NotFoundException result = assertThrows(NotFoundException.class, () -> {
            target.findById(0L);
        });
        
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            verify(userDaoMock).findById(0L);
        });
    }

    //Sapient generated method id: ${findAllTest}, hash: 8268148820493AF707D08C3DA8004A45
    @Test()
    void findAllTest() {
        //Arrange Statement(s)
        List<User> userList = new ArrayList<>();
        doReturn(userList).when(userDaoMock).findAll();
        target = new UserService();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        
        //Act Statement(s)
        Iterable<User> result = target.findAll();
        
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, equalTo(userList));
            verify(userDaoMock).findAll();
        });
    }

    //Sapient generated method id: ${deleteByIdTest}, hash: 901A9B53F6BCED5EC633DF2194A2B9E6
    @Test()
    void deleteByIdTest() {
        //Arrange Statement(s)
        doNothing().when(userDaoMock).deleteById(0L);
        target = new UserService();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        
        //Act Statement(s)
        target.deleteById(0L);
        
        //Assert statement(s)
        assertAll("result", () -> verify(userDaoMock).deleteById(0L));
    }
}
