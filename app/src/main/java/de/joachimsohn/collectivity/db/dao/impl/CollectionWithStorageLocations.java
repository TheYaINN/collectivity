package de.joachimsohn.collectivity.db.dao.impl;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionWithStorageLocations {

    @Embedded
    Collection collection;

    @Relation(parentColumn = "collection_id", entityColumn = "storage_location_id")
    List<StorageLocationWithItemsAndTags> storageLocations;
}
