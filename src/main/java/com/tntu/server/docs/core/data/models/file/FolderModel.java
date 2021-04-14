package com.tntu.server.docs.core.data.models.file;

import javax.validation.constraints.NotNull;
import java.util.List;

public class FolderModel implements Comparable<FolderModel> {
    private String name;
    private List<FolderModel> folders;
    private List<FileModel> files;

    public FolderModel(String name, List<FolderModel> folders, List<FileModel> files) {
        this.name = name;
        this.folders = folders;
        this.files = files;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FolderModel> getFolders() {
        return folders;
    }

    public void setFolders(List<FolderModel> folders) {
        this.folders = folders;
    }

    public List<FileModel> getFiles() {
        return files;
    }

    public void setFiles(List<FileModel> files) {
        this.files = files;
    }

    @Override
    public int compareTo(@NotNull FolderModel folderModel) {
        return name == null ? Integer.MIN_VALUE :
                (folderModel.name == null ?
                        Integer.MAX_VALUE :
                        name.compareTo(folderModel.name)) ;
    }
}