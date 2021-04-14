package com.tntu.server.docs.core.repositories;

import com.tntu.server.docs.core.data.models.docs.DocumentModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository {

    boolean exists(long id);

    boolean exists(String name);

    Optional<DocumentModel> getDocument(long id);

    DocumentModel save(DocumentModel model);

    void delete(long id);

    DocumentModel update(DocumentModel model);

    List<DocumentModel> getAllBySection(long section);

}
