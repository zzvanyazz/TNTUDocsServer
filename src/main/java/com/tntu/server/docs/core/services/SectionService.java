package com.tntu.server.docs.core.services;

import com.tntu.server.docs.core.data.exceptions.docs.DocumentNotExistsException;
import com.tntu.server.docs.core.data.exceptions.file.CanNotCreateDirectoryException;
import com.tntu.server.docs.core.data.exceptions.file.DeleteFileException;
import com.tntu.server.docs.core.data.exceptions.file.InvalidResourceException;
import com.tntu.server.docs.core.data.exceptions.section.SectionAlreadyExistsException;
import com.tntu.server.docs.core.data.exceptions.section.SectionNotExistsException;
import com.tntu.server.docs.core.data.models.docs.SectionModel;
import com.tntu.server.docs.core.repositories.SectionRepository;
import com.tntu.server.docs.core.services.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private StorageService storageService;

    public List<SectionModel> getAllSections() {
        return sectionRepository.getAllSections();
    }

    public SectionModel getSection(long id) throws SectionNotExistsException {
        return sectionRepository.getSection(id)
                .orElseThrow(SectionNotExistsException::new);
    }

    public SectionModel getSection(String name) throws SectionNotExistsException {
        return sectionRepository.getSection(name)
                .orElseThrow(SectionNotExistsException::new);
    }

    public SectionModel updateSection(long id, String name) throws SectionNotExistsException {
        var section = getSection(id);
        section.setName(name);

        return sectionRepository.save(section);
    }

    public SectionModel createSection(SectionModel model)
            throws SectionAlreadyExistsException, InvalidResourceException, CanNotCreateDirectoryException {
        var name = model.getName();
        if (sectionRepository.exists(name))
            throw new SectionAlreadyExistsException();

        if (!storageService.isExists(name))
            storageService.createDirectory(name);

        return sectionRepository.save(model);
    }

    public void deleteSection(long id) throws SectionNotExistsException {
        if (!sectionRepository.exists(id))
            throw new SectionNotExistsException();

        var section = getSection(id);
        for (var document : section.getDocuments()) {
            var documentId = document.getId();
            try {
                documentService.delete(documentId);
            } catch (DocumentNotExistsException | InvalidResourceException | DeleteFileException e) {
                LOG.warn("Can not delete document", e);
            }
        }


        sectionRepository.deleteSection(id);
    }

    public boolean isExists(long id) {
        return sectionRepository.exists(id);
    }

}
