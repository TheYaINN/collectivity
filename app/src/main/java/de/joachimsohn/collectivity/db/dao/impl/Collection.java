package de.joachimsohn.collectivity.db.dao.impl;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Entity(tableName = "Collections")
public class Collection {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    @Getter
    private List<StorageLocation> storageLocations;

    public Collection(@NonNull String name) {
        this.name = name;
        storageLocations = new ArrayList<>();
    }

}
