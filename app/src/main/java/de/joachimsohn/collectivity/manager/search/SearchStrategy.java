package de.joachimsohn.collectivity.manager.search;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.UIObject;

public interface SearchStrategy {

    //TODO: when to display storageLocation and when to display Item?
    List<UIObject> getResultsFor(String searchValue);
}
