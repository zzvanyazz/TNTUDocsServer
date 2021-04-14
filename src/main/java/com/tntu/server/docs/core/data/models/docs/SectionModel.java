package com.tntu.server.docs.core.data.models.docs;

import java.util.List;

public class SectionModel {

    private long id;

    private String name;

    private List<DocumentModel> documents;

    public SectionModel() {
    }

    public SectionModel(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SectionModel(String name) {
        this.name = name;
    }

    public List<DocumentModel> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentModel> documents) {
        this.documents = documents;
    }

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
}