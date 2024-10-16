package com.github.kaydunov.percentage_processor;

import com.github.kaydunov.service.AccountService;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Durations.ONE_SECOND;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class PercentageProcessorTest {

    @Mock
    private AccountService accountServiceMock;
    @InjectMocks
    private PercentageProcessor percentageProcessor;
    private AutoCloseable autoCloseable;


    @BeforeEach
    void setUp() {
        autoCloseable = openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (autoCloseable != null)
            autoCloseable.close();
    }


    @Test
    void testStartProcessing() {
        percentageProcessor.startProcessing();

        // Use Awaitility to wait for at least one task to be completed
        Awaitility.await()
                .atMost(35, TimeUnit.SECONDS)  // Wait a maximum of 35 seconds
                .untilAsserted(() -> {
                    // Verify that the AccountService.chargePercents method was called
                    verify(accountServiceMock, times(1)).chargePercents(1.0);
                });

        // Stop processing
        percentageProcessor.stopProcessing();
    }

    @Test
    void testStopProcessing() {
        percentageProcessor.startProcessing();
        percentageProcessor.stopProcessing();

        // Verify that the method is not called after stopping
        Awaitility.await().atMost(ONE_SECOND);
        verify(accountServiceMock, never()).chargePercents(Mockito.anyDouble());
    }
}

