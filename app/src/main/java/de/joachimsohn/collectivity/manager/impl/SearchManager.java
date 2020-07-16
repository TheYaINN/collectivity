package de.joachimsohn.collectivity.manager.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.joachimsohn.collectivity.db.dao.UIObject;
import lombok.NonNull;

public class SearchManager {

    private static SearchManager manager;

    static {
        manager = new SearchManager();
    }

    public static SearchManager getManager() {
        return manager;
    }

    public @NonNull List<UIObject> search(String searchValue) {
        @NonNull List<UIObject> items = searchForItems(searchValue);
        @NonNull List<UIObject> storageLocations = searchForStorageLocation(searchValue);
        return Stream.concat(items.stream(), storageLocations.stream())
                .collect(Collectors.toList());
    }

    public @NonNull List<UIObject> searchForItems(String searchValue) {
        //TODO: Find storageLocation and search in this location with property @Code searchValue given
        return null;
    }

    public @NonNull List<UIObject> searchForStorageLocation(String searchValue) {
        //TODO: Find Collection and search for Storage locations with property @Code searchValue given
        return null;
    }

}
