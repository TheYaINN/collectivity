package de.joachimsohn.collectivity.db.dao.impl;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity(tableName = "tags",
        foreignKeys = {
                @ForeignKey(
                        entity = Item.class,
                        parentColumns = "item_id",
                        childColumns = "tag_id",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = StorageLocation.class,
                        parentColumns = "storage_location_id",
                        childColumns = "tag_id",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        }
)
@Getter
@Setter
@ToString
public class Tag {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tag_id")
    private long id;

    @ColumnInfo(name = "storage_location_id")
    private long storageLocationId;

    @ColumnInfo(name = "item_id")
    private long ItemId;

    private

    String content;

    public Tag(@NonNull String content) {
        this.content = content;
    }

}
