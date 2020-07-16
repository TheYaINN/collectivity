package de.joachimsohn.collectivity.db.dao;

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

    @Query("DELETE FROM items")
    void deleteAllItems();

    @Query("SELECT * FROM items")
    List<Item> getAllItems();

    @Query("SELECT * FROM items WHERE :col LIKE :term")
    List<Item> getItemsWithParamLike(String col, String term);

}
