package com.tntu.server.docs.core.models.exceptions.file;

public class CanNotWriteFileException extends Exception {
    public CanNotWriteFileException() {
        super("Can not write to file.");
    }
}
