package com.github.kaydunov.file_processor;

import com.github.kaydunov.dto.Statement;
import com.github.kaydunov.spring.Component;

import java.io.File;

@Component
public class StatementFileProcessor implements FileProcessor<Statement> {

    private static final String DIRECTORY_PATH = "statement";
    private static final String FILE_EXTENSION = ".txt";
    private static final String FILE_PREFIX = "statement_";

    @Override
    public File process(Statement statement) {
        File file = createFile(getNextStatementNumber());
        saveToFile(statement, file);
        return file;
    }

    private File createFile(int number) {
        String fileName = FILE_PREFIX + number + FILE_EXTENSION;
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return new File(directory, fileName);
    }

    private static int getNextStatementNumber() {
        File dir = new File(DIRECTORY_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
            return 1;
        }
        int maxNumber = getMaxNumberFromDirectory(dir);
        return maxNumber + 1;
    }

    private static int getMaxNumberFromDirectory(File dir) {
        int maxNumber = 0;
        File[] statementFiles = dir.listFiles((d, name) -> name.startsWith("statement_") && name.endsWith(".txt"));
        for (File file : statementFiles) {
            String fileName = file.getName();
            String numberPart = fileName.substring(10, fileName.length() - 4); // выделяем число из имени
            try {
                int number = Integer.parseInt(numberPart);
                if (number > maxNumber) {
                    maxNumber = number;
                }
            } catch (NumberFormatException e) {
                // игнорируем файлы, которые не соответствуют шаблону
            }
        }
        return maxNumber;
    }

}
