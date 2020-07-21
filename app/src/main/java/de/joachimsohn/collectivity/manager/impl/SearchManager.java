package de.joachimsohn.collectivity.manager.impl;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.db.dao.impl.Item;
import de.joachimsohn.collectivity.db.dao.impl.StorageLocation;
import de.joachimsohn.collectivity.dbconnector.DataBaseConnector;

public class SearchManager {

    private static SearchManager manager;

    static {
        manager = new SearchManager();
    }

    public static SearchManager getManager() {
        return manager;
    }

    public @NonNull
    List<Collection> searchForCollection(String searchValue) {
        List<Collection> collections = CacheManager.getManager().getCollectionCache().getValue();
        if (collections != null) {
            return collections.parallelStream().filter(c -> c.getName().contains(searchValue) || c.getDescription() != null && c.getDescription().contains(searchValue)).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public @NonNull
    MediatorLiveData<List<StorageLocation>> searchForStorageLocation() {
        MediatorLiveData<List<StorageLocation>> liveDataMerger = new MediatorLiveData<>();
        List<Collection> collections = CacheManager.getManager().getCollectionCache().getValue();
        if (collections != null) {
            for (Collection c : collections) {
                liveDataMerger.addSource(DataBaseConnector.getInstance().getAllStorageLocationsForID(c.getId()), liveDataMerger::setValue);
            }
        }
        return liveDataMerger;
    }

    public @NonNull
    List<Item> searchForItem(String searchValue) {
        List<Item> items = CacheManager.getManager().getItemCache().getValue();
        List<Item> filteredItems = new ArrayList<>();
        if (items != null) {
            filteredItems = items.parallelStream().filter(i -> i.getAllAttributes().contains(searchValue)).collect(Collectors.toList());
        }
        return filteredItems;
    }

}
