package de.joachimsohn.collectivity.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.joachimsohn.collectivity.db.dao.impl.Collection;
import lombok.NonNull;

public class SearchManager {

    private static SearchManager manager;

    static {
        manager = new SearchManager();
    }

    public static SearchManager getManager() {
        return manager;
    }

    public @NonNull List<Collection> search(String searchValue) {
        return null;
    }

    public @NonNull List<Collection> searchForCollection(String searchValue) {
        List<Collection> collections = CacheManager.getManager().getCollections().getValue();
        List<Collection> filteredItems = new ArrayList<Collection>();
        if (collections != null) {
            filteredItems = collections.parallelStream().filter(c -> c.getName().contains(searchValue) || c.getDescription() != null && c.getDescription().contains(searchValue)).collect(Collectors.toList());
        }
        return filteredItems;
    }

    public @NonNull List<Object> searchForItem(String searchValue) {
        return null;
    }

    public @NonNull List<Object> searchForStorageLocation(String searchValue) {
        return null;
    }

}
