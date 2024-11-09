package com.github.kaydunov.exporter;

import com.github.kaydunov.dto.Report;
import com.github.kaydunov.exception.FileExporterException;
import com.github.kaydunov.spring.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class TxtExporter implements FileExporter {
    private static final String TXT_EXTENSION = ".txt";


    @Override
    public File export(Report report) {
        File file = new File(report.getFilePath() + TXT_EXTENSION);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(report.toString());
        } catch (IOException e) {
            throw new FileExporterException(e.getMessage(), e);
        }
        return file;
    }
}
