package com.tntu.server.docs.core.data.exceptions.storage.file;

import com.tntu.server.docs.core.data.exceptions.storage.file.FileException;

public class FileNotExistsException extends FileException {
    public FileNotExistsException() {
        super("File not exists.");
    }
}
