package de.joachimsohn.collectivity.manager.sort;

public enum SortCriteria {

    //TODO: maybe change to R.strings.xxx

    NAME("Name"),
    AMOUNT("Anzahl"),
    DESCRIPTION("Beschreibung"),
    EAN("EAN"),
    VALUE("Wert"),
    INSERTION_DATE("Einfüge Datum"),
    BUY_DATE("Kaufdatum"),
    CONDITION("Zustand"),
    POSITION("Position"),
    TAG("Tags");

    private String representableName;

    SortCriteria(String representableName) {
        this.representableName = representableName;
    }
}
