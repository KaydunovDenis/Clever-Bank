package com.github.kaydunov.spring;

import com.github.kaydunov.CleverBank;
import com.github.kaydunov.dao.ConnectionManager;
import com.github.kaydunov.dao.CrudRepository;
import com.github.kaydunov.dao.impl.AccountDao;
import com.github.kaydunov.dao.impl.TransactionDao;
import com.github.kaydunov.dao.impl.UserDao;
import com.github.kaydunov.percentage_processor.PercentageProcessor;
import com.github.kaydunov.servlet.ObjectMapperWrapper;
import com.github.kaydunov.servlet.impl.AccountServlet;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class ApplicationContextTest {

    private ApplicationContext target;
    @Mock
    private Connection connectionMock;

    @Test
    void getBean() {
        try (MockedStatic<ConnectionManager> mockedConnectionManager = mockStatic(ConnectionManager.class)) {
            mockedConnectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            target = new ApplicationContext(CrudRepository.class);
        }

        assertNotNull(target);
        assertNotNull(target.getBean(AccountDao.class));
        assertNotNull(target.getBean(TransactionDao.class));
        assertNotNull(target.getBean(UserDao.class));
        assertThrows(NoSuchBeanDefinitionException.class, () -> target.getBean(PercentageProcessor.class));
    }

    @Test
    void getObjectMapperBean() {
        try (MockedStatic<ConnectionManager> mockedConnectionManager = mockStatic(ConnectionManager.class)) {
            mockedConnectionManager.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            target = new ApplicationContext(CleverBank.class);
        }

        assertNotNull(target);
        assertNotNull(target.getBean(AccountDao.class));
        assertNotNull(target.getBean(TransactionDao.class));
        assertNotNull(target.getBean(PercentageProcessor.class));
        assertNotNull(target.getBean(ObjectMapperWrapper.class));
        assertNotNull(target.getBean(AccountServlet.class));
    }
}
