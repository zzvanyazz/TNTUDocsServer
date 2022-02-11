package com.tntu.server.docs.communication.services.document.convert;

import com.tntu.server.docs.core.data.models.file.BytesMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.function.Function;

@Service
public interface ConvertService {

    default MultipartFile toPdf(MultipartFile file) {
        var pdfFileBytes = getBytes(file);
        var fileName = file.getName();
        var pdfFileName = getPartFromDot(fileName, dotIndex -> fileName.substring(0, dotIndex)) + ".pdf";
        return new BytesMultipartFile(pdfFileName, pdfFileBytes);
    }

    String getFormat();

    byte[] getBytes(MultipartFile file);

    private String getPartFromDot(String name, Function<Integer, String> dotIndexConsumer) {
        var dotIndex = name.lastIndexOf(".");
        return dotIndex != -1 ? dotIndexConsumer.apply(dotIndex) : name;
    }

}
