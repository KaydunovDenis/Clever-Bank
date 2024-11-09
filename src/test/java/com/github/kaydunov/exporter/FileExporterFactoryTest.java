package com.github.kaydunov.exporter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileExporterFactoryTest {

    @Test
    void getExporter() {
        FileExporter pdfExporter = FileExporterFactory.getExporter("pdf");
        assertTrue(pdfExporter instanceof PdfExporter);

        FileExporter txtExporter = FileExporterFactory.getExporter("txt");
        assertTrue(txtExporter instanceof TxtExporter);

        assertThrows(IllegalArgumentException.class, () -> FileExporterFactory.getExporter("unsupported"));
    }
}