package com.tntu.server.docs.core.services;

import com.tntu.server.docs.core.data.enums.DocumentStatus;
import com.tntu.server.docs.core.data.exceptions.docs.DocumentAlreadyExistsException;
import com.tntu.server.docs.core.data.exceptions.docs.DocumentNotExistsException;
import com.tntu.server.docs.core.data.exceptions.file.CanNotCreateDirectoryException;
import com.tntu.server.docs.core.data.exceptions.file.InvalidResourceException;
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

    private static final String ARCHIVED_LOCATION = "Archived";

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private FilesService filesService;

    @Autowired
    private StorageService storageService;

    @PostConstruct
    protected void postConstruct() {
        try {
            if (!storageService.isExists(ARCHIVED_LOCATION))
                storageService.createDirectory(ARCHIVED_LOCATION);
        } catch (CanNotCreateDirectoryException | InvalidResourceException e) {
            e.printStackTrace();
        }
    }

    public DocumentModel getDocument(long id) throws DocumentNotExistsException {
        return documentRepository.getDocument(id)
                .orElseThrow(DocumentNotExistsException::new);
    }

    public MultipartFile loadFile(long id) throws Exception {
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

    public DocumentModel createDocument(DocumentModel document, MultipartFile file) throws Exception {
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

    private DocumentModel save(DocumentModel document, MultipartFile file) throws Exception {
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
