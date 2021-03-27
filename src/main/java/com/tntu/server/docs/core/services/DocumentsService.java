package com.tntu.server.docs.core.services;

import com.tntu.server.docs.core.models.data.ExtendedFileModel;
import com.tntu.server.docs.core.models.data.FolderModel;
import com.tntu.server.docs.core.models.exceptions.file.*;
import com.tntu.server.docs.core.options.StorageOptions;
import com.tntu.server.docs.core.services.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<ExtendedFileModel> findPublicFiles(String filePart)
            throws InvalidResourceException, ResourceNotExistsException {
        return findFile(filePart, false);
    }


    public List<ExtendedFileModel> findAnyFiles(String filePart)
            throws InvalidResourceException, ResourceNotExistsException {
        var result = new ArrayList<ExtendedFileModel>();

        var publicFiles = findFile(filePart, false);
        var privateFiles = findFile(filePart, true);

        result.addAll(publicFiles);
        result.addAll(privateFiles);
        result.sort(ExtendedFileModel::compareTo);

        return result;
    }


    public FolderModel getPublicResourceTree(String resource)
            throws ResourceNotExistsException, InvalidResourceException {
        return getFolderModel(resource, false);
    }


    public FolderModel getPrivateResourceTree(String resource)
            throws ResourceNotExistsException, InvalidResourceException {
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


    private FolderModel getFolderModel(String resource, boolean isPrivate)
            throws ResourceNotExistsException, InvalidResourceException {
        resource = combineResource(resource, isPrivate);
        return storageService.getElementsTree(resource);
    }


    private String combineResource(String resource, boolean isPrivate) {
        return (isPrivate ? storageOptions.getPrivateLocation() : storageOptions.getPublicLocation())
                + (resource != null ? resource : "");
    }

    private List<ExtendedFileModel> findFile(String filePart, boolean amongPrivate)
            throws InvalidResourceException, ResourceNotExistsException {
        var resourceTree = getFolderModel(null, amongPrivate);
        return findFile(resourceTree, filePart, "", amongPrivate);
    }

    private List<ExtendedFileModel> findFile(FolderModel folderModel,
                                             String filePart,
                                             String location,
                                             boolean isPrivate) {
        var result = new ArrayList<ExtendedFileModel>();
        for (var fileModel : folderModel.getFiles()) {
            var fileName = fileModel.getName();
            if (isValid(fileName, filePart)) {
                var extendedFileModel = new ExtendedFileModel(fileModel, location, isPrivate);
                result.add(extendedFileModel);
            }
        }
        result.addAll(folderModel
                .getFolders()
                .stream()
                .flatMap(childFolder -> findFile(
                        childFolder,
                        filePart,
                        toLocation(location, childFolder),
                        isPrivate).stream())
                .collect(Collectors.toList()));
        return result;
    }

    public boolean isValid(String fileName, String filePart) {
        return fileName != null && fileName.contains(filePart);
    }

    public String toLocation(String location, FolderModel folderModel) {
        return location + "/" + folderModel.getName();
    }

}
