package com.dev.voyagewell.model.room;

public enum Type {
    SINGLE("Single"),
    DOUBLE("Double"),
    TRIPLE("Triple");
    private final String name;

    Type(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
