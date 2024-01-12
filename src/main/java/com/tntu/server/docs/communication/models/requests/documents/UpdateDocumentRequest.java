package com.tntu.server.docs.communication.models.requests.documents;

import static com.tntu.server.docs.communication.models.Validation.MAX_LENGTH;
import static com.tntu.server.docs.communication.models.Validation.NAME_PATTERN;

import com.tntu.server.docs.core.data.enums.DocumentStatus;
import java.time.OffsetDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class UpdateDocumentRequest {

    @NotNull
    private Long sectionId;

    @Length(max = MAX_LENGTH)
    @Pattern(regexp = NAME_PATTERN, message = "Invalid document name")
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
