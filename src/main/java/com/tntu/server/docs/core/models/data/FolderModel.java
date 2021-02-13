package com.tntu.server.docs.core.models.data;

import java.util.List;

public class FolderModel {
    private String name;
    private List<FolderModel> folders;
    private List<String> fileNames;

    public FolderModel(String name, List<FolderModel> folders, List<String> fileNames) {
        this.name = name;
        this.folders = folders;
        this.fileNames = fileNames;
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

    public List<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }
}