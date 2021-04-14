package com.tntu.server.docs.core.data.exceptions.section;

public class SectionAlreadyExistsException extends Exception {
    public SectionAlreadyExistsException() {
        super("Section already exists");
    }
}
