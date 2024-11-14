package com.github.kaydunov.service;

import com.github.kaydunov.dto.Statement;
import com.github.kaydunov.entity.StatementTest;
import com.github.kaydunov.exporter.FileExporter;
import com.github.kaydunov.exporter.TxtExporter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

class TxtExporterTest {

    private final FileExporter target = new TxtExporter();

    @Test
    void export() {
        //Arrange Statement(s)
        Statement statement = StatementTest.getStatement();

        //Act Statement(s)
        File file1 = target.export(statement);
        File file2 = target.export(statement);

        //Assert statement(s)
        Assertions.assertTrue(file1.exists());
        Assertions.assertTrue(file2.exists());

        int file1Number = getFileNumberFromName(file1);
        int file2Number = getFileNumberFromName(file2);
        Assertions.assertTrue(file1Number < file2Number);

        if (file1.exists() && file2.exists()) {
            file1.delete();
            file2.delete();
            assert true;
        } else {
            assert false;
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