package com.rms.enums;

public enum OrderStatus {
    WAITING("Waiting"),
    PREPARING("Preparing"),
    READY("Ready");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
