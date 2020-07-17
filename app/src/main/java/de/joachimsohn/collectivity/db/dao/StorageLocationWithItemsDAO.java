package de.joachimsohn.collectivity.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.impl.StorageLocationWithItems;

@Dao
public interface StorageLocationWithItemsDAO {

    @Transaction
    @Query("SELECT * FROM storagelocations s WHERE s.collection_id == :collectionID")
    LiveData<List<StorageLocationWithItems>> getAllUIObjects(Long collectionID);

}
