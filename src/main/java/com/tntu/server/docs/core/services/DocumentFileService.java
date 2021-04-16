package com.tntu.server.docs.core.services;

import com.tntu.server.docs.core.data.exceptions.docs.DocumentNotExistsException;
import com.tntu.server.docs.core.data.exceptions.file.*;
import com.tntu.server.docs.core.data.exceptions.section.SectionNotExistsException;
import com.tntu.server.docs.core.data.models.docs.DocumentModel;
import com.tntu.server.docs.core.services.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentFileService {

    @Autowired
    private StorageService storageService;

    @Autowired
    private FilesService filesService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private DocumentService documentService;


    public void saveFile(long documentId, MultipartFile file)
            throws DocumentNotExistsException, SectionNotExistsException,
            FileAlreadyExistsException, InvalidResourceException, CanNotWriteFileException {

        var document = documentService.getDocument(documentId);
        saveFile(document, file);
    }

    public void saveFile(DocumentModel document, MultipartFile file)
            throws SectionNotExistsException, FileAlreadyExistsException,
            InvalidResourceException, CanNotWriteFileException {

        var sectionId = document.getSectionId();
        var originalFilename = document.getFileName();
        saveFile(sectionId, originalFilename, file);
    }

    public void saveFile(long sectionId, String name, MultipartFile file)
            throws SectionNotExistsException, CanNotWriteFileException,
            FileAlreadyExistsException, InvalidResourceException {

        var sectionName = sectionService.getSection(sectionId).getName();
        saveFile(sectionName, name, file);
    }

    public void saveFile(String sectionName, String fileName, MultipartFile file)
            throws InvalidResourceException, CanNotWriteFileException, FileAlreadyExistsException {
        var location = storageService.combine(sectionName, fileName);
        storageService.saveFile(location, file);
    }

}
