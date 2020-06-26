package de.joachimsohn.collectivity.db.dao.impl;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Tags")
public class Tag {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Long tagId;

    @NonNull
    String content;

    public Tag(@NonNull String content) {
        this.content = content;
    }
}
