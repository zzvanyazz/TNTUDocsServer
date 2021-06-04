package com.tntu.server.docs.core.data.exceptions.storage.file;

public class CanNotWriteFileException extends FileException {
    public CanNotWriteFileException() {
        super("Can not write to file.");
    }
}
