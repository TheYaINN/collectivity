package de.joachimsohn.collectivity.db.dao.impl;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.UIObject;

@Entity(tableName = "StorageLocations")
public class StorageLocation extends UIObject {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @Embedded
    private Long storageLocationId;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    @Relation(parentColumn = "storageLocationId", entityColumn = "tagId")
    private List<Tag> tags;

    @NonNull
    @Relation(parentColumn = "storageLocationId", entityColumn = "itemId")
    private List<Item> items;

}
