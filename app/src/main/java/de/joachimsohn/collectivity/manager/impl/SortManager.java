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
import de.joachimsohn.collectivity.manager.sort.SortDirection;
import de.joachimsohn.collectivity.manager.sort.SortType;
import de.joachimsohn.collectivity.manager.sort.StorageLocationSorter;
import lombok.Getter;

import static de.joachimsohn.collectivity.manager.sort.SortDirection.ASCENDING;
import static de.joachimsohn.collectivity.manager.sort.SortDirection.DESCENDING;
import static de.joachimsohn.collectivity.manager.sort.SortDirection.NONE;

@Getter
public class SortManager implements Manager<SortManager> {

    private static SortManager manager;

    static {
        manager = new SortManager();
    }

    @NonNull
    private Map<SortType, SortDirection> itemSortStateMemory =
            new HashMap<SortType, SortDirection>() {{
                put(SortType.NAME, ASCENDING);
                put(SortType.AMOUNT, ASCENDING);
                put(SortType.DESCRIPTION, ASCENDING);
                put(SortType.VALUE, ASCENDING);
                put(SortType.INSERTION_DATE, ASCENDING);
                put(SortType.BUY_DATE, ASCENDING);
                put(SortType.CONDITION, ASCENDING);
                put(SortType.POSITION, ASCENDING);
            }};
    @NonNull
    private Map<SortType, SortDirection> collectionSortStateMemory =
            new HashMap<SortType, SortDirection>() {{
                put(SortType.NAME, ASCENDING);
                put(SortType.DESCRIPTION, ASCENDING);
            }};
    @NonNull
    private Map<SortType, SortDirection> storageLocationStateMemory =
            new HashMap<SortType, SortDirection>() {{
                put(SortType.NAME, ASCENDING);
                put(SortType.TAG, ASCENDING);
                put(SortType.DESCRIPTION, ASCENDING);
            }};

    public static SortManager getManager() {
        return manager;
    }

    public List<Item> sortItemsBy(@NonNull SortType criteria) {
        List<Item> itemCache = CacheManager.getManager().getItemCache().getValue();
        if (itemCache == null) {
            return null;
        }
        ItemSorter sorter = new ItemSorter();
        SortDirection direction = itemSortStateMemory.getOrDefault(criteria, NONE);
        switch (direction) {
            case NONE:
                itemSortStateMemory.replace(criteria, ASCENDING);
                return sorter.sortAscending(criteria, itemCache);
            case ASCENDING:
                itemSortStateMemory.replace(criteria, DESCENDING);
                return sorter.sortAscending(criteria, itemCache);
            case DESCENDING:
            default:
                itemSortStateMemory.replace(criteria, NONE);
                return itemCache;
        }
    }

    public List<StorageLocation> sortStorageLocationsBy(@NonNull SortType criteria) {
        List<StorageLocation> storageLocations = CacheManager.getManager().getStorageLocationCache().getValue();
        if (storageLocations == null) {
            return null;
        }
        SortDirection direction = storageLocationStateMemory.getOrDefault(criteria, NONE);
        StorageLocationSorter sorter = new StorageLocationSorter();
        switch (direction) {
            case ASCENDING:
                storageLocationStateMemory.replace(criteria, DESCENDING);
                return sorter.sortAscending(criteria, storageLocations);
            case DESCENDING:
                storageLocationStateMemory.replace(criteria, NONE);
                return sorter.sortDescending(criteria, storageLocations);
            case NONE:
            default:
                storageLocationStateMemory.replace(criteria, ASCENDING);
                return storageLocations;
        }
    }

    public List<Collection> sortCollectionsBy(@NonNull SortType criteria) {
        List<Collection> collections = CacheManager.getManager().getCollectionCache().getValue();
        if (collections == null) {
            return null;
        }
        SortDirection direction = collectionSortStateMemory.getOrDefault(criteria, NONE);
        CollectionSorter sorter = new CollectionSorter();
        switch (direction) {
            case ASCENDING:
                collectionSortStateMemory.replace(criteria, DESCENDING);
                return sorter.sortAscending(criteria, collections);
            case DESCENDING:
                collectionSortStateMemory.replace(criteria, NONE);
                return sorter.sortDescending(criteria, collections);
            case NONE:
            default:
                collectionSortStateMemory.replace(criteria, ASCENDING);
                return collections;
        }
    }
}
