package com.tntu.server.docs.core.services;

import com.tntu.server.docs.core.data.enums.DocumentStatus;
import com.tntu.server.docs.core.data.exceptions.docs.DocumentAlreadyExistsException;
import com.tntu.server.docs.core.data.exceptions.docs.DocumentNotExistsException;
import com.tntu.server.docs.core.data.exceptions.file.*;
import com.tntu.server.docs.core.data.exceptions.section.SectionNotExistsException;
import com.tntu.server.docs.core.data.models.docs.DocumentModel;
import com.tntu.server.docs.core.repositories.DocumentRepository;
import com.tntu.server.docs.core.services.storage.StorageService;
import com.tntu.server.docs.core.utils.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private FilesService filesService;

    @Autowired
    private StorageService storageService;

    public DocumentModel getDocument(long id) throws DocumentNotExistsException {
        return documentRepository.getDocument(id)
                .orElseThrow(DocumentNotExistsException::new);
    }

    public MultipartFile loadFile(long id)
            throws DocumentNotExistsException, SectionNotExistsException,
            InvalidResourceException, CanNotReadFileException, FileNotExistsException {

        var documentId = getDocument(id).getId();
        var section = sectionService.getSection(documentId);
        var location = storageService.combine(section.getName(), String.valueOf(documentId));

        return filesService.getFile(location);
    }

    public List<DocumentModel> getDocumentsBySection(long sectionId) throws SectionNotExistsException {
        if (!sectionService.isExists(sectionId))
            throw new SectionNotExistsException();

        return documentRepository.getAllBySection(sectionId);
    }

    public DocumentModel createDocument(DocumentModel document, MultipartFile file)
            throws SectionNotExistsException, CanNotWriteFileException, InvalidResourceException,
            DeleteFileException, FileAlreadyExistsException, DocumentAlreadyExistsException {

        if (documentRepository.exists(document.getName()))
            throw new DocumentAlreadyExistsException();

        return save(document, file);
    }

    public DocumentModel updateDocument(long id, DocumentModel newDocument, MultipartFile file) throws Exception {
        var document = getDocument(id);

        var updater = new Updater<>(document, newDocument);
        updater.update(DocumentModel::getSectionId, DocumentModel::setSectionId);
        updater.update(DocumentModel::getName, DocumentModel::setName);
        updater.update(DocumentModel::getCreateTime, DocumentModel::setCreateTime);

        return save(document, file);
    }

    public void delete(long id) throws DocumentNotExistsException, SectionNotExistsException, InvalidResourceException, DeleteFileException {
        var document = getDocument(id);
        var sectionName = sectionService.getSection(document.getSectionId()).getName();

        var fileLocation = storageService.combine(sectionName, document.getName());
        storageService.deleteFile(fileLocation);
        documentRepository.delete(id);
    }

    private DocumentModel save(DocumentModel document, MultipartFile file)
            throws SectionNotExistsException, InvalidResourceException, DeleteFileException,
            FileAlreadyExistsException, CanNotWriteFileException {

        if (sectionService.isExists(document.getSectionId()))
            throw new SectionNotExistsException();
        document = documentRepository.save(document);

        if (file != null && !file.isEmpty()) {
            var section = sectionService.getSection(document.getSectionId());
            var sectionName = section.getName();
            var location = storageService.combine(sectionName, String.valueOf(document.getId()));
            filesService.saveOrRewrite(location, file);
        }

        return document;
    }


}
