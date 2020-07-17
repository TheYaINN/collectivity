package de.joachimsohn.collectivity.dbconnector;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.joachimsohn.collectivity.ui.Marker;
import de.joachimsohn.collectivity.util.logging.Logger;
import de.joachimsohn.collectivity.db.AppDataBase;
import de.joachimsohn.collectivity.db.dao.CollectionDAO;
import de.joachimsohn.collectivity.db.dao.ItemDAO;
import de.joachimsohn.collectivity.db.dao.StorageLocationDAO;
import de.joachimsohn.collectivity.db.dao.TagDAO;
import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.db.dao.impl.Item;
import de.joachimsohn.collectivity.util.logging.Priority;

public class DataBaseConnector {

    private static @NonNull
    DataBaseConnector INSTANCE;

    static {
        INSTANCE = new DataBaseConnector();
    }

    public static DataBaseConnector getInstance() {
        return INSTANCE;
    }

    public void init(Application application) {
        AppDataBase dataBase = AppDataBase.getInstance(application);
        collectionDAO = dataBase.collectionDAO();
        itemDAO = dataBase.itemDAO();
        locationDAO = dataBase.storageLocationDAO();
        tagDAO = dataBase.tagDAO();
    }

    /*   CONNECTIONS  */

    private static CollectionDAO collectionDAO;
    private ItemDAO itemDAO;
    private StorageLocationDAO locationDAO;
    private TagDAO tagDAO;


    public LiveData<List<Collection>> getAllCollections() {
        try {
            return new GetAllCollectionsAsyncTask(collectionDAO).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(Collection... collections) {
        Logger.log(Priority.DEBUG, Marker.DB, "inserting into collections -> " + collections.toString());
        new InsertCollectionsAsyncTask(collectionDAO).execute(collections);
    }

    public void update(Collection... collections) {
        new UpdateCollectionsAsyncTask(collectionDAO).execute(collections);
    }

    public void delete(Collection... collections) {
        new DeleteCollectionsAsyncTask(collectionDAO).execute(collections);
    }

    public void deleteAll() {
        new DeleteAllCollectionsAsyncTask(collectionDAO).execute();
    }

    public LiveData<List<Item>> getAllItems() {
        return itemDAO.getAllItems();
    }

    public void insert(Item item) {
        new InsertItemAsyncTask(itemDAO).execute(item);
    }

    public void deleteAllItems() {
        new DeleteAllItemsAsyncTask(itemDAO).execute();
    }

    public void delete(Item... items) {
        new DeleteItemsAsyncTask(itemDAO).execute(items);
    }

    public void update(Item... items) {
        new UpdateItemsAsyncTask(itemDAO).execute(items);
    }

    private static class UpdateCollectionsAsyncTask extends AsyncTask<Collection, Void, Void> {

        CollectionDAO collectionDAO;

        public UpdateCollectionsAsyncTask(CollectionDAO collectionDAO) {
            this.collectionDAO = collectionDAO;
        }

        @Override
        protected Void doInBackground(Collection... collections) {
            Arrays.stream(collections).forEach(collectionDAO::update);
            return null;
        }
    }

    private static class InsertCollectionsAsyncTask extends AsyncTask<Collection, Void, Void> {

        CollectionDAO collectionDAO;

        public InsertCollectionsAsyncTask(CollectionDAO collectionDAO) {
            this.collectionDAO = collectionDAO;
        }

        @Override
        protected Void doInBackground(Collection... collections) {
            Arrays.stream(collections).forEach(collectionDAO::insert);
            return null;
        }
    }

    private static class DeleteCollectionsAsyncTask extends AsyncTask<Collection, Void, Void> {

        CollectionDAO collectionDAO;

        public DeleteCollectionsAsyncTask(CollectionDAO collectionDAO) {
            this.collectionDAO = collectionDAO;
        }

        @Override
        protected Void doInBackground(Collection... collections) {
            Arrays.stream(collections).forEach(collectionDAO::delete);
            return null;
        }
    }

    private static class DeleteAllCollectionsAsyncTask extends AsyncTask<Void, Void, Void> {

        CollectionDAO collectionDAO;

        public DeleteAllCollectionsAsyncTask(CollectionDAO collectionDAO) {
            this.collectionDAO = collectionDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            collectionDAO.deleteAllCollections();
            return null;
        }
    }

    private static class InsertItemAsyncTask extends AsyncTask<Item, Void, Void> {
        private ItemDAO itemDAO;

        public InsertItemAsyncTask(ItemDAO itemDAO) {
            this.itemDAO = itemDAO;
        }

        @Override
        protected Void doInBackground(Item... items) {
            Arrays.stream(items).forEach(itemDAO::insert);
            return null;
        }
    }

    private static class DeleteAllItemsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ItemDAO itemDAO;

        public DeleteAllItemsAsyncTask(ItemDAO itemDAO) {
            this.itemDAO = itemDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            itemDAO.deleteAllItems();
            return null;
        }
    }

    private static class DeleteItemsAsyncTask extends AsyncTask<Item, Void, Void> {
        private ItemDAO itemDAO;

        public DeleteItemsAsyncTask(ItemDAO itemDAO) {
            this.itemDAO = itemDAO;
        }

        @Override
        protected Void doInBackground(Item... items) {
            Arrays.stream(items).forEach(itemDAO::delete);
            return null;
        }
    }

    private static class UpdateItemsAsyncTask extends AsyncTask<Item, Void, Void> {
        private ItemDAO itemDAO;

        public UpdateItemsAsyncTask(ItemDAO itemDAO) {
            this.itemDAO = itemDAO;
        }

        @Override
        protected Void doInBackground(Item... items) {
            Arrays.stream(items).forEach(itemDAO::update);
            return null;
        }
    }

    private static class GetAllCollectionsAsyncTask extends AsyncTask<Void, Void, LiveData<List<Collection>>> {

        private CollectionDAO collectionDAO;

        public GetAllCollectionsAsyncTask(CollectionDAO collectionDAO) {
            this.collectionDAO = collectionDAO;
        }

        @Override
        protected LiveData<List<Collection>> doInBackground(Void... voids) {
            return collectionDAO.getAllCollections();
        }
    }
}

