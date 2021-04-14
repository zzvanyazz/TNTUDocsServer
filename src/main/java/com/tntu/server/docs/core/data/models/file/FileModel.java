package com.tntu.server.docs.core.data.models.file;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public class FileModel implements Comparable<FileModel> {

    private String name;
    private OffsetDateTime creationDate;

    public FileModel(String name, OffsetDateTime creationDate) {
        this.name = name;
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }


    @Override
    public int compareTo(@NotNull FileModel fileModel) {
        return name == null ? Integer.MIN_VALUE :
                (fileModel.name == null ?
                        Integer.MAX_VALUE :
                        name.compareTo(fileModel.name)) ;
    }

}
