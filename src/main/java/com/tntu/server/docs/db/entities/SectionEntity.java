package com.tntu.server.docs.db.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sections")
public class SectionEntity {

    @Id
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany
    private List<DocumentEntity> documents;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DocumentEntity> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentEntity> documents) {
        this.documents = documents;
    }
}
