package de.joachimsohn.collectivity.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.impl.Collection;

@Dao
public interface CollectionDAO {

    @Insert
    void insert(Collection collection);

    @Update
    void update(Collection collection);

    @Delete
    void delete(Collection collection);

    @Query("SELECT * FROM collections")
    LiveData<List<Collection>> getAllCollections();

    @Query("SELECT s.collection_id FROM collections c LEFT JOIN storagelocations s ON c.collection_id == s.collection_id LEFT JOIN items i ON s.storage_location_id  == i.storage_location_id WHERE i.item_id == :id")
    long getCollectionIdFromItemId(long id);
}
