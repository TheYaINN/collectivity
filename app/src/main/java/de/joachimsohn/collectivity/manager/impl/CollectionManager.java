package de.joachimsohn.collectivity.manager.impl;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.UUID;

import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.loader.CollectionLoader;

public class CollectionManager {

    private static CollectionManager manager;

    @NonNull
    private List<Collection> collections = CollectionLoader.getInstance().loadCollections();

    static {
        manager = new CollectionManager();

    }

    public static CollectionManager getManager() {
        return manager;
    }

    public List<Collection> getCollection() {
        return collections;
    }

    public void setCollection(List<Collection> collections) {
        this.collections = collections;
    }
}
