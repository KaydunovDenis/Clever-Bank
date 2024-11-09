package com.github.kaydunov.exporter;

import com.github.kaydunov.dto.Report;
import com.github.kaydunov.exception.FileExporterException;

import java.io.File;

public interface FileExporter {

    File export(Report report) throws FileExporterException;

}
