package de.joachimsohn.collectivity.manager.impl;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.loader.DataBaseConnector;

public class CollectionManager {

    private static CollectionManager manager;

    static {
        manager = new CollectionManager();
    }

    private @Nullable
    LiveData<List<Collection>> collections;

    public static CollectionManager getManager() {
        return manager;
    }

    public LiveData<List<Collection>> getCollection() {
        return collections;
    }

    public void setCollection(LiveData<List<Collection>> collections) {
        this.collections = collections;
    }

    public void saveUserState() {

    }
}
