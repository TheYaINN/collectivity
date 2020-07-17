package de.joachimsohn.collectivity.db.dao.impl;


import android.graphics.Bitmap;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.db.dao.Condition;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity(tableName = "items", foreignKeys = {
        @ForeignKey(
                entity = StorageLocation.class,
                parentColumns = "storage_location_id",
                childColumns = "item_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)}
)
@Getter
@Setter
@ToString
public class Item {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id")
    private Long id;

    @ColumnInfo(name = "storage_location_id")
    private Long storageLocationId;

    @NonNull
    private String name;

    private int amount;

    @Nullable
    private String description;

    @Nullable
    private String ean;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    @Nullable
    private Bitmap icon;

    @Nullable
    private BigDecimal value;

    @NonNull
    private Date insertionDate;

    @Nullable
    private Date buyDate;

    @Ignore
    private List<Tag> tags;

    @NonNull
    private Condition condition;

    @NonNull
    private String position;

    public String getAllAttributes() {
        StringBuilder sb = new StringBuilder();
        //TODO: read from strings
        sb.append(R.string.action_dropdown_amount).append(": ").append(getAmount()).append(", ");
        if (description != null) {
            sb.append(R.string.action_dropdown_description).append(": ").append(getDescription()).append(", ");
        }
        if (ean != null) {
            sb.append(R.string.action_dropdown_ean).append(": ").append(getEan()).append(", ");
        }
        if (value != null) {
            sb.append(R.string.action_dropdown_value).append(": ").append(getValue()).append(", ");
        }
        if (buyDate != null) {
            sb.append(R.string.action_dropdown_buydate).append(": ").append(getBuyDate()).append(", ");
        }
        if (tags != null && tags.size() > 0) {
            sb.append(R.string.Tags).append(": ").append(getTags()).append(", ");
        }
        sb.append(R.string.action_dropdown_condition).append(": ").append(getCondition()).append(", ");
        sb.append(R.string.action_dropdown_position).append(": ").append(getPosition());
        return sb.toString();
    }
}
