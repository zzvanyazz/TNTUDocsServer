package com.tntu.server.docs.core.services;

import com.tntu.server.docs.core.data.exceptions.DocsException;
import com.tntu.server.docs.core.data.exceptions.section.SectionAlreadyExistsException;
import com.tntu.server.docs.core.data.exceptions.section.SectionNotExistsException;
import com.tntu.server.docs.core.data.models.docs.SectionModel;
import com.tntu.server.docs.core.repositories.SectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SectionService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private FilesService filesService;

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
