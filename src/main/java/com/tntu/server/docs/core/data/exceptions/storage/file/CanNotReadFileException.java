package com.tntu.server.docs.core.data.exceptions.storage.file;

import com.tntu.server.docs.core.data.exceptions.storage.file.FileException;

public class CanNotReadFileException extends FileException {
    public CanNotReadFileException() {
        super("Could not read file.");
    }
}
