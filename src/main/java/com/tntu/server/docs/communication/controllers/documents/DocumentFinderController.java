package com.tntu.server.docs.communication.controllers.documents;

import com.tntu.server.docs.communication.models.responses.ResponseEntityFactory;
import com.tntu.server.docs.communication.services.auth.CurrentUserService;
import com.tntu.server.docs.core.data.exceptions.docs.DocumentNotAvailableException;
import com.tntu.server.docs.core.data.models.docs.DocumentModel;
import com.tntu.server.docs.core.services.DocumentService;
import com.tntu.server.docs.core.services.SectionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/docs")
public class DocumentFinderController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private CurrentUserService currentUserService;

    @ApiOperation("Get file.")
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<?> getPublicFilesTree(@PathVariable long id) throws Exception {
        var file = documentService.getDocument(id);

        var status = file.getStatus();
        if (!currentUserService.isGranted() && status.isVisible()) {
            throw new DocumentNotAvailableException();
        }

        return ResponseEntityFactory.createOk(file);
    }

    @ApiOperation("Find public files.")
    @GetMapping(value = "/find/{name}")
    public ResponseEntity<?> findPublicFiles(@PathVariable String name) {
        var documents = documentService.findDocuments(name);
        if (!currentUserService.isGranted())
            documents.removeIf(DocumentModel::isNotVisible);

        return ResponseEntityFactory.createOk(documents);
    }
}
