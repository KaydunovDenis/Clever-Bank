package com.github.kaydunov.service;

import com.github.kaydunov.dao.AccountPercentDao;
import com.github.kaydunov.dao.crud.AccountDao;
import com.github.kaydunov.dao.crud.TransactionDaoTest;
import com.github.kaydunov.dto.Check;
import com.github.kaydunov.entity.Account;
import com.github.kaydunov.dto.CheckTest;
import com.github.kaydunov.entity.Transaction;
import javassist.NotFoundException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class AccountServiceTest {
    private final BigDecimal amount = new BigDecimal(10);
    private final String accountSourceId = "1L";
    private final String accountDestinationId = "2L";

    @Mock
    private AccountDao accountDao;
    @Mock
    private AccountPercentDao accountPercentDao;
    @Mock
    private CheckService checkService;
    @InjectMocks
    private AccountService target;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @SneakyThrows
    @Test
    void transfer() {
        // Arrange
        doNothing().when(accountDao).transfer(amount, accountSourceId, accountDestinationId);
        
        // Act
        target.transfer(amount, accountSourceId, accountDestinationId);
        
        // Assert
        verify(accountDao).transfer(amount, accountSourceId, accountDestinationId);
    }

    @SneakyThrows
    @Test
    void withdraw() {
        // Arrange
        doNothing().when(accountDao).withdraw(amount, accountSourceId);
        
        // Act
        target.withdraw(amount, accountSourceId);
        
        // Assert
        verify(accountDao).withdraw(amount, accountSourceId);
    }

    @SneakyThrows
    @Test
    void deposit() {
        // Arrange
        Transaction transaction = TransactionDaoTest.createTransaction();
        Check check = CheckTest.createCheck();
        when(accountDao.deposit(amount, accountDestinationId)).thenReturn(transaction);
        when(checkService.create(any(), any(), any(), any(), any())).thenReturn(check);

        // Act
        target.deposit(amount, accountDestinationId, "txt");
        
        // Assert
        verify(accountDao).deposit(amount, accountDestinationId);
        verify(checkService).create(null, accountDestinationId, amount, transaction.getTransactionType(), transaction.getCurrency());
    }

    @Test
    void getAll() {
        // Arrange
        List<Account> accounts = new ArrayList<Account>();
        when(accountDao.findAll()).thenReturn(accounts);
        
        // Act
        List<Account> result = target.getAll();
        
        // Assert
        assertEquals(accounts, result);
    }

    @Test
    void chargePercents() {
        // Arrange
        doNothing().when(accountPercentDao).chargePercents(1.0);
        
        // Act
        target.chargePercents(1.0);
        
        // Assert
        verify(accountPercentDao).chargePercents(1.0);
    }

    @SneakyThrows
    @Test
    void getById_Positive() {
        // Arrange
        Account account = mock(Account.class);
        when(accountDao.findById(accountSourceId)).thenReturn(Optional.of(account));
        
        // Act
        target.getById(accountSourceId);
        
        // Assert
        verify(accountDao).findById(accountSourceId);
    }

    @Test
    void getById_Negative() {
        // Arrange
        when(accountDao.findById(accountSourceId)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(NotFoundException.class, () -> target.getById(accountSourceId));
        verify(accountDao).findById(accountSourceId);
    }

    @Test
    void deleteById() {
        // Arrange
        doNothing().when(accountDao).deleteById(accountSourceId);
        
        // Act
        target.deleteById(accountSourceId);
        
        // Assert
        verify(accountDao).deleteById(accountSourceId);
    }
}