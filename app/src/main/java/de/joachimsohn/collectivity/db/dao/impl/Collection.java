package de.joachimsohn.collectivity.db.dao.impl;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Entity(tableName = "collections")
@Getter
@Setter
public class Collection {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;

    @ColumnInfo(name = "name")
    private String name;

    @Ignore
    private List<StorageLocation> storageLocations;

    public Collection(@NonNull String name) {
        this.name = name;
        storageLocations = new ArrayList<>();
    }

}
