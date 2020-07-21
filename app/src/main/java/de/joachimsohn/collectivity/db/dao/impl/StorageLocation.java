package de.joachimsohn.collectivity.db.dao.impl;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(tableName = "storagelocations",
        foreignKeys = {
                @ForeignKey(
                        entity = Collection.class,
                        parentColumns = "collection_id",
                        childColumns = "collection_id",
                        onDelete = ForeignKey.CASCADE)
        }
)
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

    public String getSearchString() {
        String value = name;
        if (description != null) {
            value += " " + description;
        }
        if (tags != null) {
            value += tags.toString();
        }
        return value;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public StorageLocation addTag(Tag t) {
        tags.add(t);
        return this;
    }
}
