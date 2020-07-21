package de.joachimsohn.collectivity.manager.sort;

import androidx.annotation.NonNull;

import java.util.List;

public interface Sorter<Object> {

    @NonNull
    List<Object> sortAscending(SortCriteria sortCriteria, SortDirection direction, List<Object> data);

    @NonNull
    List<Object> sortDescending(SortCriteria sortCriteria, SortDirection direction, List<Object> data);

}
