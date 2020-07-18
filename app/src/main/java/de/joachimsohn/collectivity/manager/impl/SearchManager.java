package de.joachimsohn.collectivity.manager.impl;

import java.util.List;

import lombok.NonNull;

public class SearchManager {

    private static SearchManager manager;

    static {
        manager = new SearchManager();
    }

    public static SearchManager getManager() {
        return manager;
    }

    //TODO: change returntype to something better, but it works for now
    public @NonNull List<Object> searchForCollection(String searchValue) {
        CacheManager.getManager().getCollections();
        return null;
    }

    public @NonNull List<Object> searchForItem(String searchValue) {
        return null;
    }

    public @NonNull List<Object> searchForStorageLocation(String searchValue) {
        return null;
    }

}
