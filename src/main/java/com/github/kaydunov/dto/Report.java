package com.github.kaydunov.dto;

import java.io.File;

/**
 * Entity marked this interface can be saved to directories
 * @see com.github.kaydunov.exporter.FileExporter
 */
public abstract class Report {

    private String fileDirectory;

    /**
     * Returns the directory where the report will be saved.
     */
    public String getFileDirectory() {
        if (fileDirectory == null) {
            fileDirectory = getReportType().toLowerCase();
        }
        return fileDirectory;//TODO fix package to statement-money
    }

    /**
     * Returns a prefix for the report file name.
     * Can be overridden by subclasses if needed.
     */
    public String getFilePrefix() {
        return getReportType().toLowerCase();
    }

    /**
     * Returns the type of the report (e.g., "statement", "check").
     */
    protected String getReportType() {
        return this.getClass().getSimpleName();
    }

    /**
     * Generates a unique file path for the report.
     */
    public String getFilePath() {
        String fileDirectoryPath = getFileDirectory();
        String filePrefix = getFilePrefix();
        return fileDirectoryPath + File.separator + filePrefix + "_" + getNextNumber();
    }

    /**
     * Determines the next unique number for the report file.
     */
    public int getNextNumber() {
        File dir = new File(getFileDirectory());
        if (!dir.exists()) {
            dir.mkdirs();
            return 1;
        }
        return getMaxNumberFromDirectory(dir) + 1;
    }

    /**
     * Finds the maximum number from the existing report files in the directory.
     */
    private int getMaxNumberFromDirectory(File dir) {
        String filePrefix = getFilePrefix();
        int maxNumber = 0;

        File[] reportFiles = dir.listFiles((d, name) -> name.startsWith(filePrefix));
        if (reportFiles == null) return maxNumber;

        for (File file : reportFiles) {
            String fileName = file.getName();
            String numberPart = extractNumberPart(fileName, filePrefix);

            try {
                int number = Integer.parseInt(numberPart);
                maxNumber = Math.max(maxNumber, number);
            } catch (NumberFormatException e) {
                // Ignore files that do not match the expected format
            }
        }
        return maxNumber;
    }

    /**
     * Extracts the numeric part of the file name.
     */
    private String extractNumberPart(String fileName, String filePrefix) {
        int prefixLength = filePrefix.length() + 1;
        int extensionIndex = fileName.lastIndexOf('.');
        if (extensionIndex == -1) extensionIndex = fileName.length();

        return fileName.substring(prefixLength, extensionIndex);
    }
}


