package com.tntu.server.docs.core.data.exceptions.storage.file;

public class FileNotExistsException extends FileException {
    public FileNotExistsException() {
        super("File not exists.");
    }
}
