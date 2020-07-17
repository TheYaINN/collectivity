package de.joachimsohn.collectivity.manager.search;

import java.util.List;

public interface SearchStrategy {

    //TODO: when to display storageLocation and when to display Item?
    List<Object> getResultsFor(String searchValue);
}
