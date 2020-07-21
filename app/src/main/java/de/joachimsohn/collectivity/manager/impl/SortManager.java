package de.joachimsohn.collectivity.manager.impl;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.db.dao.impl.Item;
import de.joachimsohn.collectivity.db.dao.impl.StorageLocation;
import de.joachimsohn.collectivity.manager.Manager;
import de.joachimsohn.collectivity.manager.sort.CollectionSorter;
import de.joachimsohn.collectivity.manager.sort.ItemSorter;
import de.joachimsohn.collectivity.manager.sort.SortCriteria;
import de.joachimsohn.collectivity.manager.sort.SortDirection;
import de.joachimsohn.collectivity.manager.sort.StorageLocationSorter;

public class SortManager implements Manager<SortManager> {

    private static SortManager manager;

    private Map<SortCriteria, SortDirection> itemSortStateMemory =
            new HashMap<SortCriteria, SortDirection>() {{
                put(SortCriteria.NAME, SortDirection.NONE);
                put(SortCriteria.AMOUNT, SortDirection.NONE);
                put(SortCriteria.DESCRIPTION, SortDirection.NONE);
                put(SortCriteria.EAN, SortDirection.NONE);
                put(SortCriteria.VALUE, SortDirection.NONE);
                put(SortCriteria.INSERTION_DATE, SortDirection.NONE);
                put(SortCriteria.BUY_DATE, SortDirection.NONE);
                put(SortCriteria.CONDITION, SortDirection.NONE);
                put(SortCriteria.POSITION, SortDirection.NONE);
            }};

    private Map<SortCriteria, SortDirection> collectionSortStateMemory =
            new HashMap<SortCriteria, SortDirection>() {{
                put(SortCriteria.NAME, SortDirection.NONE);
                put(SortCriteria.DESCRIPTION, SortDirection.NONE);
            }};

    private Map<SortCriteria, SortDirection> storageLocationStateMemory =
            new HashMap<SortCriteria, SortDirection>() {{
                put(SortCriteria.NAME, SortDirection.NONE);
                put(SortCriteria.TAG, SortDirection.NONE);
                put(SortCriteria.DESCRIPTION, SortDirection.NONE);
            }};

    static {
        manager = new SortManager();
    }

    public static SortManager getManager() {
        return manager;
    }

    public List<Item> sortItemsBy(SortCriteria criteria) {
        List<Item> itemCache = CacheManager.getManager().getItemCache().getValue();
        if (itemCache == null) {
            return null;
        }
        ItemSorter sorter = new ItemSorter();
        SortDirection direction = itemSortStateMemory.getOrDefault(criteria, SortDirection.NONE);
        switch (direction) {
            case NONE:
                itemSortStateMemory.replace(criteria, SortDirection.ASCENDING);
                return sorter.sortAscending(criteria, SortDirection.NONE, itemCache);
            case ASCENDING:
                itemSortStateMemory.replace(criteria, SortDirection.DESCENDING);
                return sorter.sortAscending(criteria, SortDirection.ASCENDING, itemCache);
            case DESCENDING:
            default:
                itemSortStateMemory.replace(criteria, SortDirection.NONE);
                return itemCache;
        }
    }

    public List<StorageLocation> sortStorageLocationsBy(SortCriteria criteria) {
        List<StorageLocation> storageLocations = CacheManager.getManager().getStorageLocationCache().getValue();
        if (storageLocations == null) {
            return null;
        }
        SortDirection direction = storageLocationStateMemory.getOrDefault(criteria, SortDirection.NONE);
        StorageLocationSorter sorter = new StorageLocationSorter();
        switch (direction) {
            case NONE:
                storageLocationStateMemory.replace(criteria, SortDirection.ASCENDING);
                return storageLocations;
            case ASCENDING:
                storageLocationStateMemory.replace(criteria, SortDirection.DESCENDING);
                return sorter.sortAscending(criteria, SortDirection.ASCENDING, storageLocations);
            case DESCENDING:
            default:
                storageLocationStateMemory.replace(criteria, SortDirection.NONE);
                return sorter.sortDescending(criteria, direction, storageLocations);
        }
    }

    public List<Collection> sortCollectionsBy(@NonNull SortCriteria criteria) {
        List<Collection> collections = CacheManager.getManager().getCollectionCache().getValue();
        if (collections == null) {
            return null;
        }
        SortDirection direction = collectionSortStateMemory.getOrDefault(criteria, SortDirection.NONE);
        CollectionSorter sorter = new CollectionSorter();
        switch (direction) {
            case ASCENDING:
                collectionSortStateMemory.replace(criteria, SortDirection.DESCENDING);
                sorter.sortAscending(criteria, direction, collections);
            case DESCENDING:
                sorter.sortDescending(criteria, direction, collections);
            case NONE:
            default:
                collectionSortStateMemory.replace(criteria, SortDirection.ASCENDING);
                return collections;
        }
    }
}
