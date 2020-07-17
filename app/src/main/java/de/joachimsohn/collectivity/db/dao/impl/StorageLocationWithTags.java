package de.joachimsohn.collectivity.db.dao.impl;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StorageLocationWithTags {

    @Embedded
    StorageLocation storageLocation;

    @Relation(parentColumn = "storage_location_id", entityColumn = "tag_id")
    List<Tag> tags;
}
