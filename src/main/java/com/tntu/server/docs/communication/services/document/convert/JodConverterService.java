package com.tntu.server.docs.communication.services.document.convert;

import com.tntu.server.docs.communication.models.exceptions.CanNotConvertDocumentException;
import com.tntu.server.docs.communication.options.ConverterOptions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.document.DocumentFormat;
import org.jodconverter.local.JodConverter;
import org.jodconverter.local.office.LocalOfficeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public abstract class JodConverterService implements ConvertService {


    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public abstract String getFormat();

    public abstract @NonNull DocumentFormat getDocumentFormat();

    @Override
    public byte[] getBytes(MultipartFile file) throws CanNotConvertDocumentException {
        try {
            var fileBytes = file.getBytes();
            var fileBytesStream = new ByteArrayInputStream(fileBytes);
            var pdfFileOutputStream = new ByteArrayOutputStream();

            JodConverter
                    .convert(fileBytesStream)
                    .as(getDocumentFormat())
                    .to(pdfFileOutputStream)
                    .as(DefaultDocumentFormatRegistry.PDF)
                    .execute();
            return pdfFileOutputStream.toByteArray();
        } catch (Exception e) {
            log.error("Can not convert to pdf", e);
            throw new CanNotConvertDocumentException();
        }
    }


}
