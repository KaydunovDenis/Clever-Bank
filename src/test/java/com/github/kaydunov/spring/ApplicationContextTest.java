package com.github.kaydunov.spring;

import com.github.kaydunov.CleverBank;
import com.github.kaydunov.dao.crud.AccountDao;
import com.github.kaydunov.dao.crud.CrudRepository;
import com.github.kaydunov.dao.crud.TransactionDao;
import com.github.kaydunov.dao.crud.UserDao;
import com.github.kaydunov.exporter.FileExporter;
import com.github.kaydunov.exporter.FileExporterFactory;
import com.github.kaydunov.exporter.PdfExporter;
import com.github.kaydunov.exporter.TxtExporter;
import com.github.kaydunov.percentage_processor.PercentageProcessor;
import com.github.kaydunov.servlet.ObjectMapperWrapper;
import com.github.kaydunov.servlet.impl.AccountServlet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicationContextTest {

    private ApplicationContext target;

    @Test
    void getBean() {
        target = new ApplicationContext(CrudRepository.class);

        assertNotNull(target);
        assertNotNull(target.getBean(AccountDao.class));
        assertNotNull(target.getBean(TransactionDao.class));
        assertNotNull(target.getBean(UserDao.class));
        assertThrows(NoSuchBeanDefinitionException.class, () -> target.getBean(PercentageProcessor.class));
    }

    @Test
    void getObjectMapperBean() {
        target = new ApplicationContext(CleverBank.class);

        assertNotNull(target);
        assertNotNull(target.getBean(AccountDao.class));
        assertNotNull(target.getBean(TransactionDao.class));
        assertNotNull(target.getBean(PercentageProcessor.class));
        assertNotNull(target.getBean(ObjectMapperWrapper.class));
        assertNotNull(target.getBean(AccountServlet.class));
    }

    @Test
    void checkAutowaringForListField() {
        target = new ApplicationContext(FileExporter.class);

        assertNotNull(target.getBean(TxtExporter.class));
        assertNotNull(target.getBean(PdfExporter.class));
        FileExporterFactory fileExporterFactory = target.getBean(FileExporterFactory.class);
        assertNotNull(fileExporterFactory);
        FileExporter exporter = fileExporterFactory.getExporter("pdf");
        assertNotNull(exporter);
    }
}
