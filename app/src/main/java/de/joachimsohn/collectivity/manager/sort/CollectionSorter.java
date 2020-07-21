package de.joachimsohn.collectivity.manager.sort;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import de.joachimsohn.collectivity.db.dao.impl.Collection;

public class CollectionSorter implements Sorter<Collection> {
    @NonNull
    @Override
    public List<Collection> sortAscending(SortType sortType, SortDirection direction, List<Collection> collections) {
        switch (sortType) {
            case NAME:
                return collections.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
            case DESCRIPTION:
                return collections.stream().sorted((o1, o2) -> {
                    if (o1.getDescription() == null) {
                        o1.setDescription("");
                    }
                    if (o2.getDescription() == null) {
                        o2.setDescription("");
                    }
                    return o1.getDescription().compareTo(o2.getDescription());
                }).collect(Collectors.toList());
            default:
                return collections;
        }
    }

    @NonNull
    @Override
    public List<Collection> sortDescending(SortType sortType, SortDirection
            direction, List<Collection> collections) {
        List<Collection> list = sortAscending(sortType, direction, collections);
        Collections.reverse(list);
        return list;
    }
}
