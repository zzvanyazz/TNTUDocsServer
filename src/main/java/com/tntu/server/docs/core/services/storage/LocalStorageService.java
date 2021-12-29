package com.tntu.server.docs.core.services.storage;

import com.tntu.server.docs.core.data.exceptions.storage.file.*;
import com.tntu.server.docs.core.data.exceptions.storage.resource.CanNotCreateDirectoryException;
import com.tntu.server.docs.core.data.exceptions.storage.resource.CanNotDeleteDirectoryException;
import com.tntu.server.docs.core.data.exceptions.storage.resource.InvalidResourceException;
import com.tntu.server.docs.core.data.models.file.BytesMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class LocalStorageService implements StorageService {

    private static final Logger LOG = Logger.getLogger(LocalStorageService.class.getName());

    @Override
    public void saveFile(String resource, MultipartFile multipartFile)
            throws CanNotWriteFileException, FileAlreadyExistsException, InvalidResourceException {
        var fileName = multipartFile.getOriginalFilename();
        saveFile(resource, fileName, multipartFile);
    }

    @Override
    public void saveFile(String resource, String name, MultipartFile multipartFile)
            throws InvalidResourceException, FileAlreadyExistsException, CanNotWriteFileException {
        var fileLocation = parseToPath(resource, name);
        if (isExists(fileLocation.toString())) {
            throw new FileAlreadyExistsException();
        }
        try {
            var path = Files.createFile(fileLocation);
            var fileOutputStream = new FileOutputStream(path.toFile());
            fileOutputStream.write(multipartFile.getBytes());
            fileOutputStream.close();
            LOG.log(Level.INFO, String.format("File saved. Location - %s", fileLocation));
        } catch (IOException e) {
            LOG.log(Level.WARNING, e.getMessage(), e);
            throw new CanNotWriteFileException();
        }
    }

    @Override
    public byte[] loadFile(String resource)
            throws FileNotExistsException, CanNotReadFileException, InvalidResourceException {
        if (!isExists(resource))
            throw new FileNotExistsException();
        var path = parseToPath(resource);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new CanNotReadFileException();
        }
    }

    @Override
    public boolean isExists(String resource) throws InvalidResourceException {
        var path = parseToPath(resource);
        return Files.exists(path);
    }

    @Override
    public void deleteFile(String resource)
            throws DeleteFileException, InvalidResourceException {
        try {
            var path = parseToPath(resource);
            if (!Files.isRegularFile(path))
                throw new DeleteFileException();

            Files.delete(path);
            LOG.log(Level.INFO, String.format("File Deleted. Location - %s", resource));
        } catch (IOException e) {
            LOG.log(Level.WARNING, e.getMessage(), e);
            throw new DeleteFileException();
        }
    }

    @Override
    public void createDirectory(String location)
            throws CanNotCreateDirectoryException, InvalidResourceException {
        if (!isExists(location)) {
            try {
                Files.createDirectory(parseToPath(location));
                LOG.log(Level.INFO, String.format("Directory created. Location - %s", location));
            } catch (IOException e) {
                LOG.log(Level.WARNING, e.getMessage(), e);
                throw new CanNotCreateDirectoryException();
            }
        }
    }

    @Override
    public void deleteDirectory(String location) throws CanNotDeleteDirectoryException, InvalidResourceException {
        try {
            var path = parseToPath(location);
            if (!Files.isDirectory(path))
                throw new CanNotDeleteDirectoryException();

            var isDeleted = Files.deleteIfExists(path);
            if (!isDeleted)
                throw new CanNotDeleteDirectoryException();

            LOG.log(Level.INFO, String.format("Directory deleted. Location - %s", location));
        } catch (IOException e) {
            LOG.log(Level.WARNING, e.getMessage(), e);
            throw new CanNotDeleteDirectoryException();
        }
    }

    @Override
    public String combine(String root, String... paths) throws InvalidResourceException {
        return parseToPath(root, paths).toString();
    }

    @Override
    public void moveObject(String location, String locationTo)
            throws CanNotMoveException, InvalidResourceException {
        var path = parseToPath(location);
        var pathTo = parseToPath(locationTo);
        try {
            Files.move(path, pathTo, StandardCopyOption.ATOMIC_MOVE);
            LOG.log(Level.INFO, String.format("Object moved. Location - %s. Location to - %s", location, locationTo));
        } catch (IOException e) {
            LOG.log(Level.WARNING, e.getMessage(), e);
            throw new CanNotMoveException();
        }
    }

    private Path parseToPath(String resource, String... more) throws InvalidResourceException {
        try {
            var startsWithSlash = resource.startsWith("/") || resource.startsWith("\\") || resource.isEmpty();
            resource = (startsWithSlash ? "" : "/") + resource;
            return Path.of(resource, more);
        } catch (Exception e) {
            LOG.log(Level.WARNING, e.getMessage(), e);
            throw new InvalidResourceException();
        }
    }

}
