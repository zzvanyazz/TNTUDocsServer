package com.tntu.server.docs.communication.services.document;

import com.tntu.server.docs.communication.models.exceptions.CanNotConvertDocumentException;
import com.tntu.server.docs.communication.options.VisualisationOptions;
import com.tntu.server.docs.core.data.exceptions.DocsException;
import com.tntu.server.docs.core.data.models.file.BytesMultipartFile;
import com.tntu.server.docs.core.services.DocumentService;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

@Service
public class VisualiseDocumentService {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private VisualisationOptions visualisationOptions;

    private final ConcurrentMap<Long, CashedFile> pdfFileCash = new ConcurrentHashMap<>();

    public void removeFromCash(long id) {
        pdfFileCash.remove(id);
    }

    public MultipartFile loadDocumentVisualisation(long id) throws DocsException {
        var cashedFile =  pdfFileCash.computeIfAbsent(id, this::loadFileVisualisation);
        cashedFile.deferRemoving(visualisationOptions.cashLivingTime);
        return cashedFile.getFile();
    }

    private CashedFile loadFileVisualisation(long id) throws DocsException {
        var model = documentService.getDocument(id);
        var fileName = model.getFileName();
        var fileExtension = getPartFromDot(fileName, fileName::substring);
        if (!visualisationOptions.convertableExtensions.contains(fileExtension))
            throw new CanNotConvertDocumentException();

        var file = documentService.loadFile(id);
        var pdfFile = convertFile(file);
        return new CashedFile(pdfFile, () -> removeFromCash(id));
    }

    public MultipartFile convertFile(MultipartFile file) {
        var pdfFileBytes = getBytes(file);
        var fileName = file.getName();
        var pdfFileName = getPartFromDot(fileName, dotIndex -> fileName.substring(0, dotIndex)) + ".pdf";
        return new BytesMultipartFile(pdfFileName, pdfFileBytes);
    }

    private byte[] getBytes(MultipartFile file) {
        try {
            var fileBytes = file.getBytes();
            var fileBytesStream = new ByteArrayInputStream(fileBytes);
            var xwpfDocument = new XWPFDocument(fileBytesStream);
            var pdfFileOutputStream = new ByteArrayOutputStream();
            PdfConverter.getInstance().convert(xwpfDocument, pdfFileOutputStream, PdfOptions.create());
            return pdfFileOutputStream.toByteArray();
        } catch (Exception e) {
            throw new CanNotConvertDocumentException();
        }
    }

    private String getPartFromDot(String name, Function<Integer, String> dotIndexConsumer) {
        var dotIndex = name.lastIndexOf(".");
        return dotIndex != -1 ? dotIndexConsumer.apply(dotIndex) : name;
    }

    private static class CashedFile {

        private final MultipartFile file;
        private final Runnable removing;
        private Timer timer;


        public CashedFile(MultipartFile file, Runnable removing) {
            this.file = file;
            this.timer = new Timer();
            this.removing = removing;
        }

        public MultipartFile getFile() {
            byte[] srcBytes;
            try {
                srcBytes = file.getBytes();
            } catch (IOException e) {
                throw new CanNotConvertDocumentException();
            }
            var bytes = new byte[srcBytes.length];
            System.arraycopy(srcBytes, 0, bytes, 0, bytes.length);
            return new BytesMultipartFile(file.getName(), bytes);
        }

        public void deferRemoving(long hour) {
            timer.cancel();
            schedule(hour);
        }

        private void schedule(long hour) {
            this.timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    removing.run();
                }
            }, hour * 60 * 60 * 1000);
        }

    }

}
