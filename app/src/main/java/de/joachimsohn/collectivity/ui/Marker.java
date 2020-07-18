package de.joachimsohn.collectivity.ui;

import lombok.Getter;

public enum Marker {

    MAIN(">> Main: "),
    DB(">> Database: "),
    STORAGELOCATION(">> StorageLocation: "),
    SEARCH(">> Search:"), CACHEMANAGER(">> CacheManager: ");

    @Getter
    private String text;

    Marker(String text) {
        this.text = text;
    }

}
