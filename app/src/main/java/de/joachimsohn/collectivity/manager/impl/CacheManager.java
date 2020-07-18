package de.joachimsohn.collectivity.manager.impl;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.db.dao.impl.Item;
import de.joachimsohn.collectivity.db.dao.impl.StorageLocation;
import de.joachimsohn.collectivity.manager.search.SearchType;
import de.joachimsohn.collectivity.util.logging.Logger;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CacheManager {

    @Setter(AccessLevel.NONE)
    private static CacheManager manager;

    static {
        manager = new CacheManager();
    }

    private LiveData<List<Collection>> collections;
    private LiveData<List<StorageLocation>> storageLocations;
    private LiveData<List<Item>> items;
    private SearchType currentCacheLevel = SearchType.COLLECTION;
    private long currentId;

    @NonNull
    public static CacheManager getManager() {
        return manager;
    }

    public void setLevel(Direction direction) {
        SearchType newCacheLevel = SearchType.COLLECTION;
        if (direction == Direction.DOWN) {
            switch (currentCacheLevel) {
                case COLLECTION:
                    newCacheLevel = SearchType.STORAGELOCATION;
                    break;
                case STORAGELOCATION:
                    newCacheLevel = SearchType.ITEM;
                    break;
                case ITEM:
                    break;
                default:
                    newCacheLevel = SearchType.COLLECTION;
                    break;
            }
        } else {
            switch (currentCacheLevel) {
                case COLLECTION:
                    break;
                case ITEM:
                    newCacheLevel = SearchType.STORAGELOCATION;
                    break;
                case STORAGELOCATION:
                default:
                    newCacheLevel = SearchType.COLLECTION;
                    break;
            }
        }
        Logger.log(Logger.Priority.DEBUG, Logger.Marker.CACHEMANAGER, String.format("Current Cachelevel is: %s, setting Cachelevel to: %s", currentCacheLevel, newCacheLevel));
        currentCacheLevel = newCacheLevel;
    }

    public enum Direction {
        UP, DOWN
    }

}
