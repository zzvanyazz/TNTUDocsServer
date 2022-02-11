package com.tntu.server.docs.communication.services.document.convert;

import com.tntu.server.docs.communication.models.exceptions.CanNotConvertDocumentException;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class DocxFileConverter implements ConvertService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Override
    public String getFormat() {
        return ".docx";
    }

    @Override
    public byte[] getBytes(MultipartFile file) {
        try {
            var fileBytes = file.getBytes();
            var fileBytesStream = new ByteArrayInputStream(fileBytes);
            var xwpfDocument = new XWPFDocument(fileBytesStream);
            var pdfFileOutputStream = new ByteArrayOutputStream();
            PdfConverter.getInstance().convert(xwpfDocument, pdfFileOutputStream, PdfOptions.create());
            return pdfFileOutputStream.toByteArray();
        } catch (Exception e) {
            log.error("Can not convert to pdf", e);
            throw new CanNotConvertDocumentException();
        }
    }




}
