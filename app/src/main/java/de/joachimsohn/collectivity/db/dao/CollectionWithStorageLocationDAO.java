package de.joachimsohn.collectivity.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.impl.CollectionWithStorageLocations;

public interface CollectionWithStorageLocationDAO {

    @Transaction
    @Query("SELECT * FROM collections c WHERE c.collection_id == :collectionId")
    LiveData<List<CollectionWithStorageLocations>> getAllCollectionWithStorageLocations(Long collectionId);
}
