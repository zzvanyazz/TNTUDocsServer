package com.tntu.server.docs.communication.models.requests.documents;

import com.tntu.server.docs.core.data.enums.DocumentStatus;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;

public class UpdateDocumentRequest {

    private Long sectionId;

    private String name;

    private OffsetDateTime createTime;

    private DocumentStatus status;

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
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

}
