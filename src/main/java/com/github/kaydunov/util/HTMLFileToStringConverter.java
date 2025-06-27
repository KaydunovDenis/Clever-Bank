package com.github.kaydunov.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HTMLFileToStringConverter {
    public static String convertHTMLFileToString(String filePath) throws IOException {
        StringBuilder htmlContent = new StringBuilder();

        // Create a buffered reader to read the lines of the HTML file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                htmlContent.append(line).append("\n");
            }
        }

        return htmlContent.toString();
    }
}
