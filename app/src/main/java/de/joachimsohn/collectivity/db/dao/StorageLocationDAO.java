package de.joachimsohn.collectivity.db.dao;

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

    @Query("DELETE FROM StorageLocations")
    void deleteAllItems();

    @Query("SELECT * FROM StorageLocations")
    List<StorageLocation> getAllItems();

    @Query("SELECT * FROM StorageLocations WHERE :col LIKE :term")
    List<StorageLocation> getItemsWithParamLike(String col, String term);

}
