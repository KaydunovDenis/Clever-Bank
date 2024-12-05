package com.github.kaydunov.servlet.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.kaydunov.entity.Account;
import com.github.kaydunov.entity.Transaction;
import com.github.kaydunov.service.AccountService;
import com.github.kaydunov.servlet.ObjectMapperWrapper;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class AccountServletTest {

    private final String accountSourceId = "1L";
    private final String accountDestinationId = "2L";
    @Mock
    private AccountService accountService;
    @Mock
    private HttpServletResponse responseMock;
    @Mock
    private HttpServletRequest httpServletRequestMock;
    @Mock
    private HttpServletRequest requestMock;
    @Mock
    private ObjectMapperWrapper objectMapper;
    @Mock
    private ServletInputStream servletInputStreamMock;
    @InjectMocks
    private AccountServlet target;
    private AutoCloseable autoCloseable;

    @BeforeEach
    public void setUp() {
        autoCloseable = openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (autoCloseable != null)
            autoCloseable.close();
    }


    @Test
    void doGetTest() throws IOException {
        //Arrange Statement(s)
        doNothing().when(responseMock).setContentType("application/json");
        doNothing().when(responseMock).setCharacterEncoding("UTF-8");
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        doReturn(printWriter).when(responseMock).getWriter();

        List<Account> accounts = new ArrayList<>();
        when(accountService.getAll()).thenReturn(accounts);
        when(objectMapper.writeValueAsString(accounts)).thenReturn("some json");
        //Act Statement(s)
        target.doGet(httpServletRequestMock, responseMock);
        //Assert statement(s)
        assertAll("result", () -> {
            verify(responseMock).setContentType("application/json");
            verify(responseMock).setCharacterEncoding("UTF-8");
            verify(responseMock).getWriter();
            verify(accountService).getAll();

            // Check the response content
            String responseContent = writer.toString();
            assertEquals("some json", responseContent);
        });
    }


    @Test
    void doPostWhenInputStreamIsNull() throws IOException {
        // Arrange
        when(requestMock.getInputStream()).thenReturn(null);
        doNothing().when(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        Writer writer = Writer.nullWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        doReturn(printWriter).when(responseMock).getWriter();

        // Act
        target.doPost(requestMock, responseMock);

        // Assert
        assertAll("result", () -> {
            verify(requestMock).getInputStream();
            verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            verify(responseMock).getWriter();
            verify(accountService, never()).transfer(any(), anyString(), anyString(), anyString());
        });
    }

    @Test
    void doPostReturns500WhenJsonParsingFails() throws IOException {
        // Arrange
        when(servletInputStreamMock.read()).thenThrow(new JsonProcessingException("Failed to parse JSON") {
        });
        when(requestMock.getInputStream()).thenReturn(null);
        doNothing().when(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        Writer writer = Writer.nullWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        doReturn(printWriter).when(responseMock).getWriter();

        // Act
        target.doPost(requestMock, responseMock);

        // Assert
        assertAll("result", () -> {
            verify(requestMock).getInputStream();
            verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            verify(responseMock).getWriter();
            verify(accountService, never()).transfer(any(), anyString(), anyString(), anyString());
        });
    }

    @Test
    void doPostPositiveTest() throws IOException, SQLException {
        // When
        when(requestMock.getInputStream()).thenReturn(servletInputStreamMock);
        when(objectMapper.readValue(servletInputStreamMock, Transaction.class)).thenReturn(getTransaction());
        doNothing().when(accountService).transfer(any(BigDecimal.class), eq(accountSourceId), eq(accountDestinationId), eq("txt"));

        // Act
        target.doPost(requestMock, responseMock);

        // Assert
        assertAll("result", () -> {
            verify(requestMock).getInputStream();
            verify(objectMapper).readValue(any(InputStream.class), eq(Transaction.class));
            verify(accountService).transfer(BigDecimal.valueOf(100), accountSourceId, accountDestinationId, "txt");
            verify(responseMock).setStatus(HttpServletResponse.SC_OK);
        });
    }


    private Transaction getTransaction() {
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(100));
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        transaction.setCreatedAt(timestamp);
        transaction.setSourceAccountId(accountSourceId);
        transaction.setDestinationAccountId(accountDestinationId);
        return transaction;
    }
}
