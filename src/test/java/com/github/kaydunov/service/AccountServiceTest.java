package com.github.kaydunov.service;

import com.github.kaydunov.dao.AccountPercentDao;
import com.github.kaydunov.dao.ConnectionManager;
import com.github.kaydunov.dao.impl.AccountDao;
import com.github.kaydunov.entity.Account;
import javassist.NotFoundException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class AccountServiceTest {
    private final BigDecimal amount = new BigDecimal(10);
    private final Long accountSourceId = 1L;
    private final Long accountDestinationId = 2L;

    @Mock
    private AccountDao accountDao;
    @Mock
    private AccountPercentDao accountPercentDao;
    @InjectMocks
    private AccountService target;

    @BeforeEach
    void setUp(){
        try(MockedStatic<ConnectionManager> mockedStatic = Mockito.mockStatic(ConnectionManager.class)) {
            when(ConnectionManager.getConnection()).thenReturn(Mockito.mock(Connection.class));
            openMocks(this);
        }

    }

    @SneakyThrows
    @Test
    void transfer() {
        // Given
        // When
        doNothing().when(accountDao).transfer(amount, accountSourceId, accountDestinationId);
        target.transfer(amount, accountSourceId, accountDestinationId);
        // Then
        verify(accountDao).transfer(amount, accountSourceId, accountDestinationId);
    }

    @SneakyThrows
    @Test
    void withdraw() {
        // Given
        // When
        doNothing().when(accountDao).withdraw(amount, accountSourceId);
        target.withdraw(amount, accountSourceId);
        // Then
        verify(accountDao).withdraw(amount, accountSourceId);
    }

    @SneakyThrows
    @Test
    void deposit() {
        // Given
        // When
        doNothing().when(accountDao).deposit(amount, accountDestinationId);
        target.deposit(amount, accountDestinationId);
        // Then
        verify(accountDao).deposit(amount, accountDestinationId);
    }

    @Test
    void getAll() {
        // Given
        List<Account> accounts = new ArrayList<Account>();
        // When
        when(accountDao.findAll()).thenReturn(accounts);
        // Then
        assertEquals(accounts, target.getAll());
    }

    @Test
    void chargePercents() {
        // Given
        // When
        doNothing().when(accountPercentDao).chargePercents(1.0);
        target.chargePercents(1.0);
        // Then
        verify(accountPercentDao).chargePercents(1.0);
    }

    @SneakyThrows
    @Test
    void getById_Positive() {
        // Given
        Account account = mock(Account.class);
        // When
        when(accountDao.findById(1L)).thenReturn(Optional.of(account));
        target.getById(1L);
        // Then
        verify(accountDao).findById(1L);
    }

    @Test
    void getById_Negative() {
        // Given
        Account account = mock(Account.class);
        // When
        when(accountDao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> target.getById(1L));
        // Then
        verify(accountDao).findById(1L);
    }

    @Test
    void deleteById() {
        // Given
        // When
        doNothing().when(accountDao).deleteById(1L);
        target.deleteById(1L);
        // Then
        verify(accountDao).deleteById(1L);
    }

}
