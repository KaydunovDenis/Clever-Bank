package com.github.kaydunov.service;

import com.github.kaydunov.dto.TXT;
import com.github.kaydunov.exception.FileProcessorException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public interface FileProcessor<T extends TXT> {
    void process(T object);

    default void saveToFile(T object, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(object.toString());
        } catch (IOException e) {
            throw new FileProcessorException(file.getPath(), e);
        }
    }
}
