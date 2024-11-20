package com.github.kaydunov.service;

import com.github.kaydunov.dto.Check;
import com.github.kaydunov.entity.TransactionType;
import com.github.kaydunov.util.DateConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CheckServiceTest {

    @Mock
    private BankService bankService; // Mocking BankService

    @InjectMocks
    private CheckService checkService; // Injecting mocks into CheckService

    @Test
    void createCheckSuccessfully() {
        // Arrange
        String accountSourceId = "source123";
        String accountDestinationId = "destination456";
        BigDecimal amount = new BigDecimal("1000.00");
        TransactionType transactionType = TransactionType.DEPOSIT;
        String currency = "USD";

        // Mocking the bankService calls
        when(bankService.getBankName(accountSourceId)).thenReturn("Sender Bank");
        when(bankService.getBankName(accountDestinationId)).thenReturn("Recipient Bank");

        // Mocking DateConverter to return a specific timestamp (if it's static)
        Timestamp mockTimestamp = Timestamp.valueOf("2024-11-20 15:00:00");
        try (MockedStatic<DateConverter> mockedDateConverter = Mockito.mockStatic(DateConverter.class)) {
            mockedDateConverter.when(DateConverter::getCurrentTimestamp).thenReturn(mockTimestamp);

            // Act
            Check result = checkService.create(accountSourceId, accountDestinationId, amount, transactionType, currency);

            // Assert
            assertNotNull(result);
            assertEquals(accountSourceId, result.getAccountSourceId());
            assertEquals(accountDestinationId, result.getAccountDestinationId());
            assertEquals(amount, result.getAmount());
            assertEquals(transactionType, result.getType());
            assertEquals("Sender Bank", result.getSenderBankName());
            assertEquals("Recipient Bank", result.getRecipientBankName());
            assertEquals(currency, result.getCurrency());
            assertEquals(mockTimestamp, result.getDate());

            // Verifying interactions with the mocked bankService
            verify(bankService).getBankName(accountSourceId);
            verify(bankService).getBankName(accountDestinationId);
        }
    }
}

