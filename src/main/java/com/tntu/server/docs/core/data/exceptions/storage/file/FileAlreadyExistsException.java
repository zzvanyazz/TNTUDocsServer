package com.tntu.server.docs.core.data.exceptions.storage.file;

public class FileAlreadyExistsException extends FileException {
    public FileAlreadyExistsException() {
        super("File already exists.");
    }
}
