package de.joachimsohn.collectivity.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.ItemDAO;
import de.joachimsohn.collectivity.db.dao.impl.Item;

public class ItemRepository {

    private ItemDAO itemDAO;
    private LiveData<List<Item>> allItems;

    public ItemRepository(Application application) {
        ItemDatabase database = ItemDatabase.getInstance(application);
        itemDAO = database.itemDAO();
        allItems = (LiveData<List<Item>>) itemDAO.getAllItems();
    }

    public void insert(Item item) {
        new InsertItemAsyncTask(itemDAO).execute(item);
    }

    public void update(Item item) {
        new UpdateItemAsyncTask(itemDAO).execute(item);
    }

    public void delete(Item item) {
        new DeleteItemAsyncTask(itemDAO).execute(item);
    }

    public void deleteAllItems() {
        new DeleteAllItemAsyncTask(itemDAO).execute();
    }

    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }

    private static class UpdateItemAsyncTask extends AsyncTask<Item, Void, Void> {

        private ItemDAO itemDAO;

        private UpdateItemAsyncTask(ItemDAO itemDAO) {
            this.itemDAO = itemDAO;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDAO.update(items[0]);
            return null;
        }
    }

    private static class InsertItemAsyncTask extends AsyncTask<Item, Void, Void> {

        private ItemDAO itemDAO;

        private InsertItemAsyncTask(ItemDAO itemDAO) {
            this.itemDAO = itemDAO;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDAO.insert(items[0]);
            return null;
        }
    }

    private static class DeleteItemAsyncTask extends AsyncTask<Item, Void, Void> {

        private ItemDAO itemDAO;

        private DeleteItemAsyncTask(ItemDAO itemDAO) {
            this.itemDAO = itemDAO;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDAO.delete(items[0]);
            return null;
        }
    }

    private static class DeleteAllItemAsyncTask extends AsyncTask<Void, Void, Void> {

        private ItemDAO itemDAO;

        private DeleteAllItemAsyncTask(ItemDAO itemDAO) {
            this.itemDAO = itemDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            itemDAO.deleteAllItems();
            return null;
        }
    }

}
