package com.tntu.server.docs.core.services;

import com.tntu.server.docs.core.data.exceptions.DocsException;
import com.tntu.server.docs.core.data.exceptions.section.SectionAlreadyExistsException;
import com.tntu.server.docs.core.data.exceptions.section.SectionNotExistsException;
import com.tntu.server.docs.core.data.models.docs.SectionModel;
import com.tntu.server.docs.core.repositories.SectionRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SectionService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private FilesService filesService;

    @Transactional(readOnly = true)
    public List<SectionModel> getAllSections() {
        return sectionRepository.getAllSections();
    }

    @Transactional(readOnly = true)
    public SectionModel getSection(long id) throws SectionNotExistsException {
        return sectionRepository.getSection(id)
                .orElseThrow(SectionNotExistsException::new);
    }

    @Transactional(readOnly = true)
    public SectionModel getSection(String name) throws SectionNotExistsException {
        return sectionRepository.getSection(name)
                .orElseThrow(SectionNotExistsException::new);
    }

    @Transactional
    public SectionModel updateSection(long id, String newName) throws SectionNotExistsException {
        var section = getSection(id);

        if (sectionRepository.getSection(newName)
            .map(SectionModel::getId)
            .map(sectionId -> sectionId == id)
            .orElse(true)) {
            throw new SectionAlreadyExistsException();
        }

        section.setName(newName);

        return sectionRepository.save(section);
    }

    @Transactional
    public SectionModel createSection(SectionModel model) throws DocsException {
        var name = model.getName();
        if (sectionRepository.exists(name))
            throw new SectionAlreadyExistsException();

        if (!filesService.isExists(name))
            filesService.createDirectory(name);

        return sectionRepository.save(model);
    }

    @Transactional
    public void deleteSection(long id) throws SectionNotExistsException {
        if (!sectionRepository.exists(id))
            throw new SectionNotExistsException();

        var section = getSection(id);
        for (var document : section.getDocuments()) {
            var documentId = document.getId();
            try {
                documentService.delete(documentId);
            } catch (DocsException e) {
                LOG.warn("Can not delete document");
            }
        }

        sectionRepository.deleteSection(id);
    }

    public boolean isExists(long id) {
        return sectionRepository.exists(id);
    }

}
