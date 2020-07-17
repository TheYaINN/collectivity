package de.joachimsohn.collectivity.db.dao.impl;


import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import de.joachimsohn.collectivity.db.dao.Condition;
import de.joachimsohn.collectivity.db.dao.UIObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(tableName = "items", foreignKeys = {
        @ForeignKey(
                entity = StorageLocation.class,
                parentColumns = "id",
                childColumns = "id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)}
)
@Getter
@Setter
@ToString
public class Item extends UIObject {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String name;

    private int amount;

    private String description;

    private String ean;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private Bitmap icon;

    private BigDecimal value;

    private Date insertionDate;

    private Date buyDate;

    @Ignore
    private List<Tag> tags;

    private Condition condition;

    private String position;

}
