package de.joachimsohn.collectivity.loader;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.Arrays;
import java.util.List;

import de.joachimsohn.collectivity.db.AppDataBase;
import de.joachimsohn.collectivity.db.dao.CollectionDAO;
import de.joachimsohn.collectivity.db.dao.impl.Collection;

public class DataBaseConnector {

    private static @NonNull
    DataBaseConnector connector;

    private CollectionDAO collectionDAO;

    static {
        connector = new DataBaseConnector();
    }

    public static DataBaseConnector getInstance() {
        return connector;
    }

    public LiveData<List<Collection>> loadAndGetCollection(Application application) {
        AppDataBase dataBase = AppDataBase.getInstance(application);
        collectionDAO = dataBase.collectionDAO();
        return collectionDAO.getAllCollections();
    }

    public void insert(Collection collection) {
        new InsertCollectionsAsyncTask(collectionDAO).execute(collection);
    }

    public void update(Collection collection) {
        new UpdateCollectionsAsyncTask(collectionDAO).execute(collection);
    }

    public void delete(Collection collection) {
        new DeleteCollectionsAsyncTask(collectionDAO).execute(collection);
    }

    public void deleteAllItems() {
        new DeleteAllCollectionsAsyncTask(collectionDAO).execute();
    }


    private static class UpdateCollectionsAsyncTask extends AsyncTask<Collection, Void, Void> {

        CollectionDAO collectionDAO;

        public UpdateCollectionsAsyncTask(CollectionDAO collectionDAO) {
            this.collectionDAO = collectionDAO;
        }

        @Override
        protected Void doInBackground(Collection... collections) {
            Arrays.stream(collections).forEach(c -> collectionDAO.update(c));
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
            Arrays.stream(collections).forEach(c -> collectionDAO.insert(c));
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
            Arrays.stream(collections).forEach(c -> collectionDAO.delete(c));
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

}

