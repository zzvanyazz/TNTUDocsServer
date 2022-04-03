package com.tntu.server.docs.communication.models.requests.documents;

import com.tntu.server.docs.core.data.enums.DocumentStatus;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.OffsetDateTime;

import static com.tntu.server.docs.communication.models.Validation.MAX_LENGTH;
import static com.tntu.server.docs.communication.models.Validation.NAME_PATTERN;

public class CreateDocumentRequest {

    @NotNull
    private Long sectionId;

    @NotBlank
    @Length(max = MAX_LENGTH)
    @Pattern(regexp = NAME_PATTERN, message = "Invalid document name")
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
