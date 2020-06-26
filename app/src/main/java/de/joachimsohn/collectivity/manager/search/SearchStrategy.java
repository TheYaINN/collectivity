package de.joachimsohn.collectivity.manager.search;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface SearchStrategy {

    //TODO: when to display storageLocation and when to display Item?
    List<SearchableObject> getResultsFor(String searchValue);
}
