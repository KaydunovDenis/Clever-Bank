package com.github.kaydunov.exporter;

import com.github.kaydunov.spring.Autowired;

import java.util.List;

public class FileExporterFactory {

    @Autowired
    private List<FileExporter> exporters;

    public static FileExporter getExporter(String format) {
        exporters.get
        format.toLowerCase()

    //default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
    }
}
