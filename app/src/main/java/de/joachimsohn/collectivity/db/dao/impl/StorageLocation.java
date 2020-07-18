package de.joachimsohn.collectivity.db.dao.impl;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class StorageLocation {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "storage_location_id")
    private long id;

    @ColumnInfo(name = "collection_id")
    private long collectionId;

    private String name;

    private String description;

    @Ignore
    @Embedded
    private List<Tag> tags;

    @Ignore
    @Embedded
    private List<Item> items;

    @Ignore
    public StorageLocation(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
