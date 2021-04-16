package com.tntu.server.docs.communication.models.mappings;

import com.tntu.server.docs.communication.models.requests.documents.CreateDocumentRequest;
import com.tntu.server.docs.communication.models.requests.documents.UpdateDocumentRequest;
import com.tntu.server.docs.core.data.models.docs.DocumentModel;

public class DocumentsMapper {

    public static DocumentModel toModel(CreateDocumentRequest request) {
        DocumentModel documentModel = new DocumentModel();
        documentModel.setSectionId(request.getSectionId());
        documentModel.setName(request.getName());
        documentModel.setCreateTime(request.getCreateTime());
        documentModel.setStatus(request.getStatus());
        return documentModel;
    }

    public static DocumentModel toModel(UpdateDocumentRequest request) {
        DocumentModel documentModel = new DocumentModel();
        documentModel.setSectionId(request.getSectionId());
        documentModel.setName(request.getName());
        documentModel.setCreateTime(request.getCreateTime());
        documentModel.setStatus(request.getStatus());
        return documentModel;
    }

}
