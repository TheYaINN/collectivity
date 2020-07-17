package de.joachimsohn.collectivity.manager.search;

import lombok.Getter;

public enum SearchType {
    COLLECTION,
    STORAGELOCATION,
    ITEM;

    public static final String EXTRA = "SearchType";
}
