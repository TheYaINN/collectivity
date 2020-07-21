package de.joachimsohn.collectivity.manager.sort;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.stream.Collectors;

import de.joachimsohn.collectivity.db.dao.impl.StorageLocation;

public class StorageLocationSorter implements Sorter<StorageLocation> {

    @Override
    @NonNull
    public List<StorageLocation> sortAscending(SortCriteria sortCriteria, SortDirection direction, List<StorageLocation> storageLocations) {
        switch (sortCriteria) {
            case NAME:
                return storageLocations.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
            case DESCRIPTION:
                return storageLocations.stream().sorted((o1, o2) -> o1.getDescription().compareTo(o2.getDescription())).collect(Collectors.toList());
            default:
                return storageLocations;
        }
    }

    @NonNull
    @Override
    public List<StorageLocation> sortDescending(SortCriteria sortCriteria, SortDirection direction, List<StorageLocation> storageLocations) {
        switch (sortCriteria) {
            case NAME:
                return storageLocations.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
            case DESCRIPTION:
                return storageLocations.stream().sorted((o1, o2) -> o1.getDescription().compareTo(o2.getDescription())).collect(Collectors.toList());
            default:
                return storageLocations;
        }
    }
}
