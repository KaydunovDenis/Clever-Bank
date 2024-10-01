package com.github.kaydunov.spring;

import com.github.kaydunov.dao.ConnectionManager;
import com.github.kaydunov.dao.CrudRepository;
import com.github.kaydunov.dao.impl.AccountDao;
import com.github.kaydunov.dao.impl.TransactionDao;
import com.github.kaydunov.percentage_processor.PercentageProcessor;
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
        assertNull(target.getBean(PercentageProcessor.class));
    }
}
