package de.joachimsohn.collectivity.manager.sort;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import de.joachimsohn.collectivity.db.dao.impl.StorageLocation;

public class StorageLocationSorter implements Sorter<StorageLocation> {

    @Override
    @NonNull
    public List<StorageLocation> sortAscending(SortType sortType, List<StorageLocation> storageLocations) {
        switch (sortType) {
            case NAME:
                return storageLocations.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
            case DESCRIPTION:
                return storageLocations.stream().sorted((o1, o2) -> {
                    if (o1.getDescription() == null) {
                        o1.setDescription("");
                    }
                    if (o2.getDescription() == null) {
                        o2.setDescription("");
                    }
                    return o1.getDescription().compareTo(o2.getDescription());
                }).collect(Collectors.toList());
            default:
                return storageLocations;
        }
    }

    @NonNull
    @Override
    public List<StorageLocation> sortDescending(SortType sortType, List<StorageLocation> storageLocations) {
        List<StorageLocation> list = sortAscending(sortType, storageLocations);
        Collections.reverse(list);
        return list;
    }
}
