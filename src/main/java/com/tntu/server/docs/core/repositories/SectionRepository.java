package com.tntu.server.docs.core.repositories;

import com.tntu.server.docs.core.data.models.docs.SectionModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository {

    boolean exists(long id);

    boolean exists(String name);

    Optional<SectionModel> getSection(long id);

    Optional<SectionModel> getSection(String name);

    List<SectionModel> getAllSections();

    SectionModel save(SectionModel sectionModel);

    void deleteSection(long id);

}
