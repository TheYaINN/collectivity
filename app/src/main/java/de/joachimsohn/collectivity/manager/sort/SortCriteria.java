package de.joachimsohn.collectivity.manager.sort;

public enum SortCriteria {
    NAME("Name"),
    AMOUNT("Anzahl"),
    DESCRIPTION("Beschreibung"),
    EAN("EAN"),
    VALUE("Wert"),
    INSERTION_DATE("Einfüge Datum"),
    BUY_DATE("Kaufdatum"),
    CONDITION("Zustand"),
    POSITION("Position");

    private String representableName;

    SortCriteria(String representableName) {
        this.representableName = representableName;
    }
}
