package com.tntu.server.docs.core.data.exceptions.docs;

public class DocumentNotExistsException extends DocumentException {

    public DocumentNotExistsException() {
        super("Document not exists.");
    }
}
