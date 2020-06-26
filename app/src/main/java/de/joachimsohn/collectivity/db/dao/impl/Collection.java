package de.joachimsohn.collectivity.db.dao.impl;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "Collections")
public class Collection {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @Embedded
    private Long collectionId;

    @NonNull
    private String name;

    @NonNull
    @Relation(parentColumn = "collectionId", entityColumn = "storageLocationId")
    private List<StorageLocation> storageLocations;

    public Collection(@NonNull String name) {
        this.name = name;
        storageLocations = new ArrayList<>();
    }

    @NonNull
    public Long getId() {
        return collectionId;
    }

    public void setId(@NonNull Long id) {
        this.collectionId = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public List<StorageLocation> getStorageLocations() {
        return storageLocations;
    }

    public void setStorageLocations(@NonNull List<StorageLocation> storageLocations) {
        this.storageLocations = storageLocations;
    }
}
