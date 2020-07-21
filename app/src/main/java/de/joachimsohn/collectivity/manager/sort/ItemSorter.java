package de.joachimsohn.collectivity.manager.sort;

import androidx.annotation.NonNull;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.impl.Item;

public class ItemSorter implements Sorter<Item> {

    @Override
    @NonNull
    public List<Item> sortAscending(SortCriteria sortCriteria, SortDirection direction, List<Item> items) {
        switch (sortCriteria) {
            case NAME:

            case AMOUNT:
            case DESCRIPTION:
            case EAN:
            case VALUE:
            case INSERTION_DATE:
            case BUY_DATE:
            case TAG:
            case CONDITION:
            case POSITION:
            default:
                return items;
        }
    }

    @NonNull
    @Override
    public List<Item> sortDescending(SortCriteria sortCriteria, SortDirection direction, List<Item> storageLocations) {
        //TODO:
        return null;
    }
}
