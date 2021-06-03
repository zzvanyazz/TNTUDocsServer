package com.tntu.server.docs.core.data.exceptions.storage.file;

import com.tntu.server.docs.core.data.exceptions.storage.file.FileException;

public class CanNotWriteFileException extends FileException {
    public CanNotWriteFileException() {
        super("Can not write to file.");
    }
}
