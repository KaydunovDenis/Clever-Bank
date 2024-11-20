package com.github.kaydunov.service;

import com.github.kaydunov.dto.Check;
import com.github.kaydunov.dto.CheckTest;
import com.github.kaydunov.exception.FileExporterException;
import com.github.kaydunov.exporter.FileExporter;
import com.github.kaydunov.exporter.PdfExporter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class PdfExporterTest {

    private final FileExporter target = new PdfExporter();

    @Test
    void export() {
        Check check = CheckTest.createCheck();
        String filename = "check/check_" + check.getNumber() + ".txt";
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
        //Arrange Statement(s)
        //Act Statement(s)
        target.export(check);
        assertTrue(file.exists());
    }


    @Test
    void saveAsTXTFile_shouldThrowProcessorException_whenIOExceptionOccurs() throws IOException {
        // Arrange: создаем невалидный файл (который не может быть записан)
        File invalidFile = mock(File.class);
        when(invalidFile.getPath()).thenReturn("invalid/path");

        // Мокируем исключение при создании FileWriter
        doThrow(new IOException("Test exception")).when(invalidFile).createNewFile();

        // Act & Assert: ожидаем выброс исключения FileProcessorException
        FileExporterException thrown = assertThrows(FileExporterException.class, () -> {
            target.export(CheckTest.createCheck());
        });

        assertEquals("Exception saving to file: invalid/path", thrown.getMessage(), "The exception message should contain the file path.");
    }

}
