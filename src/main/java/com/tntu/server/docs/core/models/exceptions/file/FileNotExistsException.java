package com.tntu.server.docs.core.models.exceptions.file;

public class FileNotExistsException extends Exception {
    public FileNotExistsException() {
        super("File not exists.");
    }
}
