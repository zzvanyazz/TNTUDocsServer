package com.tntu.server.docs.core.services.storage;

import com.tntu.server.docs.core.models.data.FolderModel;
import com.tntu.server.docs.core.models.exceptions.file.InvalidResourceException;
import com.tntu.server.docs.core.models.exceptions.file.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface StorageService {

    void saveFile(String resource, MultipartFile multipartFile) throws CanNotWriteFileException, FileAlreadyExistsException, InvalidResourceException;

    MultipartFile loadFile(String resource) throws FileNotExistsException, CanNotReadFileException, InvalidResourceException;

    boolean isExists(String resource) throws InvalidResourceException;

    void deleteFile(String resource) throws DeleteFileException, InvalidResourceException;

    FolderModel getElementsTree(String resource) throws ResourceNotExistsException, InvalidResourceException;

    void createDirectory(String location) throws CanNotCreateDirectoryException, InvalidResourceException;

}
