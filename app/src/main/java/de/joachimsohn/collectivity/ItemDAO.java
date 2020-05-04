package de.joachimsohn.collectivity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDAO {

    @Insert
    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);

    @Query("DELETE FROM item_table")
    void deleteAllItems();

    @Query("SELECT * FROM item_table")
    LiveData<List<Item>> getAllItems();

    @Query("SELECT * FROM item_table WHERE :col LIKE :term")
    LiveData<List<Item>> getItemsWithParamLike(String col, String term);

}
