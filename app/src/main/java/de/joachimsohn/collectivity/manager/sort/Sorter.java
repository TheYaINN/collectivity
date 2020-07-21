package de.joachimsohn.collectivity.manager.sort;

import androidx.annotation.NonNull;

import java.util.List;

public interface Sorter<Object> {

    @NonNull
    List<Object> sortAscending(SortType sortType, List<Object> data);

    @NonNull
    List<Object> sortDescending(SortType sortType, List<Object> data);

}
