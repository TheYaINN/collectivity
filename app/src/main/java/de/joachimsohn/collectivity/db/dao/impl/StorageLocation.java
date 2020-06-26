package de.joachimsohn.collectivity.db.dao.impl;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;
import java.util.UUID;

import de.joachimsohn.collectivity.manager.search.SearchableObject;

@Entity(tableName = "StorageLocations")
public class StorageLocation extends SearchableObject {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private List<Tag> tags;

    @NonNull
    private List<Item> items;

}
