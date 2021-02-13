package com.tntu.server.docs.core.services;

import com.tntu.server.docs.core.models.data.FolderModel;
import com.tntu.server.docs.core.models.exceptions.file.*;
import com.tntu.server.docs.core.options.StorageOptions;
import com.tntu.server.docs.core.services.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;

@Service
public class DocumentsService {

    @Autowired
    private StorageService storageService;

    @Autowired
    private StorageOptions storageOptions;

    @PostConstruct
    private void postConstruct() throws CanNotCreateDirectoryException, InvalidResourceException {
        var publicLocation = storageOptions.getPublicLocation();
        var privateLocation = storageOptions.getPrivateLocation();
        storageService.createDirectory(privateLocation);
        storageService.createDirectory(publicLocation);
    }


    public FolderModel getPublicResourceTree(String resource) throws ResourceNotExistsException, InvalidResourceException {
        return getFolderModel(resource, false);
    }


    public FolderModel getPrivateResourceTree(String resource) throws ResourceNotExistsException, InvalidResourceException {
        return getFolderModel(resource, true);
    }

    public void saveFile(String resource, boolean isPrivate, MultipartFile file)
            throws CanNotWriteFileException, FileAlreadyExistsException, InvalidResourceException {
        resource = combineResource(resource, isPrivate);
        storageService.saveFile(resource, file);
    }

    public MultipartFile getFile(String location, boolean isPrivate)
            throws CanNotReadFileException, FileNotExistsException, InvalidResourceException {
        return storageService.loadFile(combineResource(location, isPrivate));
    }


    private FolderModel getFolderModel(String resource, boolean isPrivate) throws ResourceNotExistsException, InvalidResourceException {
        resource = combineResource(resource, isPrivate);
        return storageService.getElementsTree(resource);
    }


    private String combineResource(String resource, boolean isPrivate) {
        return (isPrivate ? storageOptions.getPrivateLocation() : storageOptions.getPublicLocation())
                + (resource != null ? resource : "");
    }

}
