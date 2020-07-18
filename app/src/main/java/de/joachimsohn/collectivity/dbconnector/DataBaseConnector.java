package de.joachimsohn.collectivity.dbconnector;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import de.joachimsohn.collectivity.db.AppDataBase;
import de.joachimsohn.collectivity.db.dao.CollectionDAO;
import de.joachimsohn.collectivity.db.dao.ItemDAO;
import de.joachimsohn.collectivity.db.dao.StorageLocationDAO;
import de.joachimsohn.collectivity.db.dao.StorageLocationWithItemsDAO;
import de.joachimsohn.collectivity.db.dao.TagDAO;
import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.db.dao.impl.Item;
import de.joachimsohn.collectivity.db.dao.impl.StorageLocation;
import de.joachimsohn.collectivity.db.dao.impl.StorageLocationWithItems;
import de.joachimsohn.collectivity.util.logging.Logger;

public class DataBaseConnector {

    private static @NonNull
    DataBaseConnector INSTANCE;

    static {
        INSTANCE = new DataBaseConnector();
    }


    /*   CONNECTIONS  */
    private StorageLocationDAO storageLocationDAO;
    private TagDAO tagDAO;
    private StorageLocationWithItemsDAO uiObjectDAO;
    private ItemDAO itemDAO;
    private CollectionDAO collectionDAO;

    public static DataBaseConnector getInstance() {
        return INSTANCE;
    }

    public void init(Application application) {
        AppDataBase dataBase = AppDataBase.getInstance(application);
        collectionDAO = dataBase.collectionDAO();
        itemDAO = dataBase.itemDAO();
        storageLocationDAO = dataBase.storageLocationDAO();
        tagDAO = dataBase.tagDAO();
        uiObjectDAO = dataBase.uiObjectDAO();
    }

    public LiveData<List<Collection>> getAllCollections() {
        try {
            return new GetAllCollectionsAsyncTask(collectionDAO).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(Collection... collections) {
        Logger.log(Logger.Priority.DEBUG, Logger.Marker.DB, "inserting into collections -> " + collections.toString());
        new InsertCollectionsAsyncTask(collectionDAO).execute(collections);
    }

    public void update(Collection... collections) {
        new UpdateCollectionsAsyncTask(collectionDAO).execute(collections);
    }

    public void delete(Collection... collections) {
        new DeleteCollectionsAsyncTask(collectionDAO).execute(collections);
    }

    public void insert(Item... item) {
        new InsertItemAsyncTask(itemDAO).execute(item);
    }

    public void delete(Item... items) {
        new DeleteItemsAsyncTask(itemDAO).execute(items);
    }

    public void update(Item... items) {
        new UpdateItemsAsyncTask(itemDAO).execute(items);
    }

    public LiveData<List<StorageLocation>> getAllStorageLocationsForID(Long id) {
        try {
            return new GetAllStorageLocationsForID(storageLocationDAO).execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<StorageLocation>> getAllStorageLocations() {
        try {
            return new GetAllStorageLocations(storageLocationDAO).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(StorageLocation... storageLocation) {
        new InsertStorageLocationAsyncTask(storageLocationDAO).execute(storageLocation);
    }

    public LiveData<List<Item>> getAllItemsForID(long id) {
        try {
            return new GetAllItemsAsyncTask(itemDAO).execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
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

    private static class getAllUIObjectsAsyncTask extends AsyncTask<Collection, Void, LiveData<List<StorageLocationWithItems>>> {

        private StorageLocationWithItemsDAO storageLocationsWithItemsAndTags;

        public getAllUIObjectsAsyncTask(StorageLocationWithItemsDAO uiObjectDAO) {
            this.storageLocationsWithItemsAndTags = uiObjectDAO;
        }

        @Override
        protected LiveData<List<StorageLocationWithItems>> doInBackground(Collection... collections) {
            return Arrays.stream(collections).findFirst().map(collection -> (storageLocationsWithItemsAndTags.getAllUIObjects(collection.getId()))).orElse(null);
        }
    }

    private static class GetAllStorageLocationsForID extends AsyncTask<Long, Void, LiveData<List<StorageLocation>>> {
        private StorageLocationDAO storageLocationDAO;

        public GetAllStorageLocationsForID(StorageLocationDAO storageLocationDAO) {
            this.storageLocationDAO = storageLocationDAO;
        }

        @Override
        protected LiveData<List<StorageLocation>> doInBackground(Long... ids) {
            if (ids.length == 1) {
                Long id = Arrays.stream(ids).collect(Collectors.toList()).get(0);
                return storageLocationDAO.getAllStorageLocationsForID(id);
            }
            return null;
        }
    }

    private static class GetAllStorageLocations extends AsyncTask<Void, Void, LiveData<List<StorageLocation>>> {
        private StorageLocationDAO storageLocationDAO;

        public GetAllStorageLocations(StorageLocationDAO storageLocationDAO) {
            this.storageLocationDAO = storageLocationDAO;
        }

        @Override
        protected LiveData<List<StorageLocation>> doInBackground(Void... voids) {
            return storageLocationDAO.getAllStorageLocations();
        }
    }

    private static class InsertStorageLocationAsyncTask extends AsyncTask<StorageLocation, Void, Void> {
        private StorageLocationDAO storageLocationDAO;

        public InsertStorageLocationAsyncTask(StorageLocationDAO storageLocationDAO) {
            this.storageLocationDAO = storageLocationDAO;
        }

        @Override
        protected Void doInBackground(StorageLocation... storageLocations) {
            Arrays.stream(storageLocations).forEach(storageLocationDAO::insert);
            return null;
        }
    }

    private static class GetAllItemsAsyncTask extends AsyncTask<Long, Void, LiveData<List<Item>>> {
        private ItemDAO itemDAO;

        public GetAllItemsAsyncTask(ItemDAO itemDAO) {
            this.itemDAO = itemDAO;
        }

        @Override
        protected LiveData<List<Item>> doInBackground(Long... ids) {
            if (ids.length == 1) {
                Long id = Arrays.stream(ids).collect(Collectors.toList()).get(0);
                return itemDAO.getAllItemsForID(id);
            }
            return null;
        }
    }
}

