package com.github.kaydunov.exception;

public class FileExporterException extends RuntimeException  {
    public FileExporterException(String s, Exception e) {
        super("Exception saving to file: " + s, e);
    }

    public FileExporterException(String s) {
        super(s);
    }
}
