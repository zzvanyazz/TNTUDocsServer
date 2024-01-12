package com.tntu.server.docs.core.services;

import com.tntu.server.docs.core.data.exceptions.DocsException;
import com.tntu.server.docs.core.data.exceptions.docs.DocumentAlreadyExistsException;
import com.tntu.server.docs.core.data.exceptions.docs.DocumentNotExistsException;
import com.tntu.server.docs.core.data.exceptions.section.SectionNotExistsException;
import com.tntu.server.docs.core.data.exceptions.storage.file.FileNotExistsException;
import com.tntu.server.docs.core.data.models.docs.DocumentModel;
import com.tntu.server.docs.core.repositories.DocumentRepository;
import com.tntu.server.docs.core.utils.Updater;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private FilesService filesService;


    @Transactional(readOnly = true)
    public DocumentModel getDocument(long id) throws DocumentNotExistsException {
        return documentRepository.getDocument(id)
                .orElseThrow(DocumentNotExistsException::new);
    }

    @Transactional(readOnly = true)
    public MultipartFile loadFile(long id) throws DocsException {

        var document = getDocument(id);
        var section = sectionService.getSection(document.getSectionId());

        var sectionName = section.getName();
        var originalFileName = document.getFileName();

        if (originalFileName == null || originalFileName.isEmpty())
            throw new FileNotExistsException();

        var location = filesService.combine(sectionName, originalFileName);
        var fileName = document.getName();
        if (originalFileName.contains("."))
            fileName += originalFileName.substring(originalFileName.lastIndexOf("."));
        return filesService.getFile(location, fileName);
    }

    @Transactional(readOnly = true)
    public List<DocumentModel> getDocumentsBySection(long sectionId) throws SectionNotExistsException {
        if (!sectionService.isExists(sectionId))
            throw new SectionNotExistsException();

        return documentRepository.getAllBySection(sectionId);
    }

    @Transactional
    public DocumentModel createDocument(DocumentModel document) throws DocsException {
        if (documentRepository.exists(document.getName()))
            throw new DocumentAlreadyExistsException();

        return save(document);
    }

    @Transactional(readOnly = true)
    public void updateDocument(long id, DocumentModel newDocument) throws DocsException {
        var document = getDocument(id);
        var oldSectionId = document.getSectionId();
        var newSectionId = newDocument.getSectionId();

        if (newSectionId != null && !newSectionId.equals(oldSectionId))
            moveDocument(document, newSectionId);

        var updater = new Updater<>(document, newDocument);
        updater.update(DocumentModel::getSectionId, DocumentModel::setSectionId);
        updater.update(DocumentModel::getName, DocumentModel::setName);
        updater.update(DocumentModel::getCreateTime, DocumentModel::setCreateTime);
        updater.update(DocumentModel::getStatus, DocumentModel::setStatus);

        save(document);
    }

    @Transactional
    public void delete(long id) throws DocsException {
        var document = getDocument(id);
        var sectionName = sectionService.getSection(document.getSectionId()).getName();

        var fileLocation = filesService.combine(sectionName, document.getName());
        documentRepository.delete(id);
        filesService.deleteFile(fileLocation);
    }

    @Transactional(readOnly = true)
    public List<DocumentModel> findDocuments(String name) {
        return documentRepository.find(name);
    }


    private DocumentModel save(DocumentModel document) throws SectionNotExistsException {
        if (!sectionService.isExists(document.getSectionId()))
            throw new SectionNotExistsException();
        document = documentRepository.save(document);

        return document;
    }

    public void uploadFile(long id, MultipartFile file) throws DocsException {
        if (file == null || file.isEmpty())
            return;

        var document = getDocument(id);

        var section = sectionService.getSection(document.getSectionId());
        var sectionName = section.getName();

        if (!filesService.isExists(sectionName))
            filesService.createDirectory(sectionName);

        var originalFilename = file.getOriginalFilename();
        var fileName = String.valueOf(id);
        if (originalFilename != null && originalFilename.contains("."))
            fileName += originalFilename.substring(originalFilename.lastIndexOf("."));

        filesService.saveOrRewrite(sectionName, fileName, file);
        document.setFileName(fileName);
        documentRepository.save(document);
    }

    private void moveDocument(DocumentModel documentModel, Long newSectionId) throws DocsException {
        var oldSectionId = documentModel.getSectionId();

        var oldSection = sectionService.getSection(oldSectionId);
        var newSection = sectionService.getSection(newSectionId);

        var oldSectionName = oldSection.getName();
        var newSectionName = newSection.getName();

        var documentName = documentModel.getFileName();
        var oldFileLocation = filesService.combine(oldSectionName, documentName);

        if (filesService.isExists(newSectionName))
            filesService.createDirectory(newSectionName);

        filesService.moveFile(oldFileLocation, newSectionName);
    }

}
