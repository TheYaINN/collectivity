package de.joachimsohn.collectivity.db.dao.impl;


import android.graphics.Bitmap;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import de.joachimsohn.collectivity.db.dao.Condition;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Entity(tableName = "items", foreignKeys = {
        @ForeignKey(
                entity = StorageLocation.class,
                parentColumns = "storage_location_id",
                childColumns = "storage_location_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)}
)
public class Item {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id")
    private long id;

    @ColumnInfo(name = "storage_location_id")
    private long storageLocationId;

    @NonNull
    private String name;

    private int amount;

    @Nullable
    private String description;

    @Nullable
    private String ean;

    @Nullable
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private Bitmap icon;

    @Nullable
    private BigDecimal value;

    @NonNull
    private Calendar insertionDate;

    @Nullable
    private Calendar buyDate;

    @Ignore
    private List<Tag> tags;

    @NonNull
    private Condition condition;

    @NonNull
    private String position;

    public Item(@NonNull String name, @NonNull Calendar insertionDate, @NonNull Condition condition, @NonNull String position, long storageLocationId) {
        this.name = name;
        this.insertionDate = insertionDate;
        this.condition = condition;
        this.position = position;
        this.storageLocationId = storageLocationId;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public String getAllAttributes() {
        StringBuilder sb = new StringBuilder();
        sb.append("Anzahl: ").append(getAmount()).append(", ");
        if (description != null) {
            sb.append("Beschreibung: ").append(getDescription()).append(", ");
        }
        if (ean != null) {
            sb.append("EAN: ").append(getEan()).append(", ");
        }
        if (value != null) {
            sb.append("Wert: ").append(getValue()).append(", ");
        }
        if (buyDate != null) {
            sb.append("Kaufdatum: ").append(new SimpleDateFormat("dd/MM/yyyy").format(buyDate.getTime())).append(", ");
        }
        if (tags != null && tags.size() > 0) {
            sb.append("Tag: ").append(getTags()).append(", ");
        }
        sb.append("Zustand: ").append(getCondition()).append(", ");
        sb.append("Position: ").append(getPosition());
        return sb.toString();
    }

    public String getSearchString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(getAmount());
        if (description != null) {
            sb.append(getDescription());
        }
        if (ean != null) {
            sb.append(getEan());
        }
        if (value != null) {
            sb.append(getValue());
        }
        if (buyDate != null) {
            sb.append(new SimpleDateFormat("dd/MM/yyyy").format(buyDate.getTime()));
        }
        if (tags != null && tags.size() > 0) {
            sb.append(getTags());
        }
        sb.append(getCondition());
        sb.append(getPosition());
        return sb.toString();
    }

}
