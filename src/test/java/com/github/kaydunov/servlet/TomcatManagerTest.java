package com.github.kaydunov.servlet;

import com.github.kaydunov.exception.TomCatException;
import com.github.kaydunov.spring.ApplicationContext;
import jakarta.servlet.http.HttpServlet;
import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TomcatManagerTest {

    private ApplicationContext applicationContext;

    @Test()
    @Disabled()
    void startWhenAnnotationIsNotNull() {
        //Arrange Statement(s)
        Wrapper wrapperMock = mock(Wrapper.class);
        HttpServlet httpServletMock = mock(HttpServlet.class);
        try (MockedStatic<Tomcat> tomcat = mockStatic(Tomcat.class);
             MockedStatic<ServletScanner> servletScanner = mockStatic(ServletScanner.class)) {
            Set<Class<?>> anySet = new HashSet<>();
            anySet.add(Object.class);
            servletScanner.when(() -> ServletScanner.getServletClasses("")).thenReturn(anySet);
            tomcat.when(() -> Tomcat.addServlet((Context) any(), eq("jakarta.servlet.http.HttpServlet"), eq(httpServletMock))).thenReturn(wrapperMock);
            //Act Statement(s)
            TomcatManager.start(applicationContext);
            //Assert statement(s)
            assertAll("result", () -> {
                servletScanner.verify(() -> ServletScanner.getServletClasses(""), atLeast(1));
                tomcat.verify(() -> Tomcat.addServlet((Context) any(), eq("jakarta.servlet.http.HttpServlet"), eq(httpServletMock)));
            });
        }
    }

    @Test()
    @Disabled()
    void startWhenAnnotationIsNullThrowsTomCatException() {
        //Arrange Statement(s)
        try (MockedStatic<ServletScanner> servletScanner = mockStatic(ServletScanner.class)) {
            Set<Class<?>> anySet = new HashSet<>();
            anySet.add(Object.class);
            servletScanner.when(() -> ServletScanner.getServletClasses("")).thenReturn(anySet);
            //Act Statement(s)
            final TomCatException result = assertThrows(TomCatException.class, () -> {
                TomcatManager.start(applicationContext);
            });
            Exception exception = new Exception("message1");
            TomCatException tomCatException = new TomCatException("message1", exception);
            //Assert statement(s)
            assertAll("result", () -> {
                assertThat(result, is(notNullValue()));
                assertThat(result.getMessage(), equalTo(tomCatException.getMessage()));
                servletScanner.verify(() -> ServletScanner.getServletClasses(""), atLeast(1));
            });
        }
    }

    @Test
    void testInitializeTomcat() {
        //arrange
        applicationContext = mock(ApplicationContext.class);
        //act
        Tomcat tomcat = TomcatManager.initializeTomcat();

        //assert
        assertNotNull(tomcat);
        assertNotNull(tomcat.getConnector());
        assertEquals(TomcatManager.PORT, tomcat.getConnector().getPort());
        assertEquals(TomcatManager.TOMCAT_BASE_DIR, tomcat.getServer().getCatalinaHome().getName());
    }
}
