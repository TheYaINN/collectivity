package de.joachimsohn.collectivity.manager.impl;

import androidx.annotation.NonNull;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.loader.CollectionLoader;

public class CollectionManager {

    private static CollectionManager manager;

    static {
        manager = new CollectionManager();

    }

    @NonNull
    private List<Collection> collections = CollectionLoader.getInstance().loadAndGetCollection();

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
