package com.github.kaydunov.dao;

import com.github.kaydunov.dao.crud.TransactionDaoTest;
import com.github.kaydunov.dto.Statement;
import com.github.kaydunov.entity.Account;
import com.github.kaydunov.entity.Transaction;
import com.github.kaydunov.entity.User;
import com.github.kaydunov.service.AccountService;
import com.github.kaydunov.service.StatementService;
import com.github.kaydunov.service.TransactionService;
import com.github.kaydunov.service.UserService;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class StatementServiceSapientGeneratedTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private AccountService accountService;

    @Mock
    private UserService userService;

    @InjectMocks
    private StatementService statementService;

    private Account account;

    private User user;

    private List<Transaction> transactions;

    @BeforeEach
    void setUp() {
        openMocks(this);
        account = new Account();
        account.setUserId(1L);
        account.setCurrency("USD");
        account.setValueAsBalance("1000.0");
        account.setCreatedAt(Timestamp.valueOf(LocalDateTime.now().minusDays(30)));
        user = new User();
        user.setName("John Doe");
        Transaction transaction1 = TransactionDaoTest.createTransaction();
        Transaction transaction2 = TransactionDaoTest.createTransaction();
        transaction2.setAmount(new BigDecimal("1001"));
        transactions = List.of(transaction1, transaction2);
    }

    @Test
    void testFindByAccountId() throws NotFoundException {
        String accountId = "123";
        when(accountService.getById(accountId)).thenReturn(account);
        when(transactionService.getFullPeriodStatement(accountId)).thenReturn(transactions);
        when(userService.findById(1L)).thenReturn(user);
        Statement result = statementService.findByAccountId(accountId);
        assertNotNull(result);
        assertEquals("John Doe", result.getClientName());
        assertEquals(accountId, result.getAccountNumber());
        assertEquals("USD", result.getCurrency());
        assertEquals(new BigDecimal("1000.0"), result.getBalance());
        assertEquals(account.getCreatedAt().toLocalDateTime().toLocalDate(), result.getAccountOpeningDate());
        assertEquals(LocalDate.now(), result.getEndOfPeriod());
        assertNotNull(result.getGenerationDate());
        assertEquals(transactions, result.getTransactions());
        verify(accountService).getById(accountId);
        verify(transactionService).getFullPeriodStatement(accountId);
        verify(userService).findById(1L);
    }

    @Test
    void testFindByAccountIdThrowsNotFoundException() throws NotFoundException {
        //Arrange
        String accountId = "123";
        when(accountService.getById(accountId)).thenThrow(new NotFoundException("Account not found"));
        //Act
        assertThrows(NotFoundException.class, () -> statementService.findByAccountId(accountId));
        //Assert
        verify(accountService).getById(accountId);
        verify(transactionService).getFullPeriodStatement(accountId);
        verifyNoInteractions(userService);
    }

    @Test
    void testFindByAccountIdByAccountIdAndYear() throws NotFoundException {
        String accountId = "123";
        int year = 2023;
        Timestamp startDate = Timestamp.valueOf(year + "-01-01 00:00:00");
        when(accountService.getById(accountId)).thenReturn(account);
        when(transactionService.getYearlyStatement(accountId, year)).thenReturn(transactions);
        when(userService.findById(1L)).thenReturn(user);
        Statement result = statementService.findByAccountIdAndYear(accountId, year);
        assertNotNull(result);
        assertEquals("John Doe", result.getClientName());
        assertEquals(accountId, result.getAccountNumber());
        assertEquals("USD", result.getCurrency());
        verify(accountService).getById(accountId);
        verify(transactionService).getYearlyStatement(accountId, year);
        verify(userService).findById(1L);
    }

    @Test
    void findByAccountIdAndYear_ThrowsNotFoundException() throws NotFoundException {
        //Arrange
        String accountId = "123";
        int year = 2023;
        when(accountService.getById(accountId)).thenThrow(new NotFoundException("Account not found"));
        //Act
        assertThrows(NotFoundException.class, () -> statementService.findByAccountIdAndYear(accountId, year));
        //Assert
        verify(accountService).getById(accountId);
        verify(transactionService).getYearlyStatement(accountId, year);
        verifyNoInteractions(userService);
    }

    @Test
    void testFindByAccountIdByAccountIdAndMonth() throws NotFoundException {
        //Arrange
        String accountId = "123";
        int year = 2024;
        int month = 1;

        when(transactionService.getMonthlyStatement(accountId, year, month))
                .thenReturn(transactions);
        when(accountService.getById(accountId)).thenReturn(account);
        Long userId = account.getUserId();
        when(userService.findById(userId)).thenReturn(user);
        //Act
        Statement result = statementService.findByAccountIdAndMonth(accountId, year, month);
        //Assert
        verify(transactionService).getMonthlyStatement(accountId, year, month);
        verify(accountService).getById(accountId);
        verify(userService).findById(userId);

//        statement.setClientName(user.getName());
//        statement.setAccountNumber(accountId);
//        statement.setCurrency(account.getCurrency());
//        statement.setBalance(account.getBalance());
//        statement.setAccountOpeningDate(account.getCreatedAtAsLocalDate());
//        statement.setStartOfPeriod(account.getCreatedAtAsLocalDate());
//        statement.setEndOfPeriod(LocalDate.now());
//        statement.setGenerationDate(LocalDateTime.now());
//        statement.setTransactions(transactions);
        assertEquals(result.getClientName(), user.getName());
        assertEquals(result.getAccountNumber(), accountId);
        assertEquals(result.getCurrency(), account.getCurrency());
        assertEquals(result.getBalance(), account.getBalance());
        assertEquals(result.getAccountOpeningDate(), account.getCreatedAtAsLocalDate());
        assertEquals(result.getEndOfPeriod(), LocalDate.now());
        assertEquals(result.getGenerationDate(), LocalDate.now());

        assertEquals(result.getTransactions(), transactions);
    }
}
