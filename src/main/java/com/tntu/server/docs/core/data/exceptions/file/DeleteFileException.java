package com.tntu.server.docs.core.data.exceptions.file;

public class DeleteFileException extends Exception {
    public DeleteFileException() {
        super("Could not delete file.");
    }
}
