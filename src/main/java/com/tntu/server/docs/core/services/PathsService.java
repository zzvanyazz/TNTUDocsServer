package com.tntu.server.docs.core.services;

import com.tntu.server.docs.core.data.exceptions.file.CanNotCreateDirectoryException;
import com.tntu.server.docs.core.data.exceptions.file.CanNotDeleteDirectoryException;
import com.tntu.server.docs.core.data.exceptions.file.CanNotMoveException;
import com.tntu.server.docs.core.data.exceptions.file.InvalidResourceException;
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
        storageService.createDirectory(resource);
    }


    public void deletePath(String location)
            throws InvalidResourceException, CanNotDeleteDirectoryException {
        storageService.deleteDirectory(location);
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
