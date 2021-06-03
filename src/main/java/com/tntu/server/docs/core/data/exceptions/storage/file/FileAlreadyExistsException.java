package com.tntu.server.docs.core.data.exceptions.storage.file;

import com.tntu.server.docs.core.data.exceptions.storage.file.FileException;

public class FileAlreadyExistsException extends FileException {
    public FileAlreadyExistsException() {
        super("File already exists.");
    }
}
