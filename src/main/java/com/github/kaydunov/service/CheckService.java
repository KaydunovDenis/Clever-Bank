package com.github.kaydunov.service;

import com.github.kaydunov.entity.Check;
import com.github.kaydunov.exception.CheckServiceException;
import com.github.kaydunov.spring.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class CheckService {

    private static final String PATH = "check";
    private static final String FILE_EXTENSION = ".txt";
    private static final String FILE_PREFIX = "check_";

    public void processCheck(Check check) {
        File file = createFile(check.getNumber());
        saveCheckToFile(check, file);
    }

    private File createFile(int number) {
        String fileName = FILE_PREFIX + number + FILE_EXTENSION;
        File directory = new File(PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return new File(directory, fileName);
    }

    private void saveCheckToFile(Check check, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(check.toString());
        } catch (IOException e) {
            throw new CheckServiceException(file.getPath(), e);
        }
    }
}
