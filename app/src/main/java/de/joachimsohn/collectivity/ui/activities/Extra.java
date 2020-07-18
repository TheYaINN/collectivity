package de.joachimsohn.collectivity.ui.activities;

import lombok.Getter;

@Getter
public enum Extra {

    ID("id");

    private String value;

    Extra(String value) {
        this.value = value;
    }
}
