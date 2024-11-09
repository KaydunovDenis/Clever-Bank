package com.github.kaydunov.exporter;

import com.github.kaydunov.dto.Report;
import com.github.kaydunov.exception.FileExporterException;
import com.github.kaydunov.spring.Component;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Component
public class PdfExporter implements FileExporter {

    private static final String PDF_EXTENSION = ".pdf";

    @Override
    public File export(Report report) {
        File file = new File(report.getFilePath() + PDF_EXTENSION);
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            document.add(new Paragraph(report.toString()));
        } catch (DocumentException | FileNotFoundException e) {
            throw new FileExporterException(e.getMessage(), e);
        }
        document.close();
        return file;
    }
}
