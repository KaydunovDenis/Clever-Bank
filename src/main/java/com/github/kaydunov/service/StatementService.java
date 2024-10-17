package com.github.kaydunov.service;

import com.github.kaydunov.dto.Statement;
import com.github.kaydunov.spring.Component;

import java.io.File;

@Component
public class StatementService implements Process<Statement> {

    private static final String PATH = "statement";
    private static final String FILE_EXTENSION = ".txt";
    private static final String FILE_PREFIX = "statement_";

    @Override
    public void process(Statement statement) {
        File file = createFile(statement.getNumber());
        saveToFile(statement, file);
    }

    private File createFile(int number) {
        String fileName = FILE_PREFIX + number + FILE_EXTENSION;
        File directory = new File(PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return new File(directory, fileName);
    }


}
