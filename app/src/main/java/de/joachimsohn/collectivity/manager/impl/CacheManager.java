package de.joachimsohn.collectivity.manager.impl;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.db.dao.impl.Item;
import de.joachimsohn.collectivity.db.dao.impl.StorageLocation;
import de.joachimsohn.collectivity.db.dao.impl.Tag;
import de.joachimsohn.collectivity.dbconnector.DataBaseConnector;
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

    private @NonNull
    MediatorLiveData<List<Collection>> collections = new MediatorLiveData<>();

    private @NonNull
    MediatorLiveData<List<StorageLocation>> storageLocations = new MediatorLiveData<>();

    private @NonNull
    MediatorLiveData<List<Tag>> tags = new MediatorLiveData<>();

    private @NonNull
    MediatorLiveData<List<Item>> items = new MediatorLiveData<>();

    @NonNull
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
                    loadStorageLocations();
                    loadTags();
                    break;
                case STORAGELOCATION:
                    newCacheLevel = SearchType.ITEM;
                    loadItems();
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
                    items = new MediatorLiveData<>();
                    break;
                case STORAGELOCATION:
                default:
                    newCacheLevel = SearchType.COLLECTION;
                    storageLocations = new MediatorLiveData<>();
                    break;
            }
        }
        Logger.log(Logger.Priority.DEBUG, Logger.Marker.CACHEMANAGER, String.format("Current Cachelevel is: %s, setting Cachelevel to: %s", currentCacheLevel, newCacheLevel));
        currentCacheLevel = newCacheLevel;
    }

    private void loadTags() {
        tags.addSource(DataBaseConnector.getInstance().getAllTagsForID(getCurrentId()), tags::postValue);
    }

    public void loadCollectionsOnStartup() {
        collections.addSource(DataBaseConnector.getInstance().getAllCollections(), collections::postValue);
    }

    private void loadStorageLocations() {
        storageLocations.addSource(DataBaseConnector.getInstance().getAllStorageLocationsForID(getCurrentId()), storageLocations::postValue);
    }

    private void loadItems() {
        items.addSource(DataBaseConnector.getInstance().getAllItemsForID(getCurrentId()), items::postValue);
    }

    public enum Direction {
        UP, DOWN
    }

}
