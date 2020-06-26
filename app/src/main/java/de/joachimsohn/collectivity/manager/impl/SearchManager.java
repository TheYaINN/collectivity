package de.joachimsohn.collectivity.manager.impl;

import java.util.List;

import de.joachimsohn.collectivity.manager.search.LinearSearch;
import de.joachimsohn.collectivity.manager.search.SearchStrategy;
import de.joachimsohn.collectivity.manager.search.SearchableObject;

public class SearchManager {

    private static SearchManager manager;

    static {
        manager = new SearchManager();
    }

    public List<SearchableObject> search(String searchValue) {
        SearchStrategy search = new LinearSearch(CollectionManager.getManager().getCollection());
        return search.getResultsFor(searchValue);
    }

    public SearchManager getManager() {
        return manager;
    }

}
