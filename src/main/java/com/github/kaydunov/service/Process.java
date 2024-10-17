package com.github.kaydunov.service;

import com.github.kaydunov.dto.TXT;
import com.github.kaydunov.exception.CheckServiceException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public interface Process<T extends TXT> {
    void process(T object);

    default void saveToFile(T object, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(object.toString());
        } catch (IOException e) {
            throw new CheckServiceException(file.getPath(), e);
        }
    }
}
