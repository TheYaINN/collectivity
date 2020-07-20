package de.joachimsohn.collectivity.manager.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MediatorLiveData;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.db.dao.impl.Item;
import de.joachimsohn.collectivity.db.dao.impl.StorageLocation;
import de.joachimsohn.collectivity.db.dao.impl.Tag;
import de.joachimsohn.collectivity.manager.search.SearchType;
import de.joachimsohn.collectivity.util.logging.Logger;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import static de.joachimsohn.collectivity.dbconnector.DataBaseConnector.getInstance;

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

    private @NonNull
    SearchType currentCacheLevel = SearchType.COLLECTION;

    private @Nullable
    long currentCollectionId;

    private @Nullable
    long currentStorageLocationId;

    private @Nullable
    long itemId;

    @NonNull
    public static CacheManager getManager() {
        return manager;
    }

    public synchronized void setLevel(Direction direction, int amount, long id) {
        SearchType newCacheLevel = SearchType.COLLECTION;
        if (direction == Direction.DOWN) {
            switch (currentCacheLevel) {
                case COLLECTION:
                    newCacheLevel = SearchType.STORAGELOCATION;
                    currentCollectionId = id;
                    updateStorageLocations();
                    updateTags();
                    break;
                case STORAGELOCATION:
                    newCacheLevel = SearchType.ITEM;
                    currentStorageLocationId = id;
                    updateItems();
                    updateTags();
                    break;
                case ITEM:
                    itemId = id;
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
                    storageLocations = new MediatorLiveData<>();
                    updateStorageLocations();
                    updateTags();
                    break;
                case STORAGELOCATION:
                default:
                    newCacheLevel = SearchType.COLLECTION;
                    break;
            }
        }
        Logger.log(Logger.Priority.DEBUG, Logger.Marker.CACHEMANAGER, String.format("Current Cachelevel is: %s, setting Cachelevel to: %s", currentCacheLevel, newCacheLevel));
        currentCacheLevel = newCacheLevel;
        if (amount > 1) {
            setLevel(direction, amount - 1, id);
        }
    }

    public void loadCollectionsOnStartup() {
        collections.addSource(getInstance().getAllCollections(), collections::postValue);
    }

    public void loadDataForSearch() {
        storageLocations.addSource(getInstance().getAllStorageLocations(), storageLocations::postValue);
        tags.addSource(getInstance().getAllTags(), tags::postValue);
        items.addSource(getInstance().getAllItems(), items::postValue);
    }

    private void updateStorageLocations() {
        if (currentCacheLevel == SearchType.ITEM) {
            storageLocations.addSource(getInstance().getAllStorageLocationsForID(getInstance().getCollectionIdFromItemId(itemId)), storageLocations::postValue);
        } else {
            storageLocations.addSource(getInstance().getAllStorageLocationsForID(getCurrentCollectionId()), storageLocations::postValue);
        }
    }

    private void updateTags() {
        if (currentCacheLevel == SearchType.COLLECTION) {
            tags.addSource(getInstance().getAllTagsForID(getCurrentCollectionId()), tags::postValue);
        } else {
            tags.addSource(getInstance().getAllTagsForID(getCurrentStorageLocationId()), tags::postValue);
        }
    }

    private void updateItems() {
        items.addSource(getInstance().getAllItemsForID(getCurrentStorageLocationId()), items::postValue);
    }

    public enum Direction {
        UP, DOWN
    }

}
