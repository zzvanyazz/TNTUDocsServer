package com.tntu.server.docs.core.data.exceptions.storage.file;

import com.tntu.server.docs.core.data.exceptions.storage.file.FileException;

public class DeleteFileException extends FileException {
    public DeleteFileException() {
        super("Could not delete file.");
    }
}
