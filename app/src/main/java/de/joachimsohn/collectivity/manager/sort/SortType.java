package de.joachimsohn.collectivity.manager.sort;

public enum SortType {

    NAME("Name"),
    AMOUNT("Anzahl"),
    DESCRIPTION("Beschreibung"),
    VALUE("Wert"),
    INSERTION_DATE("Einfüge Datum"),
    BUY_DATE("Kaufdatum"),
    CONDITION("Zustand"),
    POSITION("Position"),
    TAG("Tags");

    private String representableName;

    SortType(String representableName) {
        this.representableName = representableName;
    }
}
