package de.joachimsohn.collectivity.db.dao.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(tableName = "collections")
@Getter
@Setter
@ToString
public class Collection {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "collection_id")
    private long id;

    @NonNull
    private String name;

    @Nullable
    private String description;

    @Ignore
    @Nullable
    private List<StorageLocation> storageLocations;

    public Collection(@NonNull String name) {
        this.name = name;
        storageLocations = new ArrayList<>();
    }

    @Ignore
    public Collection(@NonNull String name, @NonNull String description) {
        this(name);
        this.description = description;
    }
}
