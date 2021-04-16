package com.tntu.server.docs.communication.models.mappings;

import com.tntu.server.docs.communication.models.requests.sections.CreateSectionRequest;
import com.tntu.server.docs.communication.models.requests.sections.UpdateSectionRequest;
import com.tntu.server.docs.core.data.models.docs.SectionModel;

public class SectionMapper {

    public static SectionModel toModel(CreateSectionRequest request) {
        SectionModel sectionModel = new SectionModel();
        sectionModel.setName(request.getName());
        return sectionModel;
    }

    public static SectionModel toModel(UpdateSectionRequest request) {
        SectionModel sectionModel = new SectionModel();
        sectionModel.setName(request.getName());
        return sectionModel;
    }

}
