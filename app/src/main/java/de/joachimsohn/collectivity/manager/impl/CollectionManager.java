package de.joachimsohn.collectivity.manager.impl;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.impl.Collection;
import lombok.Getter;

public class CollectionManager {

    private static CollectionManager manager;

    static {
        manager = new CollectionManager();
    }

    @Getter
    private @Nullable
    LiveData<List<Collection>> collections;

    public static CollectionManager getManager() {
        return manager;
    }

}
