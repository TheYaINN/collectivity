package de.joachimsohn.collectivity.db.dao.impl;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(tableName = "storagelocations",
        foreignKeys = {
                @ForeignKey(
                        entity = Collection.class,
                        parentColumns = "collection_id",
                        childColumns = "storage_location_id",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        }
)
@Getter
@Setter
@ToString
public class StorageLocation {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "storage_location_id")
    private Long id;

    @ColumnInfo(name = "collection_id")
    private int collectionId;

    private String name;

    private String description;

    @Ignore
    @Embedded
    private List<Tag> tags;

    @Ignore
    @Embedded
    private List<Item> items;

}
