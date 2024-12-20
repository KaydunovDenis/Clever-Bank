package com.github.kaydunov.exporter;

import com.github.kaydunov.exception.FileExporterException;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;

import java.util.List;

@Component
public class FileExporterFactory {

    @Autowired
    private List<FileExporter> exporters;


    public FileExporter getExporter(String format) {
        String beanName = format.toLowerCase() + "exporter";
        return exporters.stream()
                .filter(exporter -> getExporterName(exporter).equals(beanName))
                .findFirst()
                .orElseThrow(() -> new FileExporterException("Unsupported format: " + format));
    }

    private String getExporterName(FileExporter exporter) {
        return exporter.getClass().getSimpleName().toLowerCase();
    }

}

