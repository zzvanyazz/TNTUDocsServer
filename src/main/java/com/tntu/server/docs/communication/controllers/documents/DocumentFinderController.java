package com.tntu.server.docs.communication.controllers.documents;

import com.tntu.server.docs.communication.models.mappings.DocumentsMapper;
import com.tntu.server.docs.communication.models.requests.documents.FindFileRequest;
import com.tntu.server.docs.communication.models.responses.ResponseEntityFactory;
import com.tntu.server.docs.core.models.data.ExtendedFileModel;
import com.tntu.server.docs.core.services.DocumentsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/v1/docs")
public class DocumentFinderController {

    @Autowired
    private DocumentsService documentsService;

    @ApiOperation("Get public files tree.")
    @GetMapping(value = "/public/tree")
    public ResponseEntity<?> getPublicFilesTree(@RequestParam(required = false) String location) throws Exception {
        var folder = documentsService.getPublicResourceTree(location);

        return ResponseEntityFactory.createOk(folder);
    }


    @ApiOperation("Get private files tree.")
    @GetMapping(value = "/private/tree")
    public ResponseEntity<?> getPrivateFilesTree(@RequestParam(required = false) String location) throws Exception {
        var folder = documentsService.getPrivateResourceTree(location);

        return ResponseEntityFactory.createOk(folder);
    }

    @ApiOperation("Find public files.")
    @GetMapping(value = "/find")
    public ResponseEntity<?> findPublicFiles(@RequestParam FindFileRequest findFileRequest) throws Exception {
        var findModel = DocumentsMapper.map(findFileRequest);
        List<ExtendedFileModel> files = documentsService.findPublicFiles(findModel);

        return ResponseEntityFactory.createOk(files);
    }
}
