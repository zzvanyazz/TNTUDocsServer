package com.tntu.server.docs.core.data.models.docs;

import com.tntu.server.docs.core.data.enums.DocumentStatus;

import java.time.OffsetDateTime;

public class DocumentModel {

    private long id;

    private long sectionId;

    private String name;

    private OffsetDateTime createTime;

    private DocumentStatus status;

    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSectionId() {
        return sectionId;
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OffsetDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(OffsetDateTime createTime) {
        this.createTime = createTime;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    public boolean isNotVisible() {
        if (status != null)
            return !status.isVisible();
        return false;
    }

}
