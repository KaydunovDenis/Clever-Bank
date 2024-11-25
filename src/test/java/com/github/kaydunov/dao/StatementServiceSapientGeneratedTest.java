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
import com.github.kaydunov.util.DateConverter;
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

    private static final long USER_ID = 1L;
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
        account.setUserId(USER_ID);
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
    void testCreateFullPeriodStatement() throws NotFoundException {
        String accountId = "123";
        when(accountService.getById(accountId)).thenReturn(account);
        when(transactionService.findByAccountIdAndDateRange(eq(accountId), any(), any()))
                .thenReturn(transactions);
        when(userService.findById(USER_ID)).thenReturn(user);
        Statement result = statementService.createFullPeriodStatement(accountId);
        assertNotNull(result);
        assertEquals("John Doe", result.getClientName());
        assertEquals(accountId, result.getAccountNumber());
        assertEquals("USD", result.getCurrency());
        assertEquals(new BigDecimal("1000.0"), result.getBalance());
        assertEquals(account.getCreatedAt().toLocalDateTime().toLocalDate(), result.getAccountOpeningDate());
        assertEquals(LocalDate.now(), result.getEndOfPeriod());
        assertNotNull(result.getGenerationDate());
        assertEquals(transactions, result.getTransactions());
        verify(accountService, times(2)).getById(accountId);//TODO it is OK or not?
        verify(transactionService).findByAccountIdAndDateRange(eq(accountId), any(), any());
        verify(userService).findById(USER_ID);
    }

    @Test
    void testCreateFullPeriodStatement_throwsException() throws NotFoundException {
        //Arrange
        String accountId = "123";
        when(accountService.getById(accountId)).thenThrow(new NotFoundException("Account not found"));
        //Act
        assertThrows(NotFoundException.class, () -> statementService.createFullPeriodStatement(accountId));
        //Assert
        verify(accountService).getById(accountId);
        verify(userService, never()).findById(any());
        verify(transactionService,never()).findByAccountIdAndDateRange(eq(accountId), any(), any());
        verifyNoInteractions(userService);
    }

    @Test
    void testCreateByAccountIdFullReportAndYear() throws NotFoundException {
        //Arrange
        String accountId = "123";
        int year = 2023;
        when(accountService.getById(accountId)).thenReturn(account);
        when(userService.findById(USER_ID)).thenReturn(user);
        when(transactionService.findByAccountIdAndDateRange(eq(accountId), any(Timestamp.class), any(Timestamp.class))).thenReturn(transactions);
        //Act
        Statement result = statementService.createYearlyStatement(accountId, year);
        //Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getClientName());
        assertEquals(accountId, result.getAccountNumber());
        assertEquals("USD", result.getCurrency());
        verify(accountService).getById(accountId);
        verify(transactionService).findByAccountIdAndDateRange(eq(accountId), any(), any());
        verify(userService).findById(USER_ID);
    }

    @Test
    void createFullPeriodStatementAndYear_ThrowsNotFoundException() throws NotFoundException {
        //Arrange
        String accountId = "123";
        int year = 2023;
        when(accountService.getById(accountId)).thenThrow(new NotFoundException("Account not found"));
        //Act
        assertThrows(NotFoundException.class, () -> statementService.createYearlyStatement(accountId, year));
        //Assert
        verify(accountService).getById(accountId);
        verify(userService, never()).findById(any());
        verify(transactionService, never()).findByAccountIdAndDateRange(any(), any(Timestamp.class), any(Timestamp.class));
        verifyNoInteractions(userService);
    }

    @Test
    void testCreateByAccountIdFullReportAndMonth() throws NotFoundException {
        //Arrange
        String accountId = "123";
        int year = 2024;
        int month = 1;

        when(transactionService.findByAccountIdAndDateRange(anyString(), any(), any()))
                .thenReturn(transactions);
        when(accountService.getById(accountId)).thenReturn(account);
        Long userId = account.getUserId();
        when(userService.findById(userId)).thenReturn(user);
        //Act
        Statement result = statementService.createMonthlyStatement(accountId, year, month);
        //Assert
        verify(transactionService).findByAccountIdAndDateRange(eq(accountId), any(), any());
        verify(accountService).getById(accountId);
        verify(userService).findById(userId);

        assertEquals(result.getClientName(), user.getName());
        assertEquals(result.getAccountNumber(), accountId);
        assertEquals(result.getCurrency(), account.getCurrency());
        assertEquals(result.getBalance(), account.getBalance());
        assertEquals(result.getAccountOpeningDate(), DateConverter.toLocalDate(account.getCreatedAt()));

        LocalDate startOfPeriod = result.getStartOfPeriod();
        LocalDate endOfPeriod = result.getEndOfPeriod();
        assertNotNull(startOfPeriod);
        assertNotNull(endOfPeriod);
        assertTrue(startOfPeriod.isBefore(endOfPeriod));
        assertEquals(result.getGenerationDate().toLocalDate(), LocalDate.now());
        assertEquals(result.getTransactions(), transactions);
    }
}
