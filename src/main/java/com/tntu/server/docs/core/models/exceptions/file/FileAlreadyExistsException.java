package com.tntu.server.docs.core.models.exceptions.file;

public class FileAlreadyExistsException extends Exception {
    public FileAlreadyExistsException() {
        super("File already exists.");
    }
}
