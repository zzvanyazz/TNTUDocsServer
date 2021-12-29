package com.tntu.server.docs.communication.controllers.documents;

import com.tntu.server.docs.communication.models.responses.ResponseEntityFactory;
import com.tntu.server.docs.communication.services.auth.CurrentUserService;
import com.tntu.server.docs.core.data.exceptions.DocsException;
import com.tntu.server.docs.core.data.exceptions.docs.DocumentNotAvailableException;
import com.tntu.server.docs.core.data.models.docs.DocumentModel;
import com.tntu.server.docs.core.services.DocumentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/docs")
public class DocumentFinderController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private CurrentUserService currentUserService;

    @ApiOperation("Get document info.")
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<?> getDocument(@PathVariable long id) throws DocsException {
        var file = documentService.getDocument(id);

        var status = file.getStatus();
        if (currentUserService.isNotGranted() && !status.isVisible()) {
            throw new DocumentNotAvailableException();
        }

        return ResponseEntityFactory.createOk(file);
    }

    @ApiOperation("Load document file.")
    @GetMapping(value = "/load/{id}")
    public ResponseEntity<?> loadDocumentFile(@PathVariable long id) throws DocsException {
        var document = documentService.getDocument(id);

        var status = document.getStatus();
        if (currentUserService.isNotGranted() && !status.isVisible()) {
            throw new DocumentNotAvailableException();
        }
        var file = documentService.loadFile(document.getId());

        return ResponseEntityFactory.createFile(file);
    }

    @ApiOperation("Find public files.")
    @GetMapping(value = "/find/{name}")
    public ResponseEntity<?> findPublicFiles(@PathVariable String name) {
        var documents = documentService.findDocuments(name);
        if (currentUserService.isNotGranted())
            documents.removeIf(DocumentModel::isNotVisible);

        return ResponseEntityFactory.createOk(documents);
    }
}
