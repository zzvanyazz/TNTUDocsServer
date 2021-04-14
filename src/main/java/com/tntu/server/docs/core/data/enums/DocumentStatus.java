package com.tntu.server.docs.core.data.enums;

public enum DocumentStatus {

    ACTIVE(true),
    INOPERATIVE(false),
    ARCHIVED(false);

    private final boolean isVisible;

    DocumentStatus(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public boolean isVisible() {
        return isVisible;
    }
}
