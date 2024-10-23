package com.github.kaydunov.servlet;

import com.github.kaydunov.exception.TomCatException;
import com.github.kaydunov.spring.ApplicationContext;
import jakarta.servlet.http.HttpServlet;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class TomcatManagerTest {

    @Mock
    private ApplicationContext applicationContext;

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
    void testInitializeTomcat() {
        //arrange
        //act
        Tomcat tomcat = TomcatManager.initializeTomcat();
        //assert
        assertNotNull(tomcat);
        assertNotNull(tomcat.getConnector());
        assertEquals(TomcatManager.PORT, tomcat.getConnector().getPort());
        assertEquals(TomcatManager.TOMCAT_BASE_DIR, tomcat.getServer().getCatalinaHome().getName());
    }

    @Test
    void start_when_servlet_is_Null() {
        Tomcat tomcat = Mockito.spy(Tomcat.class);
        TomCatException tomCatException = assertThrows(TomCatException.class, () ->
                TomcatManager.start(applicationContext));

        assertEquals(tomCatException.getMessage(), "Servlet shouldn't be null.");
    }

    @Test
    void start_when_incorrectServlet() {
        //arrange
        when(applicationContext.getBean(any())).thenReturn(mock(HttpServlet.class));

        //act
        TomCatException tomCatException = assertThrows(TomCatException.class, () ->
                TomcatManager.start(applicationContext));

        //assert
        assertTrue(tomCatException.getMessage().contains("servlet class doesn't have @WebServlet annotation"));
    }
}
