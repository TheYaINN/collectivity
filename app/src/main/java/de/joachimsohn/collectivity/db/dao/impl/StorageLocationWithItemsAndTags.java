package de.joachimsohn.collectivity.db.dao.impl;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StorageLocationWithItemsAndTags {

    @Embedded
    StorageLocation storageLocation;

    @Relation(parentColumn = "storage_location_id", entityColumn = "item_id")
    List<Item> items;

    @Relation(parentColumn = "storage_location_id", entityColumn = "item_id")
    List<Tag> tags;

}
