package com.tntu.server.docs.communication.controllers;


import com.tntu.server.docs.communication.models.auth.AuthorityRole;
import com.tntu.server.docs.communication.models.responses.ResponseEntityFactory;
import com.tntu.server.docs.core.services.DocumentsService;
import io.swagger.annotations.ApiOperation;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/docs")
@RequiredArgsConstructor
public class DocumentsController {

    private final DocumentsService documentsService;


    @ApiOperation("Save private file.")
    @PostMapping(value = "/private/save")
    @Secured({AuthorityRole.ADMIN, AuthorityRole.MANAGER})
    public ResponseEntity<?> saveNewPrivateFile(
            @RequestParam(required = false) String resource,
            @RequestPart MultipartFile request) throws Exception {
        documentsService.saveFile(resource, true, request);
        return ResponseEntityFactory.createOk();
    }

    @ApiOperation("Save public file.")
    @PostMapping(value = "/public/save")
    @Secured({AuthorityRole.ADMIN, AuthorityRole.MANAGER})
    public ResponseEntity<?> saveNewPublicFile(
            @RequestParam(required = false) String resource,
            @RequestPart MultipartFile request) throws Exception {
        documentsService.saveFile(resource, false, request);
        return ResponseEntityFactory.createOk();
    }


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


    @ApiOperation("Get private files.")
    @GetMapping(value = "/private")
    @Secured({AuthorityRole.ADMIN, AuthorityRole.MANAGER})
    public ResponseEntity<?> getPrivateFile(@RequestParam @NotBlank String location) throws Exception {
        if (location.startsWith("/"))
            location = "/" + location;
        var file = documentsService.getFile(location, true);
        return ResponseEntityFactory.createFile(file);
    }

    @ApiOperation("Get public file.")
    @GetMapping(value = "/public")
    public ResponseEntity<?> getPublicFile(@RequestParam @NotBlank String location) throws Exception {
        if (!location.startsWith("/"))
            location = "/" + location;
        var file = documentsService.getFile(location, false);
        return ResponseEntityFactory.createFile(file);
    }

}
