package de.joachimsohn.collectivity.db.dao.impl;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.UIObject;
import lombok.Getter;
import lombok.Setter;

@Entity(tableName = "storagelocations",
        foreignKeys = {
                @ForeignKey(
                        entity = Collection.class,
                        parentColumns = "id",
                        childColumns = "id",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        }
)
@Getter
@Setter
public class StorageLocation extends UIObject {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String name;

    private String description;

    @Ignore
    private List<Tag> tags;

    @Ignore
    private List<Item> items;

}
