package com.github.kaydunov.service;

import com.github.kaydunov.dto.Check;
import com.github.kaydunov.entity.CheckTest;
import com.github.kaydunov.exception.FileProcessorException;
import com.github.kaydunov.file_processor.CheckFileProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class CheckFileProcessorTest {

    private final CheckFileProcessor target = new CheckFileProcessor();

    @Test
    void process() {
        Check check = CheckTest.getCheck();
        String filename = "check/check_" + check.getNumber() + ".txt";
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
        //Arrange Statement(s)
        //Act Statement(s)
        target.process(check);
        assertTrue(file.exists());
    }


    @Test
    void saveToFile_shouldThrowFileProcessorException_whenIOExceptionOccurs() throws IOException {
        // Arrange: создаем невалидный файл (который не может быть записан)
        File invalidFile = mock(File.class);
        when(invalidFile.getPath()).thenReturn("invalid/path");

        // Мокируем исключение при создании FileWriter
        doThrow(new IOException("Test exception")).when(invalidFile).createNewFile();

        // Act & Assert: ожидаем выброс исключения FileProcessorException
        FileProcessorException thrown = assertThrows(FileProcessorException.class, () -> {
            target.saveToFile(CheckTest.getCheck(), invalidFile);
        });

        assertEquals("Exception saving to file: invalid/path", thrown.getMessage(), "The exception message should contain the file path.");
    }

}
