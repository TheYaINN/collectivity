package de.joachimsohn.collectivity.manager.search;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.impl.Collection;


public class LinearSearch implements SearchStrategy {

    private List<Collection> collection;

    public LinearSearch(List<Collection> collection) {
        this.collection = collection;
    }

    @Override
    public List<Object> getResultsFor(String searchValue) {
        //TODO
        return null;
    }
}
