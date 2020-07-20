package de.joachimsohn.collectivity.manager.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MediatorLiveData;

import java.util.ArrayList;
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

    private @NonNull
    SearchType currentCacheLevel = SearchType.COLLECTION;

    private @NonNull
    List<Long> currentId = new ArrayList<>();

    private @Nullable
    long parentID;

    @NonNull
    public static CacheManager getManager() {
        return manager;
    }

    public synchronized void setLevel(Direction direction, int amount) {
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
        if (amount > 1) {
            setLevel(direction, amount - 1);
        }
    }

    public long getCurrentId() {
        return currentId.get(currentId.size() - 1);
    }

    public void setCurrentId(long id) {
        if (currentId.size() > 9) {
            currentId = currentId.subList(7, 10);
        }
        Logger.log(Logger.Priority.DEBUG, Logger.Marker.CACHEMANAGER, "current id stack:" + currentId + " adding id:" + id);
        currentId.add(id);
    }

    public void parentIdOnTopOfStack() {
        currentId = currentId.subList(0, currentId.size() - 2);
    }

    public void loadCollectionsOnStartup() {
        collections.addSource(DataBaseConnector.getInstance().getAllCollections(), collections::postValue);
    }

    public void loadDataForSearch() {
        storageLocations.addSource(DataBaseConnector.getInstance().getAllStorageLocations(), storageLocations::postValue);
        tags.addSource(DataBaseConnector.getInstance().getAllTags(), tags::postValue);
        items.addSource(DataBaseConnector.getInstance().getAllItems(), items::postValue);
    }

    private void loadStorageLocations() {
        storageLocations.addSource(DataBaseConnector.getInstance().getAllStorageLocationsForID(getCurrentId()), storageLocations::postValue);
    }

    private void loadTags() {
        tags.addSource(DataBaseConnector.getInstance().getAllTagsForID(getCurrentId()), tags::postValue);
    }

    private void loadItems() {
        items.addSource(DataBaseConnector.getInstance().getAllItemsForID(getCurrentId()), items::postValue);
    }

    public void setParentId(long id, SearchType destLevel) {
        if (destLevel == SearchType.ITEM) {
            parentID = DataBaseConnector.getInstance().getStorageLocationIdFromItemId(id).getValue();
        } else {
            DataBaseConnector.getInstance().getCollectionFromStorageLocationId(id);
        }
    }

    public enum Direction {
        UP, DOWN
    }

}
