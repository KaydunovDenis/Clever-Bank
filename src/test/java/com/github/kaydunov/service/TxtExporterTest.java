package com.github.kaydunov.service;

import com.github.kaydunov.dto.Check;
import com.github.kaydunov.dto.Statement;
import com.github.kaydunov.dto.CheckTest;
import com.github.kaydunov.dto.StatementTest;
import com.github.kaydunov.exporter.FileExporter;
import com.github.kaydunov.exporter.TxtExporter;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;


class TxtExporterTest {

    private final FileExporter target = new TxtExporter();

    @Test
    void checkFileNumber() {
        //Arrange
        Statement statement = StatementTest.createStatement();

        //Act
        File file1 = target.export(statement);
        File file2 = target.export(statement);

        //Assert
          assertTrue(file1.exists());
          assertTrue(file2.exists());

        int file1Number = getFileNumberFromName(file1);
        int file2Number = getFileNumberFromName(file2);
          assertTrue(file1Number < file2Number);

        if (file1.exists() && file2.exists()) {
            assertTrue(file1.delete());
            assertTrue(file2.delete());
        }
    }

    @Test
    void exportCheckToTxt() {
        //Arrange
        Check check = CheckTest.createCheck();

        //Act
        File result = target.export(check);

        //Assert
          assertTrue(result.exists());
          assertTrue(result.getPath().contains("check/check_"));
          assertTrue(result.getPath().contains(".txt"));
        if (result.exists()) {
              assertTrue(result.delete());
        }
    }

    @Test
    void exportStatementToTxt() {
        //Arrange
        Statement statement = StatementTest.createStatement();

        //Act
        File result = target.export(statement);

        //Assert
        assertTrue(result.exists());
        assertTrue(result.getPath().contains("statement-money/statement_"));
        assertTrue(result.getPath().contains(".txt"));
        if (result.exists()) {
            assertTrue(result.delete());
        }
    }


    private static int getFileNumberFromName(File file) {
        String fileName = file.getName();
        // Проверяем, что имя файла соответствует формату
        if (fileName.matches("statement_\\d+\\.txt")) {
            // Извлекаем только числовую часть из имени файла
            String numberPart = fileName.replaceAll("\\D+", ""); // Оставляем только цифры
            return Integer.parseInt(numberPart); // Преобразуем в int
        } else {
            throw new IllegalArgumentException("Неверный формат имени файла: " + fileName);
        }
    }

}