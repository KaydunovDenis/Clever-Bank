package com.github.kaydunov.service;

import com.github.kaydunov.dao.impl.AccountDao;
import com.github.kaydunov.entity.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class AccountServiceSapientGeneratedTest {

    private final AccountDao accountDaoMock = mock(AccountDao.class, "accountDao");

    private AutoCloseable autoCloseableMocks;

    @InjectMocks()
    private AccountService target;

    @AfterEach()
    public void afterTest() throws Exception {
        if (autoCloseableMocks != null)
            autoCloseableMocks.close();
    }

    //Sapient generated method id: ${transferTest}, hash: 999E10F5D83C055A86ABCDBD8ED704F1
    @Test()
    void transferTest() throws SQLException {
        //Arrange Statement(s)
        target = new AccountService();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        doNothing().when(accountDaoMock).transfer(any(), eq(0L), eq(0L));
        
        //Act Statement(s)
        target.transfer(new BigDecimal("0"), 0L, 0L);
        
        //Assert statement(s)
        assertAll("result", () -> verify(accountDaoMock).transfer(any(), eq(0L), eq(0L)));
    }

    //Sapient generated method id: ${withdrawTest}, hash: B40500F6E6177F11E40E49FF892227E0
    @Test()
    void withdrawTest() throws SQLException {
        //Arrange Statement(s)
        target = new AccountService();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        doNothing().when(accountDaoMock).withdraw(any(), eq(0L));
        
        //Act Statement(s)
        target.withdraw(new BigDecimal("0"), 0L);
        
        //Assert statement(s)
        assertAll("result", () -> verify(accountDaoMock).withdraw(any(), eq(0L)));
    }

    //Sapient generated method id: ${depositTest}, hash: BC177CA84B322C55BFCB93E0EDCD79D0
    @Test()
    void depositTest() throws SQLException {
        //Arrange Statement(s)
        target = new AccountService();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        doNothing().when(accountDaoMock).deposit(any(), eq(0L));
        
        //Act Statement(s)
        target.deposit(new BigDecimal("0"), 0L);
        
        //Assert statement(s)
        assertAll("result", () -> verify(accountDaoMock).deposit(any(), eq(0L)));
    }

    //Sapient generated method id: ${getAllTest}, hash: 8003A2752FD773EBC9551D5F26022843
    @Test()
    void getAllTest() {
        //Arrange Statement(s)
        target = new AccountService();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        List<Account> accountList = new ArrayList<>();
        doReturn(accountList).when(accountDaoMock).findAll();
        
        //Act Statement(s)
        List<Account> result = target.getAll();
        
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, equalTo(accountList));
            verify(accountDaoMock).findAll();
        });
    }
}
