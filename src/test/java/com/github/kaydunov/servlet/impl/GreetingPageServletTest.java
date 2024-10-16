package com.github.kaydunov.servlet.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class GreetingPageServletTest {

    private GreetingPageServlet target = new GreetingPageServlet();
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
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
    void testGreeting() throws Exception {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        when(resp.getWriter()).thenReturn(printWriter);
        target.doGet(req, resp);
        String expectedOutput = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Welcome</title>
                </head>
                <body>
                <h1>Welcome to Clever-Bank!</h1>
                <h1>Have a Great Day!</h1>
                </body>
                </html>
                
                """;

        assertEquals(expectedOutput, writer.toString());
    }
}
