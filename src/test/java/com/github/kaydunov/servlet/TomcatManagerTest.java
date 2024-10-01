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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TomcatManagerTest {

    private ApplicationContext applicationContext;

    //Sapient generated method id: ${startWhenAnnotationIsNotNull}, hash: 03873BFF0DEFCD415D2FCA84FAFB1405
    @Test()
    @Disabled()
    void startWhenAnnotationIsNotNull() {
        /* Branches:
         * (for-each(servletClasses)) : true  #  inside registerServlets method
         * (annotation != null) : true  #  inside registerServlet method
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
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

    //Sapient generated method id: ${startWhenAnnotationIsNullThrowsTomCatException}, hash: E8EC765D7FB7AD3134AF486CC0717570
    @Test()
    @Disabled()
    void startWhenAnnotationIsNullThrowsTomCatException() {
        /* Branches:
         * (for-each(servletClasses)) : true  #  inside registerServlets method
         * (annotation != null) : false  #  inside registerServlet method
         *
         * TODO: Help needed! Please adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
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
        assertEquals(TomcatManager.TEMP, tomcat.getServer().getCatalinaHome().getName());
    }
}
