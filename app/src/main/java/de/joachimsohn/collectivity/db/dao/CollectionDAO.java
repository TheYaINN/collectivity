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

    @Query("SELECT * FROM collections WHERE :col LIKE :term")
    LiveData<List<Collection>> getCollectionsWithParamLike(String col, String term);

}
