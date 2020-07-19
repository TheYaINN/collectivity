package de.joachimsohn.collectivity.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.db.dao.impl.Item;
import de.joachimsohn.collectivity.db.dao.impl.StorageLocation;
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
        List<Collection> filteredCollections = new ArrayList<Collection>();
        if (collections != null) {
            filteredCollections = collections.parallelStream().filter(c -> c.getName().contains(searchValue) || c.getDescription() != null && c.getDescription().contains(searchValue)).collect(Collectors.toList());
        }
        return filteredCollections;
    }

    public @NonNull List<StorageLocation> searchForStorageLocation(String searchValue) {
        List<StorageLocation> storageLocations = CacheManager.getManager().getStorageLocations().getValue();
        List<StorageLocation> filteredStorageCollections = new ArrayList<>();
        if (storageLocations != null) {
            filteredStorageCollections = storageLocations.parallelStream().filter(s -> s.getSearchString().contains(searchValue)).collect(Collectors.toList());
        }
        return filteredStorageCollections;
    }

    public @NonNull List<Item> searchForItem(String searchValue) {
        List<Item> items = CacheManager.getManager().getItems().getValue();
        List<Item> filteredItems = new ArrayList<>();
        if (items != null) {
            filteredItems = items.parallelStream().filter(i -> i.getAllAttributes().contains(searchValue)).collect(Collectors.toList());
        }
        return filteredItems;
    }

}
