package de.joachimsohn.collectivity.loader;

import androidx.annotation.NonNull;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.CollectionDAO;
import de.joachimsohn.collectivity.db.dao.impl.Collection;

public class CollectionLoader {

    private static CollectionLoader loader;

    static {
        loader = new CollectionLoader();
    }

    private CollectionDAO collection;

    public static CollectionLoader getInstance() {
        return loader;
    }

    public @NonNull
    List<Collection> loadAndGetCollection() {
        return collection.getAllItems();
    }

}
