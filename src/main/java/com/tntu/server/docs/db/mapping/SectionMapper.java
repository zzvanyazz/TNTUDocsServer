package com.tntu.server.docs.db.mapping;

import com.tntu.server.docs.core.data.models.docs.SectionModel;
import com.tntu.server.docs.db.entities.DocumentEntity;
import com.tntu.server.docs.db.entities.SectionEntity;

import java.util.List;

public class SectionMapper {

    public static SectionModel toModel(SectionEntity entity) {
        SectionModel sectionModel = new SectionModel();
        sectionModel.setId(entity.getId());
        sectionModel.setName(entity.getName());
        return sectionModel;
    }

    public static SectionModel toModel(SectionEntity entity, List<DocumentEntity> documentEntities) {
        SectionModel sectionModel = new SectionModel();
        var documents = DocumentMapper.toModel(documentEntities);
        sectionModel.setDocuments(documents);
        sectionModel.setId(entity.getId());
        sectionModel.setName(entity.getName());
        return sectionModel;
    }

    public static SectionEntity toEntity(SectionModel model) {
        SectionEntity sectionEntity = new SectionEntity();
        sectionEntity.setId(model.getId());
        sectionEntity.setName(model.getName());
        return sectionEntity;
    }

}
