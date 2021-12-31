package com.tntu.server.docs.core.services;

import com.tntu.server.docs.core.data.exceptions.DocsException;
import com.tntu.server.docs.core.data.exceptions.storage.file.CanNotMoveException;
import com.tntu.server.docs.core.data.exceptions.storage.file.CanNotWriteFileException;
import com.tntu.server.docs.core.data.exceptions.storage.file.DeleteFileException;
import com.tntu.server.docs.core.data.exceptions.storage.file.FileAlreadyExistsException;
import com.tntu.server.docs.core.data.exceptions.storage.resource.CanNotCreateDirectoryException;
import com.tntu.server.docs.core.data.exceptions.storage.resource.InvalidResourceException;
import com.tntu.server.docs.core.data.exceptions.storage.resource.ResourceNotExistsException;
import com.tntu.server.docs.core.data.models.file.BytesMultipartFile;
import com.tntu.server.docs.core.services.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesService {

    @Autowired
    private StorageService storageService;

    public String combine(String root, String... paths) throws InvalidResourceException {
        return storageService.combine(root, paths);
    }

    public boolean isExists(String resource) throws InvalidResourceException {
        return storageService.isExists(resource);
    }

    public void createDirectory(String resource) throws InvalidResourceException, CanNotCreateDirectoryException {
        storageService.createDirectory(resource);
    }

    public void saveFile(String location, MultipartFile file)
            throws CanNotWriteFileException, FileAlreadyExistsException, InvalidResourceException {
        storageService.saveFile(location, file);
    }

    public MultipartFile getFile(String location, String name) throws DocsException {
        var fileBytes = storageService.loadFile(location);
        return new BytesMultipartFile(name, fileBytes);
    }

    public void moveFile(String fileLocation, String pathLocation)
            throws InvalidResourceException, CanNotMoveException {
        var isExistsPath = storageService.isExists(fileLocation);
        var isExistsPathTo = storageService.isExists(pathLocation);
        if (!isExistsPath || !isExistsPathTo)
            throw new InvalidResourceException();

        storageService.moveObject(fileLocation, pathLocation);
    }

    public void deleteFile(String location)
            throws InvalidResourceException, ResourceNotExistsException, DeleteFileException {
        if (!storageService.isExists(location))
            throw new ResourceNotExistsException();
        storageService.deleteFile(location);
    }

    public synchronized void saveOrRewrite(String location, String name, MultipartFile file)
            throws InvalidResourceException, CanNotWriteFileException, FileAlreadyExistsException, DeleteFileException {
        var resource = storageService.combine(location, name);
        if (storageService.isExists(resource))
            storageService.deleteFile(resource);
        storageService.saveFile(location, name, file);
    }


}
