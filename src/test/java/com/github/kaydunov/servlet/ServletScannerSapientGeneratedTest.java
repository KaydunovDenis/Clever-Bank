package com.github.kaydunov.servlet;

import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class ServletScannerSapientGeneratedTest {

    @Test()
    void getServletClasses() {
        //Act Statement(s)
        Set<Class<?>> result = ServletScanner.getServletClasses("com.github.kaydunov.servlet.impl");

        //Assert statement(s)
        assertTrue(result.size() > 0);
    }

    @Test()
    void getServletClasses_When_PackageNameIsNull() {
        //Act & Assert
        Executable executable = () -> ServletScanner.getServletClasses(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Package name must not be null or empty", exception.getMessage());
    }

    @Test
    void testGetServletClasses_EmptyPackageName() {
        // Act & Assert
        Executable executable = () -> ServletScanner.getServletClasses("");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Package name must not be null or empty", exception.getMessage());
    }
}
