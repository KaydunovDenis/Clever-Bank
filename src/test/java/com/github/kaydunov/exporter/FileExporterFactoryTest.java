package com.github.kaydunov.exporter;

import com.github.kaydunov.exception.FileExporterException;
import com.github.kaydunov.spring.ApplicationContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileExporterFactoryTest {

    private static ApplicationContext context;

    private FileExporterFactory target;

    @BeforeAll

    static void initContext() {
        context = new ApplicationContext(FileExporter.class);
    }

    @BeforeEach
    void setUp() {
        target = context.getBean(FileExporterFactory.class);
    }

    @Test
    void shouldReturnPdfExporter() {
        FileExporter pdfExporter = target.getExporter("pdf");
        assertTrue(pdfExporter instanceof PdfExporter);
    }

    @Test
    void shouldReturnTxtExporter() {
        FileExporter txtExporter = target.getExporter("txt");
        assertTrue(txtExporter instanceof TxtExporter);
    }

    @Test
    void shouldThrowExceptionForUnsupportedFormat() {
        assertThrows(FileExporterException.class, () -> target.getExporter("unsupported"));
    }
}