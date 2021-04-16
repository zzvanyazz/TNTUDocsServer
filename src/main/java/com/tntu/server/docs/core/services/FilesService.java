package com.tntu.server.docs.core.services;

import com.tntu.server.docs.core.data.exceptions.file.*;
import com.tntu.server.docs.core.data.models.file.ExtendedFileModel;
import com.tntu.server.docs.core.data.models.file.FolderModel;
import com.tntu.server.docs.core.services.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilesService {

    @Autowired
    private StorageService storageService;

    public void saveFile(String resource, MultipartFile file)
            throws CanNotWriteFileException, FileAlreadyExistsException, InvalidResourceException {
        storageService.saveFile(resource, file);
    }

    public MultipartFile getFile(String location)
            throws CanNotReadFileException, FileNotExistsException, InvalidResourceException {
        return storageService.loadFile(location);
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

    private FolderModel getFolderModel()
            throws ResourceNotExistsException, InvalidResourceException {
        return storageService.getElementsTree(null);
    }

    private List<ExtendedFileModel> findFile(String filePart)
            throws InvalidResourceException, ResourceNotExistsException {
        var resourceTree = getFolderModel();
        return findFile(resourceTree, filePart, "");
    }

    private List<ExtendedFileModel> findFile(FolderModel folderModel,
                                             String filePart,
                                             String location) {
        var result = new ArrayList<ExtendedFileModel>();
        for (var fileModel : folderModel.getFiles()) {
            var fileName = fileModel.getName();
            if (isValid(fileName, filePart)) {
                var extendedFileModel = new ExtendedFileModel(fileModel, location);
                result.add(extendedFileModel);
            }
        }
        result.addAll(folderModel
                .getFolders()
                .stream()
                .flatMap(childFolder -> findFile(
                        childFolder,
                        filePart,
                        toLocation(location, childFolder))
                        .stream())
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
