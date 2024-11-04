package com.github.kaydunov.service;

import com.github.kaydunov.dto.Check;
import com.github.kaydunov.spring.Component;

import java.io.File;

@Component
public class CheckService implements FileProcessor<Check> {

    private static final String PATH = "check";
    private static final String FILE_EXTENSION = ".txt";
    private static final String FILE_PREFIX = "check_";

    @Override
    public File process(Check check) {
        File file = createFile(check.getNumber());
        saveToFile(check, file);
        return file;
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
