package com.tntu.server.docs.core.services.storage;

import com.tntu.server.docs.core.data.exceptions.storage.file.*;
import com.tntu.server.docs.core.data.exceptions.storage.resource.CanNotCreateDirectoryException;
import com.tntu.server.docs.core.data.exceptions.storage.resource.CanNotDeleteDirectoryException;
import com.tntu.server.docs.core.data.exceptions.storage.resource.InvalidResourceException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface StorageService {

    void saveFile(String resource, MultipartFile multipartFile) throws CanNotWriteFileException, FileAlreadyExistsException, InvalidResourceException;

    void saveFile(String resource, String name, MultipartFile multipartFile) throws CanNotWriteFileException, FileAlreadyExistsException, InvalidResourceException;

    byte[] loadFile(String resource) throws FileNotExistsException, CanNotReadFileException, InvalidResourceException;

    boolean isExists(String resource) throws InvalidResourceException;

    void deleteFile(String resource) throws DeleteFileException, InvalidResourceException;

    void createDirectory(String location) throws CanNotCreateDirectoryException, InvalidResourceException;

    void deleteDirectory(String location) throws CanNotDeleteDirectoryException, InvalidResourceException;

    String combine(String root, String... paths) throws InvalidResourceException;

    void moveObject(String location, String locationTo) throws CanNotMoveException, InvalidResourceException;

}
