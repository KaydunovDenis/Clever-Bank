package com.github.kaydunov.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceTest {

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    @Mock
    private URL mockUrl;

    @Mock
    private HttpURLConnection mockConnection;


    @Test
    void shouldReturnCorrectExchangeRateForValidCurrencies() throws Exception {
        // Arrange
        String fromCurrency = "USD";
        String toCurrency = "EUR";

        // Act
        BigDecimal result = exchangeRateService.getExchangeRate(fromCurrency, toCurrency);

        // Assert
        assertNotNull(result);
    }
}
