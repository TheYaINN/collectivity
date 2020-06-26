package de.joachimsohn.collectivity.db.dao.impl;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Tags")
public class Tag {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;

    @NonNull
    String content;

    public Tag(@NonNull String content) {
        this.content = content;
    }
}
