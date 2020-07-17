package de.joachimsohn.collectivity.manager.impl;

import java.util.HashMap;
import java.util.Map;

import de.joachimsohn.collectivity.manager.sort.ItemSorter;
import de.joachimsohn.collectivity.manager.sort.SortCriteria;
import de.joachimsohn.collectivity.manager.sort.SortDirection;

public class SortManager {

    private static SortManager manager;
    private ItemSorter sorter = new ItemSorter();

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

    public SortDirection sortItemsBy(SortCriteria criteria) {
        SortDirection direction = itemSortStateMemory.getOrDefault(criteria, SortDirection.NONE);
        switch (direction) {
            case NONE:
                direction = SortDirection.ASCENDING;
                itemSortStateMemory.replace(criteria, SortDirection.ASCENDING);
                break;
            case ASCENDING:
                direction = SortDirection.DESCENDING;
                itemSortStateMemory.replace(criteria, SortDirection.DESCENDING);
                break;
            case DESCENDING:
            default:
                direction = SortDirection.NONE;
                itemSortStateMemory.replace(criteria, SortDirection.NONE);
                break;
        }
        sorter.sort(criteria, direction);
        return direction;
    }

    public SortDirection sortStorageLocationsBy(SortCriteria criteria) {
        SortDirection direction = storageLocationStateMemory.getOrDefault(criteria, SortDirection.NONE);
        switch (direction) {
            case NONE:
                direction = SortDirection.ASCENDING;
                storageLocationStateMemory.replace(criteria, SortDirection.ASCENDING);
                break;
            case ASCENDING:
                direction = SortDirection.DESCENDING;
                storageLocationStateMemory.replace(criteria, SortDirection.DESCENDING);
                break;
            case DESCENDING:
            default:
                direction = SortDirection.NONE;
                storageLocationStateMemory.replace(criteria, SortDirection.NONE);
                break;
        }
        sorter.sort(criteria, direction);
        return direction;
    }

    public SortDirection sortCollectionsBy(SortCriteria criteria) {
        SortDirection direction = collectionSortStateMemory.getOrDefault(criteria, SortDirection.NONE);
        assert direction != null;
        switch (direction) {
            case NONE:
                direction = SortDirection.ASCENDING;
                collectionSortStateMemory.replace(criteria, SortDirection.ASCENDING);
                break;
            case ASCENDING:
                direction = SortDirection.DESCENDING;
                collectionSortStateMemory.replace(criteria, SortDirection.DESCENDING);
                break;
            case DESCENDING:
            default:
                direction = SortDirection.NONE;
                collectionSortStateMemory.replace(criteria, SortDirection.NONE);
                break;
        }
        collectionSortStateMemory.replace(criteria, direction);
        return direction;
    }
}
