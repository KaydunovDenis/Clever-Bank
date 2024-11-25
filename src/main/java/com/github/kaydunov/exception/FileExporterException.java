package com.github.kaydunov.exception;

public class FileExporterException extends RuntimeException  {
    public FileExporterException(String message, Exception e) {
        super(message, e);
    }

    public FileExporterException(String s) {
        super(s);
    }

}
