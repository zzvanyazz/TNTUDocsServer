package com.tntu.server.docs.core.models.exceptions.file;

public class CanNotReadFileException extends Exception {
    public CanNotReadFileException() {
        super("Could not read file.");
    }
}
