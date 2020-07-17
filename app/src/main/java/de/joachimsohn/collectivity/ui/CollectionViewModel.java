package de.joachimsohn.collectivity.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.dbconnector.DataBaseConnector;

public class CollectionViewModel extends AndroidViewModel {

    private ExecutorService service;

    public CollectionViewModel(@NonNull Application application) {
        super(application);
        service = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Collection>> getAllCollections() {
        return DataBaseConnector.getInstance().getAllCollections();
    }

    public void saveCollection(Collection collection) {
        service.execute(() -> DataBaseConnector.getInstance().insert(collection));
    }

    public void deleteCollection(Collection collection) {
        service.execute(() -> DataBaseConnector.getInstance().delete(collection));
    }

}
