package com.tntu.server.docs.db.entities;

import com.tntu.server.docs.core.data.enums.DocumentStatus;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "documents")
public class DocumentEntity {

    @Id
    private long id;

    @Column(name = "section_id")
    private long sectionId;

    @Column(name = "name")
    private String name;

    @Column(name = "create_time")
    private OffsetDateTime createTime;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private DocumentStatus status;

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
}
