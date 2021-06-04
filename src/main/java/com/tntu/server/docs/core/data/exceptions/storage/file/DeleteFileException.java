package com.tntu.server.docs.core.data.exceptions.storage.file;

public class DeleteFileException extends FileException {
    public DeleteFileException() {
        super("Could not delete file.");
    }
}
