package com.github.kaydunov.service;

import com.github.kaydunov.dto.Check;
import com.github.kaydunov.dto.CheckTest;
import com.github.kaydunov.exporter.FileExporter;
import com.github.kaydunov.exporter.PdfExporter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

class PdfExporterTest {

    private final FileExporter target = new PdfExporter();

    @Test
    void export() {
        Check check = CheckTest.createCheck();

        //Act Statement(s)
        File file = target.export(check);

        //Arrange Statement(s)
        assertTrue(file.getPath().contains("check/check_"));
        assertTrue(file.getPath().contains(".pdf"));
        if (file.exists()) {
            Assertions.assertTrue(file.delete());
        }
    }

}
