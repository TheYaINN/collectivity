package de.joachimsohn.collectivity.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import de.joachimsohn.collectivity.db.dao.CollectionDAO;
import de.joachimsohn.collectivity.db.dao.ItemDAO;
import de.joachimsohn.collectivity.db.dao.StorageLocationDAO;
import de.joachimsohn.collectivity.db.dao.StorageLocationWithItemsDAO;
import de.joachimsohn.collectivity.db.dao.TagDAO;
import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.db.dao.impl.Item;
import de.joachimsohn.collectivity.db.dao.impl.StorageLocation;
import de.joachimsohn.collectivity.db.dao.impl.Tag;

@Database(entities = {Collection.class, StorageLocation.class, Tag.class, Item.class}, version = 4)
@TypeConverters({Converter.class})
public abstract class AppDataBase extends RoomDatabase {

    public abstract CollectionDAO collectionDAO();

    public abstract StorageLocationDAO storageLocationDAO();

    public abstract TagDAO tagDAO();

    public abstract ItemDAO itemDAO();

    public abstract StorageLocationWithItemsDAO uiObjectDAO();

    private static AppDataBase instance;

    public static synchronized AppDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDataBase.class,
                    "appDataBase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
