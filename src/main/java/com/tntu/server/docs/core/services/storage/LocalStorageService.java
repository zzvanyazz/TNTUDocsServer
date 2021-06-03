package com.tntu.server.docs.core.services.storage;

import com.google.common.collect.Lists;
import com.tntu.server.docs.core.data.exceptions.storage.file.*;
import com.tntu.server.docs.core.data.exceptions.storage.resource.CanNotCreateDirectoryException;
import com.tntu.server.docs.core.data.exceptions.storage.resource.CanNotDeleteDirectoryException;
import com.tntu.server.docs.core.data.exceptions.storage.resource.InvalidResourceException;
import com.tntu.server.docs.core.data.exceptions.storage.resource.ResourceNotExistsException;
import com.tntu.server.docs.core.data.models.file.BytesMultipartFile;
import com.tntu.server.docs.core.data.models.file.FileModel;
import com.tntu.server.docs.core.data.models.file.FolderModel;
import com.tntu.server.docs.core.options.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
            FileOutputStream fileOutputStream = new FileOutputStream(path.toFile());
            fileOutputStream.write(multipartFile.getBytes());
            fileOutputStream.close();
            LOG.log(Level.INFO, String.format("File saved. Location - %s", fileLocation));
        } catch (IOException e) {
            LOG.log(Level.WARNING, e.getMessage(), e);
            throw new CanNotWriteFileException();
        }
    }

    @Override
    public MultipartFile loadFile(String resource)
            throws FileNotExistsException, CanNotReadFileException, InvalidResourceException {
        if (!isExists(resource))
            throw new FileNotExistsException();
        var path = parseToPath(resource);
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new CanNotReadFileException();
        }
        var fileName = path.getFileName().toString();
        LOG.log(Level.INFO, String.format("File Loaded. Location - %s", resource));
        return new BytesMultipartFile(fileName, bytes);
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
    public FolderModel getElementsTree(String resource)
            throws ResourceNotExistsException, InvalidResourceException {
        var path = parseToPath(resource);
        if (!isExists(resource) || Files.isRegularFile(path))
            throw new ResourceNotExistsException();
        var folder = createFolderModel(path);
        if (folder == null)
            throw new ResourceNotExistsException();
        return folder;
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

    private FolderModel createFolderModel(Path rootPath) {
        var folders = new ArrayList<FolderModel>();
        var files = new ArrayList<FileModel>();
        if (Files.isRegularFile(rootPath))
            return null;
        List<Path> paths = getPaths(rootPath);
        for (Path path : paths) {
            if (Files.isRegularFile(path)) {
                files.add(createFileModel(path));
            } else if (Files.isDirectory(path)) {
                folders.add(createFolderModel(path));
            }
        }
        var namesCount = rootPath.getNameCount();
        var name = namesCount != 0 ? rootPath.getName(namesCount - 1) : rootPath;
        folders.sort(FolderModel::compareTo);
        files.sort(FileModel::compareTo);
        return new FolderModel(name.toString(), folders, files);
    }

    private List<Path> getPaths(Path rootPath) {
        try {
            return Files.list(rootPath).collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    private FileModel createFileModel(Path path) {
        var fileName = path.getFileName().toString();
        try {
            var attributes = Files.readAttributes(path, BasicFileAttributes.class);
            var timeInstant = attributes.creationTime().toInstant();
            var createDateTime = OffsetDateTime.ofInstant(timeInstant, ZoneId.systemDefault());
            return new FileModel(fileName, createDateTime);
        } catch (IOException e) {
            LOG.log(Level.WARNING, e.getMessage(), e);
        }
        return new FileModel(fileName, null);
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
