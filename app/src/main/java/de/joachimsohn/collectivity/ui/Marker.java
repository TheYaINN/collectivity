package de.joachimsohn.collectivity.ui;

import lombok.Getter;

public enum Marker {

    MAIN(">> Main: ");

    @Getter
    private String text;

    Marker(String text) {
        this.text = text;
    }

}
