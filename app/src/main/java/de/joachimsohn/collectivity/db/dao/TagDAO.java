package de.joachimsohn.collectivity.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.impl.Tag;

@Dao
public interface TagDAO {

    @Insert
    void insert(Tag tag);

    @Update
    void update(Tag tag);

    @Delete
    void delete(Tag tag);

    @Query("DELETE FROM tags")
    void deleteAllTags();

    @Query("SELECT * FROM tags")
    LiveData<List<Tag>> getAllTags();

    @Query("SELECT * FROM tags WHERE :col LIKE :term")
    LiveData<List<Tag>> getTagsWithParamLike(String col, String term);

}
