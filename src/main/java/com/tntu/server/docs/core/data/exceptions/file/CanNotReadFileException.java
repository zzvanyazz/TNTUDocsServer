package com.tntu.server.docs.core.data.exceptions.file;

public class CanNotReadFileException extends Exception {
    public CanNotReadFileException() {
        super("Could not read file.");
    }
}
