package de.joachimsohn.collectivity.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.impl.Item;

@Dao
public interface ItemDAO {

    @Insert
    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);

    @Query("SELECT * FROM items i WHERE i.storage_location_id == :storageLocationId")
    LiveData<List<Item>> getAllItemsForID(long storageLocationId);

}
