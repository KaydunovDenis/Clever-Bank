package com.github.kaydunov.exporter;

import com.github.kaydunov.dto.Report;
import com.github.kaydunov.exception.FileExporterException;
import com.github.kaydunov.spring.Component;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class PdfExporter implements FileExporter {

    private static final String PDF_EXTENSION = ".pdf";
    private Font font;

    public PdfExporter() {
        try {
            BaseFont baseFont = BaseFont.createFont("fonts/cour.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            font = new com.itextpdf.text.Font(baseFont, 12);
        } catch (DocumentException | IOException e) {
            throw new FileExporterException(e.getMessage(), e);
        }
    }

    @Override
    public File export(Report report) {
        File file = new File(report.getFilePath() + PDF_EXTENSION);
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));

            document.open();
            Paragraph paragraph = new Paragraph(report.toString(), font);
            paragraph.setFont(font);
            document.add(paragraph);
        } catch (DocumentException | FileNotFoundException e) {
            throw new FileExporterException(e.getMessage(), e);
        }
        document.close();
        return file;
    }
}
