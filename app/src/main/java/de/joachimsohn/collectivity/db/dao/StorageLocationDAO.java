package de.joachimsohn.collectivity.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.impl.StorageLocation;

@Dao
public interface StorageLocationDAO {

    @Insert
    void insert(StorageLocation storageLocation);

    @Update
    void update(StorageLocation storageLocation);

    @Delete
    void delete(StorageLocation storageLocation);

    @Query("SELECT * FROM storagelocations s WHERE s.collection_id == :collectionId")
    LiveData<List<StorageLocation>> getAllStorageLocationsFromCollectionId(Long collectionId);

    @Query("SELECT * FROM storagelocations")
    LiveData<List<StorageLocation>> getAllStorageLocations();

}
