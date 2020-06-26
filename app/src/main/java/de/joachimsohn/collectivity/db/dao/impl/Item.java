package de.joachimsohn.collectivity.db.dao.impl;


import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import de.joachimsohn.collectivity.db.dao.Condition;
import de.joachimsohn.collectivity.manager.search.SearchableObject;

@Entity(tableName = "Items")
public class Item extends SearchableObject {

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @Nullable
    public String getEan() {
        return ean;
    }

    public void setEan(@Nullable String ean) {
        this.ean = ean;
    }

    @Nullable
    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(@Nullable Bitmap icon) {
        this.icon = icon;
    }

    @NonNull
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(@NonNull BigDecimal value) {
        this.value = value;
    }

    @NonNull
    public Date getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(@NonNull Date insertionDate) {
        this.insertionDate = insertionDate;
    }

    @NonNull
    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(@NonNull Date buyDate) {
        this.buyDate = buyDate;
    }

    @NonNull
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(@NonNull List<Tag> tags) {
        this.tags = tags;
    }

    @NonNull
    public Condition getCondition() {
        return condition;
    }

    public void setCondition(@NonNull Condition condition) {
        this.condition = condition;
    }

    @NonNull
    public String getPosition() {
        return position;
    }

    public void setPosition(@NonNull String position) {
        this.position = position;
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private int amount;

    @NonNull
    private String description;

    @Nullable
    private String ean;

    @Nullable
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private Bitmap icon;

    @NonNull
    private BigDecimal value;

    @NonNull
    private Date insertionDate;

    @NonNull
    private Date buyDate;

    @NonNull
    @Ignore
    private List<Tag> tags;

    @NonNull
    private Condition condition;

    @NonNull
    private String position;

}
