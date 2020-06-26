package de.joachimsohn.collectivity;

//TODO: propably refactor
enum DataType {

    AMOUNT("amount"),
    COLOR("color"),
    EAN("ean"),
    MONEY_VALUE("worth"),
    ARTICLE_NR("article_nr");

    private String value;

    DataType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
