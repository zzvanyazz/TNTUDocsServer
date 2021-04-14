package com.tntu.server.docs.core.data.exceptions.file;

public class FileNotExistsException extends Exception {
    public FileNotExistsException() {
        super("File not exists.");
    }
}
