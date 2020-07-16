package de.joachimsohn.collectivity.manager.impl;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import de.joachimsohn.collectivity.db.dao.impl.Collection;

public class CollectionManager {

    private static CollectionManager manager;

    static {
        manager = new CollectionManager();
    }

    private @Nullable LiveData<Collection> collections;

    public static CollectionManager getManager() {
        return manager;
    }

    public LiveData<Collection> getCollection() {
        return collections;
    }

    public void setCollection(LiveData<Collection> collections) {
        this.collections = collections;
    }

    public void saveUserState() {

    }
}
