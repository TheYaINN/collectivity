package de.joachimsohn.collectivity.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.impl.StorageLocationWithItemsAndTags;


@Dao
public interface StorageLocationWithItemsAndTagsDAO {

    @Transaction
    @Query("SELECT * FROM storagelocations")
    LiveData<List<StorageLocationWithItemsAndTags>> getAllUIObjects(Long collectionId);
}
