package com.tntu.server.docs.communication.controllers.documents;

import com.tntu.server.docs.communication.models.auth.AuthorityRole;
import com.tntu.server.docs.communication.models.mappings.DocumentsMapper;
import com.tntu.server.docs.communication.models.requests.documents.CreateDocumentRequest;
import com.tntu.server.docs.communication.models.requests.documents.UpdateDocumentRequest;
import com.tntu.server.docs.communication.models.responses.ResponseEntityFactory;
import com.tntu.server.docs.core.data.exceptions.DocsException;
import com.tntu.server.docs.core.services.DocumentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/docs")
public class DocumentManagementController {

    @Autowired
    private DocumentService documentService;

    @ApiOperation("Create document.")
    @PostMapping
    @Secured({AuthorityRole.ADMIN, AuthorityRole.MANAGER})
    public ResponseEntity<?> createDocument(@RequestBody CreateDocumentRequest request) throws DocsException {
        var model = DocumentsMapper.toModel(request);
        model = documentService.createDocument(model);

        return ResponseEntityFactory.createCreated(model);
    }

    @ApiOperation("Upload file")
    @PutMapping(value = "/{id}")
    @Secured({AuthorityRole.ADMIN, AuthorityRole.MANAGER})
    public ResponseEntity<?> uploadFile(
            @PathVariable long id,
            @RequestPart MultipartFile file) throws DocsException {
        documentService.uploadFile(id, file);

        return ResponseEntityFactory.createOk();
    }


    @ApiOperation("Update document.")
    @PatchMapping(value = "/{id}")
    @Secured({AuthorityRole.ADMIN, AuthorityRole.MANAGER})
    public ResponseEntity<?> getPublicFile(
            @PathVariable long id,
            @RequestBody UpdateDocumentRequest request) throws DocsException {
        var document = DocumentsMapper.toModel(request);
        documentService.updateDocument(id, document);

        return ResponseEntityFactory.createOk();
    }

    @ApiOperation("Delete document.")
    @DeleteMapping(value = "/{id}")
    @Secured({AuthorityRole.ADMIN, AuthorityRole.MANAGER})
    public ResponseEntity<?> deletePublicFile(@PathVariable long id) throws DocsException {
        documentService.delete(id);

        return ResponseEntityFactory.createOk();
    }

}
