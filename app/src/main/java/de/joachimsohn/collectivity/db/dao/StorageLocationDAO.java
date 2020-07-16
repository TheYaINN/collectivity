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

    @Query("DELETE FROM storagelocations")
    void deleteAllStorageLocations();

    @Query("SELECT * FROM storagelocations ")
    LiveData<List<StorageLocation>> getAllStorageLocations();

    @Query("SELECT * FROM StorageLocations WHERE :col LIKE :term")
    LiveData<List<StorageLocation>> getStorageLocationsWithParamLike(String col, String term);

}
