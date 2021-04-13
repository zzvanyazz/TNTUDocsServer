package com.tntu.server.docs.communication.models.mappings;

import com.tntu.server.docs.communication.models.requests.documents.FindFileRequest;
import com.tntu.server.docs.core.models.data.FindFileModel;

public class DocumentsMapper {

    public static FindFileModel map(FindFileRequest request) {
        FindFileModel findFileModel = new FindFileModel();
        findFileModel.setFilePart(request.getFilePart());
        findFileModel.setLocation(request.getLocation());
        findFileModel.setPrivate(request.isPrivate());
        return findFileModel;
    }

}
