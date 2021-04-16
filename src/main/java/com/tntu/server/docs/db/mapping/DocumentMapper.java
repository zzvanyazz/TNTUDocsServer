package com.tntu.server.docs.db.mapping;

import com.tntu.server.docs.core.data.models.docs.DocumentModel;
import com.tntu.server.docs.db.entities.DocumentEntity;

import java.util.List;
import java.util.stream.Collectors;

public class DocumentMapper {

    public static DocumentModel toModel(DocumentEntity entity) {
        DocumentModel documentModel = new DocumentModel();
        documentModel.setId(entity.getId());
        documentModel.setSectionId(entity.getSectionId());
        documentModel.setName(entity.getName());
        documentModel.setCreateTime(entity.getCreateTime());
        documentModel.setStatus(entity.getStatus());
        documentModel.setFileName(entity.getFileName());
        return documentModel;
    }

    public static List<DocumentModel> toModel(List<DocumentEntity> entities) {
        if (entities == null)
            return null;
        return entities
                .stream()
                .map(DocumentMapper::toModel)
                .collect(Collectors.toList());
    }

    public static DocumentEntity toEntity(DocumentModel model) {
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setId(model.getId());
        documentEntity.setSectionId(model.getSectionId());
        documentEntity.setName(model.getName());
        documentEntity.setCreateTime(model.getCreateTime());
        documentEntity.setStatus(model.getStatus());
        documentEntity.setFileName(model.getFileName());
        return documentEntity;
    }

}
