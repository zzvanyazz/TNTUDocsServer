package com.tntu.server.docs.communication.models.requests.documents;

import com.tntu.server.docs.core.data.enums.DocumentStatus;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.OffsetDateTime;

public class CreateDocumentRequest {

    @NotNull
    private Long sectionId;

    @NotBlank
    @Length(max = 120)
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9 .()і]", message = "Invalid document name")
    private String name;

    @NotNull
    private OffsetDateTime createTime;

    @NotNull
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
