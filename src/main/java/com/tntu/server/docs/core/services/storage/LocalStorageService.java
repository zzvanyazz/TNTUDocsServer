package com.tntu.server.docs.core.services.storage;

import com.tntu.server.docs.core.models.data.BytesMultipartFile;
import com.tntu.server.docs.core.models.data.FolderModel;
import com.tntu.server.docs.core.models.exceptions.file.InvalidResourceException;
import com.tntu.server.docs.core.models.exceptions.file.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalStorageService implements StorageService {
    @Override
    public void saveFile(String resource, MultipartFile multipartFile)
            throws CanNotWriteFileException, FileAlreadyExistsException, InvalidResourceException {
        var fileLocation = createPath(resource, multipartFile.getOriginalFilename());
        if (isExists(fileLocation.toString())) {
            throw new FileAlreadyExistsException();
        }
        try {
            var path = Files.createFile(fileLocation);
            FileOutputStream fileOutputStream = new FileOutputStream(path.toFile());
            fileOutputStream.write(multipartFile.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            throw new CanNotWriteFileException();
        }

    }

    @Override
    public MultipartFile loadFile(String resource)
            throws FileNotExistsException, CanNotReadFileException, InvalidResourceException {
        if (!isExists(resource))
            throw new FileNotExistsException();
        var path = createPath(resource);
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new CanNotReadFileException();
        }
        var fileName = path.getFileName().toString();
        return new BytesMultipartFile(fileName, bytes);

    }

    @Override
    public boolean isExists(String resource) throws InvalidResourceException {
        return Files.exists(createPath(resource));
    }

    @Override
    public void deleteFile(String resource)
            throws DeleteFileException, InvalidResourceException {
        try {
            Files.delete(createPath(resource));
        } catch (IOException e) {
            throw new DeleteFileException();
        }
    }

    @Override
    public FolderModel getElementsTree(String resource)
            throws ResourceNotExistsException, InvalidResourceException {
        var path = createPath(resource);
        if (!isExists(resource) || Files.isRegularFile(path))
            throw new ResourceNotExistsException();
        var folder = getFolder(path);
        if (folder == null)
            throw new ResourceNotExistsException();
        return folder;
    }

    @Override
    public void createDirectory(String location)
            throws CanNotCreateDirectoryException, InvalidResourceException {
        if (!isExists(location)) {
            try {
                Files.createDirectory(createPath(location));
            } catch (IOException e) {
                throw new CanNotCreateDirectoryException();
            }
        }
    }

    private FolderModel getFolder(Path rootPath) {
        var folders = new ArrayList<FolderModel>();
        var files = new ArrayList<String>();
        if (Files.isRegularFile(rootPath))
            return null;
        List<Path> paths;
        try {
            paths = Files.list(rootPath).collect(Collectors.toList());
        } catch (IOException e) {
            return null;
        }
        for (Path path : paths) {
            if (Files.isRegularFile(path)) {
                files.add(path.getFileName().toString());
            } else if (Files.isDirectory(path)) {
                folders.add(getFolder(path));
            }
        }
        var namesCount = rootPath.getNameCount();
        var name = namesCount != 0 ? rootPath.getName(namesCount - 1) : rootPath;
        return new FolderModel(name.toString(), folders, files);
    }

    private Path createPath(String first, String... more) throws InvalidResourceException {
        try {
            return Path.of(first, more);
        } catch (Exception e) {
            throw new InvalidResourceException();
        }
    }

}
