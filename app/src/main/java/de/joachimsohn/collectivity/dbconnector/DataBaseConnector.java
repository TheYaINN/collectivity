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
import de.joachimsohn.collectivity.db.dao.TagDAO;
import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.db.dao.impl.Item;
import de.joachimsohn.collectivity.db.dao.impl.StorageLocation;
import de.joachimsohn.collectivity.db.dao.impl.Tag;
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
    }

    public LiveData<List<Collection>> getAllCollections() {
        Logger.log(Logger.Priority.DEBUG, Logger.Marker.DB, "getting all Collections");
        try {
            return new GetAllCollectionsAsyncTask(collectionDAO).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(Collection... collections) {
        Logger.log(Logger.Priority.DEBUG, Logger.Marker.DB, "inserting into collections -> " + Arrays.toString(collections));
        try {
            return new InsertCollectionsAsyncTask(collectionDAO).execute(collections).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Collection... collections) {
        Logger.log(Logger.Priority.DEBUG, Logger.Marker.DB, "updating collections -> " + Arrays.toString(collections));
        try {
            return new UpdateCollectionsAsyncTask(collectionDAO).execute(collections).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(Collection... collections) {
        Logger.log(Logger.Priority.DEBUG, Logger.Marker.DB, "updating collections -> " + Arrays.toString(collections));
        try {
            return new DeleteCollectionsAsyncTask(collectionDAO).execute(collections).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insert(Item... items) {
        Logger.log(Logger.Priority.DEBUG, Logger.Marker.DB, "inserting into Items -> " + Arrays.toString(items));
        try {
            return new InsertItemAsyncTask(itemDAO).execute(items).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(Item... items) {
        Logger.log(Logger.Priority.DEBUG, Logger.Marker.DB, "deleting Items -> " + Arrays.toString(items));
        try {
            return new DeleteItemsAsyncTask(itemDAO).execute(items).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Item... items) {
        Logger.log(Logger.Priority.DEBUG, Logger.Marker.DB, "updating Items -> " + Arrays.toString(items));
        try {
            return new UpdateItemsAsyncTask(itemDAO).execute(items).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public LiveData<List<StorageLocation>> getAllStorageLocationsForID(Long id) {
        Logger.log(Logger.Priority.DEBUG, Logger.Marker.DB, "getting all StorageLocations for ID: -> " + id);
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

    public boolean insert(StorageLocation... storageLocation) {
        Logger.log(Logger.Priority.DEBUG, Logger.Marker.DB, "inserting into StorageLocations -> " + Arrays.toString(storageLocation));
        try {
            return new InsertStorageLocationAsyncTask(storageLocationDAO).execute(storageLocation).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public LiveData<List<Item>> getAllItemsForID(long id) {
        Logger.log(Logger.Priority.DEBUG, Logger.Marker.DB, "getting all items for ID: -> " + id);
        try {
            return new GetAllItemsForIDAsyncTask(itemDAO).execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean delete(StorageLocation... storageLocation) {
        Logger.log(Logger.Priority.DEBUG, Logger.Marker.DB, "deleting StorageLocations -> " + Arrays.toString(storageLocation));
        try {
            return new DeleteStorageLocationAsyncTask(storageLocationDAO).execute(storageLocation).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(StorageLocation... storageLocations) {
        Logger.log(Logger.Priority.DEBUG, Logger.Marker.DB, "updating Items -> " + Arrays.toString(storageLocations));
        try {
            return new UpdateStorageLocationAsyncTask(storageLocationDAO).execute(storageLocations).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public LiveData<List<Tag>> getAllTags() {
        Logger.log(Logger.Priority.DEBUG, Logger.Marker.DB, "getting all tags");
        try {
            return new GetAllTagsAsyncTask(tagDAO).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<Item>> getAllItems() {
        Logger.log(Logger.Priority.DEBUG, Logger.Marker.DB, "getting all items");
        try {
            return new GetAllItemsAsyncTask(itemDAO).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<Long> getStorageLocationIdFromItemId(long id) {
        Logger.log(Logger.Priority.DEBUG, Logger.Marker.DB, "getting parent of ID");
        try {
            return new GetStorageLocationFromItemId(itemDAO).execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long getCollectionIdFromItemId(long id) {
        try {
            return new CIdFromIId(collectionDAO).execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return -1L;
    }


    private static class UpdateCollectionsAsyncTask extends AsyncTask<Collection, Void, Boolean> {

        CollectionDAO collectionDAO;

        public UpdateCollectionsAsyncTask(CollectionDAO collectionDAO) {
            this.collectionDAO = collectionDAO;
        }

        @Override
        protected Boolean doInBackground(Collection... collections) {
            Arrays.stream(collections).forEach(collectionDAO::update);
            return true;
        }
    }

    private static class InsertCollectionsAsyncTask extends AsyncTask<Collection, Void, Boolean> {

        CollectionDAO collectionDAO;

        public InsertCollectionsAsyncTask(CollectionDAO collectionDAO) {
            this.collectionDAO = collectionDAO;
        }

        @Override
        protected Boolean doInBackground(Collection... collections) {
            Arrays.stream(collections).forEach(collectionDAO::insert);
            return true;
        }
    }

    private static class DeleteCollectionsAsyncTask extends AsyncTask<Collection, Void, Boolean> {

        CollectionDAO collectionDAO;

        public DeleteCollectionsAsyncTask(CollectionDAO collectionDAO) {
            this.collectionDAO = collectionDAO;
        }

        @Override
        protected Boolean doInBackground(Collection... collections) {
            Arrays.stream(collections).forEach(collectionDAO::delete);
            return true;
        }
    }

    private static class InsertItemAsyncTask extends AsyncTask<Item, Void, Boolean> {
        private ItemDAO itemDAO;

        public InsertItemAsyncTask(ItemDAO itemDAO) {
            this.itemDAO = itemDAO;
        }

        @Override
        protected Boolean doInBackground(Item... items) {
            Arrays.stream(items).forEach(itemDAO::insert);
            return true;
        }
    }

    private static class DeleteItemsAsyncTask extends AsyncTask<Item, Void, Boolean> {
        private ItemDAO itemDAO;

        public DeleteItemsAsyncTask(ItemDAO itemDAO) {
            this.itemDAO = itemDAO;
        }

        @Override
        protected Boolean doInBackground(Item... items) {
            Arrays.stream(items).forEach(itemDAO::delete);
            return true;
        }
    }

    private static class UpdateItemsAsyncTask extends AsyncTask<Item, Void, Boolean> {
        private ItemDAO itemDAO;

        public UpdateItemsAsyncTask(ItemDAO itemDAO) {
            this.itemDAO = itemDAO;
        }

        @Override
        protected Boolean doInBackground(Item... items) {
            Arrays.stream(items).forEach(itemDAO::update);
            return true;
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

    private static class GetAllStorageLocationsForID extends AsyncTask<Long, Void, LiveData<List<StorageLocation>>> {
        private StorageLocationDAO storageLocationDAO;

        public GetAllStorageLocationsForID(StorageLocationDAO storageLocationDAO) {
            this.storageLocationDAO = storageLocationDAO;
        }

        @Override
        protected LiveData<List<StorageLocation>> doInBackground(Long... ids) {
            if (ids.length == 1) {
                Long id = Arrays.stream(ids).collect(Collectors.toList()).get(0);
                return storageLocationDAO.getAllStorageLocationsFromCollectionId(id);
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

    private static class InsertStorageLocationAsyncTask extends AsyncTask<StorageLocation, Void, Boolean> {
        private StorageLocationDAO storageLocationDAO;

        public InsertStorageLocationAsyncTask(StorageLocationDAO storageLocationDAO) {
            this.storageLocationDAO = storageLocationDAO;
        }

        @Override
        protected Boolean doInBackground(StorageLocation... storageLocations) {
            Arrays.stream(storageLocations).forEach(storageLocationDAO::insert);
            return true;
        }
    }

    private static class GetAllItemsForIDAsyncTask extends AsyncTask<Long, Void, LiveData<List<Item>>> {
        private ItemDAO itemDAO;

        public GetAllItemsForIDAsyncTask(ItemDAO itemDAO) {
            this.itemDAO = itemDAO;
        }

        @Override
        protected LiveData<List<Item>> doInBackground(Long... ids) {
            if (ids.length == 1) {
                Long id = Arrays.stream(ids).collect(Collectors.toList()).get(0);
                return itemDAO.getItemsForId(id);
            }
            return null;
        }
    }

    private static class DeleteStorageLocationAsyncTask extends AsyncTask<StorageLocation, Void, Boolean> {

        private StorageLocationDAO storageLocationDAO;

        public DeleteStorageLocationAsyncTask(StorageLocationDAO storageLocationDAO) {
            this.storageLocationDAO = storageLocationDAO;
        }

        @Override
        protected Boolean doInBackground(StorageLocation... storageLocations) {
            Arrays.stream(storageLocations).forEach(storageLocationDAO::delete);
            return true;
        }
    }

    private static class UpdateStorageLocationAsyncTask extends AsyncTask<StorageLocation, Void, Boolean> {
        private StorageLocationDAO storageLocationDAO;

        public UpdateStorageLocationAsyncTask(StorageLocationDAO storageLocationDAO) {
            this.storageLocationDAO = storageLocationDAO;
        }

        @Override
        protected Boolean doInBackground(StorageLocation... storageLocations) {
            Arrays.stream(storageLocations).forEach(storageLocationDAO::update);
            return true;
        }
    }

    private static class GetAllTagsAsyncTask extends AsyncTask<Long, Void, LiveData<List<Tag>>> {
        private TagDAO tagDAO;

        public GetAllTagsAsyncTask(TagDAO tagDAO) {
            this.tagDAO = tagDAO;
        }

        @Override
        protected LiveData<List<Tag>> doInBackground(Long... longs) {
            return tagDAO.getAllTags();
        }
    }

    private static class GetAllItemsAsyncTask extends AsyncTask<Long, Void, LiveData<List<Item>>> {
        private ItemDAO itemDAO;

        public GetAllItemsAsyncTask(ItemDAO itemDAO) {
            this.itemDAO = itemDAO;
        }

        @Override
        protected LiveData<List<Item>> doInBackground(Long... longs) {
            return itemDAO.getAllItems();
        }
    }

    private static class GetStorageLocationFromItemId extends AsyncTask<Long, Void, LiveData<Long>> {
        private ItemDAO itemDAO;

        public GetStorageLocationFromItemId(ItemDAO itemDAO) {
            this.itemDAO = itemDAO;
        }

        @Override
        protected LiveData<Long> doInBackground(Long... ids) {
            if (ids.length == 1) {
                Long id = Arrays.stream(ids).collect(Collectors.toList()).get(0);
                return itemDAO.getStorageLocationIdFromItemId(id);
            }
            return null;
        }
    }

    private static class CIdFromIId extends AsyncTask<Long, Void, Long> {
        private CollectionDAO collectionDAO;

        public CIdFromIId(CollectionDAO collectionDAO) {
            this.collectionDAO = collectionDAO;
        }

        @Override
        protected Long doInBackground(Long... ids) {
            if (ids.length == 1) {
                Long id = Arrays.stream(ids).collect(Collectors.toList()).get(0);
                return collectionDAO.getCollectionIdFromItemId(id);
            }
            return null;
        }
    }
}

