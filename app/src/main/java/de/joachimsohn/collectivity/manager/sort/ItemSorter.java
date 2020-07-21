package de.joachimsohn.collectivity.manager.sort;

import androidx.annotation.NonNull;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import de.joachimsohn.collectivity.db.dao.impl.Item;

public class ItemSorter implements Sorter<Item> {

    @Override
    @NonNull
    public List<Item> sortAscending(SortType sortType, SortDirection direction, List<Item> items) {
        switch (sortType) {
            case NAME:
                return items.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
            case AMOUNT:
                return items.stream().sorted((o1, o2) -> o1.getAmount() - o2.getAmount()).collect(Collectors.toList());
            case DESCRIPTION:
                return items.stream().sorted((o1, o2) -> {
                    if (o1.getDescription() == null) {
                        o1.setDescription("");
                    }
                    if (o2.getDescription() == null) {
                        o2.setDescription("");
                    }
                    return o1.getDescription().compareTo(o2.getDescription());
                }).collect(Collectors.toList());
            case VALUE:
                return items.stream().sorted((o1, o2) -> {
                    if (o1.getValue() == null) {
                        o1.setValue(BigDecimal.ZERO);
                    }
                    if (o2.getValue() == null) {
                        o2.setValue(BigDecimal.ZERO);
                    }
                    return o1.getValue().compareTo(o2.getValue());
                }).collect(Collectors.toList());
            case INSERTION_DATE:
                return items.stream().sorted((o1, o2) -> o1.getInsertionDate().compareTo(o2.getInsertionDate())).collect(Collectors.toList());
            case BUY_DATE:
                return items.stream().sorted((o1, o2) -> {
                    if (o1.getBuyDate() == null) {
                        o1.setBuyDate(Calendar.getInstance());
                    }
                    if (o2.getBuyDate() == null) {
                        o2.setBuyDate(Calendar.getInstance());
                    }
                    return o1.getBuyDate().compareTo(o2.getBuyDate());
                }).collect(Collectors.toList());
            case CONDITION:
                return items.stream().sorted((o1, o2) -> o1.getCondition().compareTo(o2.getCondition())).collect(Collectors.toList());
            case POSITION:
                return items.stream().sorted((o1, o2) -> o1.getPosition().compareTo(o2.getPosition())).collect(Collectors.toList());
            default:
                return items;
        }
    }

    @NonNull
    @Override
    public List<Item> sortDescending(SortType sortType, SortDirection direction, List<Item> items) {
        List<Item> list = sortAscending(sortType, direction, items);
        Collections.reverse(list);
        return list;
    }
}
