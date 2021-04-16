package com.tntu.server.docs.core.services;

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

import java.util.List;
import java.util.stream.Collectors;

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

    public DocumentModel createDocument(DocumentModel document)
            throws SectionNotExistsException, DocumentAlreadyExistsException {

        if (documentRepository.exists(document.getName()))
            throw new DocumentAlreadyExistsException();

        return save(document);
    }

    public DocumentModel updateDocument(long id, DocumentModel newDocument) throws Exception {
        var document = getDocument(id);

        var updater = new Updater<>(document, newDocument);
        updater.update(DocumentModel::getSectionId, DocumentModel::setSectionId);
        updater.update(DocumentModel::getName, DocumentModel::setName);
        updater.update(DocumentModel::getCreateTime, DocumentModel::setCreateTime);

        return save(document);
    }

    public void delete(long id) throws DocumentNotExistsException, SectionNotExistsException, InvalidResourceException, DeleteFileException {
        var document = getDocument(id);
        var sectionName = sectionService.getSection(document.getSectionId()).getName();

        var fileLocation = storageService.combine(sectionName, document.getName());
        storageService.deleteFile(fileLocation);
        documentRepository.delete(id);
    }

    public List<DocumentModel> findDocuments(String name) {
        return documentRepository.getAll()
                .stream()
                .filter(documentModel -> documentModel.getName().contains(name))
                .collect(Collectors.toList());
    }

    private DocumentModel save(DocumentModel document) throws SectionNotExistsException {
        if (!sectionService.isExists(document.getSectionId()))
            throw new SectionNotExistsException();
        document = documentRepository.save(document);

        return document;
    }

    public void uploadFile(long id, MultipartFile file)
            throws DocumentNotExistsException, SectionNotExistsException, InvalidResourceException,
            DeleteFileException, FileAlreadyExistsException, CanNotWriteFileException, CanNotCreateDirectoryException {
        var document = getDocument(id);
        if (file != null && !file.isEmpty()) {
            var section = sectionService.getSection(document.getSectionId());
            var sectionName = section.getName();
            if (!storageService.isExists(sectionName))
                storageService.createDirectory(sectionName);

            var originalFilename = file.getOriginalFilename();
            var fileName = String.valueOf(id);
            if (originalFilename != null)
                fileName += originalFilename.substring(originalFilename.lastIndexOf("."));

            filesService.saveOrRewrite(sectionName, fileName, file);
        }

    }


}
