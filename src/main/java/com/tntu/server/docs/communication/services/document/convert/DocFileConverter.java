package com.tntu.server.docs.communication.services.document.convert;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.document.DocumentFormat;
import org.springframework.stereotype.Service;

@Service
public class DocFileConverter extends JodConverterService {


    @Override
    public String getFormat() {
        return ".doc";
    }

    @Override
    public @NonNull DocumentFormat getDocumentFormat() {
        return DefaultDocumentFormatRegistry.DOC;
    }
}
