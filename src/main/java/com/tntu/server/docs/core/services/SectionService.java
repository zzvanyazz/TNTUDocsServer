package com.tntu.server.docs.core.services;

import com.tntu.server.docs.core.data.exceptions.section.SectionAlreadyExistsException;
import com.tntu.server.docs.core.data.exceptions.section.SectionNotExistsException;
import com.tntu.server.docs.core.data.models.docs.SectionModel;
import com.tntu.server.docs.core.repositories.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;

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

    public SectionModel createSection(String name) throws SectionAlreadyExistsException {
        if (sectionRepository.exists(name))
            throw new SectionAlreadyExistsException();

        var section = new SectionModel(name);

        return sectionRepository.save(section);
    }

    public void deleteSection(long id) throws SectionNotExistsException {
        if (!sectionRepository.exists(id))
            throw new SectionNotExistsException();

        sectionRepository.deleteSection(id);
    }

    public boolean isExists(long id) {
        return sectionRepository.exists(id);
    }

}
