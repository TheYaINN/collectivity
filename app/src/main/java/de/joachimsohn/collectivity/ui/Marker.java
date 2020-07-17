package de.joachimsohn.collectivity.ui;

import lombok.Getter;

public enum Marker {

    MAIN(">> Main: "),
    DB(">> Database: ");

    @Getter
    private String text;

    Marker(String text) {
        this.text = text;
    }

}
