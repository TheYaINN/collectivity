package de.joachimsohn.collectivity.db.dao;

public enum Condition {
    NEW("Neu"),
    SLIGHTLY_USED("Minimale Gebrauchsspuren"),
    USED("Abgenutzt"),
    OLD("Alt"),
    ANTIQUE("Antik");

    private String condition;

    Condition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return condition;
    }
}
