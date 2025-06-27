package com.github.kaydunov.service;

import com.github.kaydunov.dto.Check;
import com.github.kaydunov.dto.CheckTest;
import com.github.kaydunov.dto.Statement;
import com.github.kaydunov.dto.StatementTest;
import com.github.kaydunov.exporter.FileExporter;
import com.github.kaydunov.exporter.PdfExporter;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PdfExporterTest {

    private final FileExporter target = new PdfExporter();

    @Test
    void exportCheckToPdf() {
        Check check = CheckTest.createCheck();

        //Act Statement(s)
        File result = target.export(check);

        //Arrange Statement(s)
        assertTrue(result.getPath().contains("check/check_"));
        assertTrue(result.getPath().contains(".pdf"));
        if (result.exists()) {
            assertTrue(result.delete());
        }
    }

    @Test
    void exportStatementToPdf() {
        //Arrange
        Statement statement = StatementTest.createStatement();

        //Act
        File result = target.export(statement);

        //Arrange Statement(s)
        assertTrue(result.getPath().contains("statement-money/statement_"));
        assertTrue(result.getPath().contains(".pdf"));
        if (result.exists()) {
            assertTrue(result.delete());
        }
    }

}
