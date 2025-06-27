package com.github.kaydunov.service;

import com.github.kaydunov.dao.crud.AccountDao;
import com.github.kaydunov.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class TransferServiceBaseRockGeneratedTest {

    @Mock
    private AccountDao accountDao;

    @InjectMocks
    private TransferService target;

    private Account sourceAccount;

    private Account destinationAccount;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() throws SQLException {
        autoCloseable = openMocks(this);
        sourceAccount = new Account();
        sourceAccount.setId("source123");
        sourceAccount.setBalance(new BigDecimal("1000"));
        sourceAccount.setCurrency("USD");
        destinationAccount = new Account();
        destinationAccount.setId("dest456");
        destinationAccount.setBalance(new BigDecimal("500"));
        destinationAccount.setCurrency("USD");
        when(accountDao.findById("source123")).thenReturn(Optional.of(sourceAccount));
        when(accountDao.findById("dest456")).thenReturn(Optional.of(destinationAccount));

    }

    @Test
    void transferTest() throws SQLException {
        //Arrange Statement(s)
        Account accountMock = mock(Account.class);
        Account accountMock2 = mock(Account.class);
            doReturn(Optional.of(accountMock)).when(accountDao).findById("1");
            doNothing().when(accountMock).withdrawBalance(any());
            doReturn(Optional.of(accountMock2)).when(accountDao).findById("987654321");
            doNothing().when(accountMock2).depositBalance(any());
            doNothing().when(accountDao).update(accountMock);
            doNothing().when(accountDao).update(accountMock2);
            //Act Statement(s)
            target.transfer(new BigDecimal("100.0"), "1", "987654321");
            //Assert statement(s)
            assertAll("result", () -> {
                verify(accountDao).findById("1");
                verify(accountMock).withdrawBalance(any());
                verify(accountDao).findById("987654321");
                verify(accountMock2).depositBalance(any());
                verify(accountDao, times(2)).updateWithTransaction(any(), any());
            });

    }

}