package de.joachimsohn.collectivity.manager.sort;

public enum SortDirection {
    ASCENDING("Aufsteigend"),
    DESCENDING("Absteigend");

    private String representableName;

    SortDirection(String representableName) {
        this.representableName = representableName;
    }

    public String getRepresentableName() {
        return representableName;
    }
}
