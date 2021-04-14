package com.tntu.server.docs.communication.controllers.documents;

import com.tntu.server.docs.communication.models.auth.AuthorityRole;
import com.tntu.server.docs.communication.models.requests.path.MoveObjectRequest;
import com.tntu.server.docs.communication.models.responses.ResponseEntityFactory;
import com.tntu.server.docs.core.services.FilesService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/v1/docs")
public class DocumentManagementController {

    @Autowired
    private FilesService filesService;


    @ApiOperation("Save private file.")
    @PostMapping(value = "/private/save")
    @Secured({AuthorityRole.ADMIN, AuthorityRole.MANAGER})
    public ResponseEntity<?> saveNewPrivateFile(
            @RequestParam(required = false) String resource,
            @RequestPart MultipartFile request) throws Exception {
        filesService.saveFile(resource, true, request);
        return ResponseEntityFactory.createOk();
    }

    @ApiOperation("Save public file.")
    @PostMapping(value = "/public/save")
    @Secured({AuthorityRole.ADMIN, AuthorityRole.MANAGER})
    public ResponseEntity<?> saveNewPublicFile(
            @RequestParam(required = false) String resource,
            @RequestPart MultipartFile request) throws Exception {
        filesService.saveFile(resource, false, request);

        return ResponseEntityFactory.createOk();
    }

    @ApiOperation("Get private files.")
    @GetMapping(value = "/private")
    @Secured({AuthorityRole.ADMIN, AuthorityRole.MANAGER})
    public ResponseEntity<?> getPrivateFile(@RequestParam @NotBlank String location) throws Exception {
        var file = filesService.getFile(location, true);

        return ResponseEntityFactory.createFile(file);
    }

    @ApiOperation("Get public file.")
    @GetMapping(value = "/public")
    public ResponseEntity<?> getPublicFile(@RequestParam @NotBlank String location) throws Exception {
        var file = filesService.getFile(location, false);

        return ResponseEntityFactory.createFile(file);
    }

    @ApiOperation("Move file")
    @PostMapping(value = "/public")
    public ResponseEntity<?> getPublicFile(@RequestBody @NotBlank MoveObjectRequest request) throws Exception {
        String location = request.getLocation();
        String locationTo = request.getLocationTo();
        filesService.moveFile(location, locationTo);

        return ResponseEntityFactory.createOk();
    }

    @ApiOperation("Delete public file")
    @DeleteMapping(value = "/public/delete")
    public ResponseEntity<?> deletePublicFile(@RequestParam @NotBlank String location) throws Exception {
        filesService.deleteFile(location);

        return ResponseEntityFactory.createOk();
    }

}
