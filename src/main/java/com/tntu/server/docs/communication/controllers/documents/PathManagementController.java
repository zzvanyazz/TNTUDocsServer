package com.tntu.server.docs.communication.controllers.documents;

import com.tntu.server.docs.communication.models.requests.path.CreatePathRequest;
import com.tntu.server.docs.communication.models.requests.path.MoveObjectRequest;
import com.tntu.server.docs.communication.models.responses.ResponseEntityFactory;
import com.tntu.server.docs.core.data.exceptions.file.CanNotCreateDirectoryException;
import com.tntu.server.docs.core.data.exceptions.file.CanNotDeleteDirectoryException;
import com.tntu.server.docs.core.data.exceptions.file.CanNotMoveException;
import com.tntu.server.docs.core.data.exceptions.file.InvalidResourceException;
import com.tntu.server.docs.core.services.PathsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/v1/docs")
public class PathManagementController {

    @Autowired
    private PathsService pathsService;

    @ApiOperation("Create new public Path")
    @PostMapping(value = "/create-public-path")
    public ResponseEntity<?> createPublicPath(@RequestBody @Validated CreatePathRequest request)
            throws CanNotCreateDirectoryException, InvalidResourceException {
        var location = request.getLocation();
        var name = request.getName();
        pathsService.createPath(location, name, false);

        return ResponseEntityFactory.createOk();
    }

    @ApiOperation("Delete public Path")
    @DeleteMapping(value = "/delete-public-path")
    public ResponseEntity<?> deletePublicPath(@RequestParam @NotBlank String location)
            throws InvalidResourceException, CanNotDeleteDirectoryException {
        pathsService.deletePath(location, false);

        return ResponseEntityFactory.createOk();
    }

    @ApiOperation("Create new private path")
    @PostMapping(value = "/create-private-path")
    public ResponseEntity<?> createPrivatePath(@RequestBody @Validated CreatePathRequest request)
            throws CanNotCreateDirectoryException, InvalidResourceException {
        var location = request.getLocation();
        var name = request.getName();
        pathsService.createPath(location, name, true);

        return ResponseEntityFactory.createOk();
    }

    @ApiOperation("Delete private Path")
    @DeleteMapping(value = "/delete-private-path")
    public ResponseEntity<?> deletePrivatePath(@RequestParam @NotBlank String location)
            throws InvalidResourceException, CanNotDeleteDirectoryException {
        pathsService.deletePath(location, true);

        return ResponseEntityFactory.createOk();
    }

    @ApiOperation("Move path")
    @PostMapping(value = "/move-path")
    public ResponseEntity<?> movePath(@RequestBody @Validated MoveObjectRequest request)
            throws CanNotMoveException, InvalidResourceException {
        String location = request.getLocation();
        String locationTo = request.getLocationTo();
        pathsService.movePath(location, locationTo);

        return ResponseEntityFactory.createOk();
    }

}
