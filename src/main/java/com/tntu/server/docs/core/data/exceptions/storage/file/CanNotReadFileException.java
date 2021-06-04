package com.tntu.server.docs.core.data.exceptions.storage.file;

public class CanNotReadFileException extends FileException {
    public CanNotReadFileException() {
        super("Could not read file.");
    }
}
