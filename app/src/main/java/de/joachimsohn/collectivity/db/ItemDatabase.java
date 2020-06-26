package de.joachimsohn.collectivity.db;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import de.joachimsohn.collectivity.db.dao.ItemDAO;
import de.joachimsohn.collectivity.db.dao.impl.Item;

@Database(entities = Item.class, version = 1)
@TypeConverters({Converter.class})
public abstract class ItemDatabase extends RoomDatabase {

    private static ItemDatabase instance;
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    public static synchronized ItemDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ItemDatabase.class,
                    "item_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    public abstract ItemDAO itemDAO();

    //FIXME: remove this in production adds item to DB on startup
    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {

        private ItemDAO itemDAO;

        private PopulateDBAsyncTask(ItemDatabase db) {
            itemDAO = db.itemDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Bitmap bmp = Bitmap.createBitmap(250, 250, Bitmap.Config.ARGB_8888);
            /*itemDAO.insert(new Item("Farrari xyz", OffsetDateTime.now(), null, bmp, null, "50 €"));
            itemDAO.insert(new Item("Farrari test", OffsetDateTime.now(), null, bmp, null, "10 €"));
            itemDAO.insert(new Item("Farrari 1", OffsetDateTime.now(), null, bmp, null, "50 €"));
            itemDAO.insert(new Item("Farrari 2", OffsetDateTime.now(), null, bmp, null, "50 €"));*/
            return null;
        }
    }


}
