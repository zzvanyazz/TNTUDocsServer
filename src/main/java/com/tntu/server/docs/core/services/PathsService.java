package com.tntu.server.docs.core.services;

import com.tntu.server.docs.core.models.exceptions.file.CanNotCreateDirectoryException;
import com.tntu.server.docs.core.models.exceptions.file.CanNotDeleteDirectoryException;
import com.tntu.server.docs.core.models.exceptions.file.CanNotMoveException;
import com.tntu.server.docs.core.models.exceptions.file.InvalidResourceException;
import com.tntu.server.docs.core.services.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PathsService {

    @Autowired
    public StorageService storageService;

    public void createPath(String location, String pathName, boolean isPrivate)
            throws InvalidResourceException, CanNotCreateDirectoryException {
        var resource = storageService.combine(location, pathName);
        resource = storageService.combineResource(resource, isPrivate);
        storageService.createDirectory(resource);
    }


    public void deletePath(String location, boolean isPrivate)
            throws InvalidResourceException, CanNotDeleteDirectoryException {
        var resource = storageService.combineResource(location, isPrivate);
        storageService.deleteDirectory(resource);
    }

    public void movePath(String path, String pathTo)
            throws InvalidResourceException, CanNotMoveException {

        var isExistsPath = storageService.isExists(path);
        var isExistsPathTo = storageService.isExists(pathTo);
        if (!isExistsPath || !isExistsPathTo)
            throw new InvalidResourceException();

        storageService.moveObject(path, pathTo);
    }


}
