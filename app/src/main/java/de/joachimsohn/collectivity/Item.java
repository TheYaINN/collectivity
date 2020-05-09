package de.joachimsohn.collectivity;


import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.OffsetDateTime;

@Entity(tableName = "item_table")
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String title;

    @NonNull
    private OffsetDateTime creationDate;

    @Nullable
    private String ean;

    @Nullable
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private Bitmap icon;

    @Nullable
    private OffsetDateTime buyDate;

    @Nullable
    private String price;

    public Item(@NonNull String title, @NonNull OffsetDateTime creationDate, @Nullable String ean, @Nullable Bitmap icon, @Nullable OffsetDateTime buyDate, String price) {
        this.title = title;
        this.creationDate = creationDate;
        this.ean = ean;
        this.icon = icon;
        this.buyDate = buyDate;
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    @Nullable
    public String getEan() {
        return ean;
    }

    @Nullable
    public Bitmap getIcon() {
        return icon;
    }

    @Nullable
    public OffsetDateTime getBuyDate() {
        return buyDate;
    }

    public String getPrice() {
        return price;
    }
}
