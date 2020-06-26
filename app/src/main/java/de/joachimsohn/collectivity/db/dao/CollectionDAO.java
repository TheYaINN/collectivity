package de.joachimsohn.collectivity.db.dao;

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

    @Query("DELETE FROM Collections")
    void deleteAllItems();

    @Query("SELECT * FROM Collections")
    List<Collection> getAllItems();

    @Query("SELECT * FROM Collections WHERE :col LIKE :term")
    List<Collection> getItemsWithParamLike(String col, String term);

}
