package de.joachimsohn.collectivity.manager.impl;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.db.dao.impl.Item;
import de.joachimsohn.collectivity.db.dao.impl.StorageLocation;
import de.joachimsohn.collectivity.db.dao.impl.Tag;
import de.joachimsohn.collectivity.util.logging.Logger;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import static de.joachimsohn.collectivity.dbconnector.DataBaseConnector.getInstance;
import static de.joachimsohn.collectivity.manager.CacheManager.CacheDirection.DOWN;
import static de.joachimsohn.collectivity.manager.CacheManager.CacheLevel.COLLECTION;
import static de.joachimsohn.collectivity.manager.CacheManager.CacheLevel.ITEM;
import static de.joachimsohn.collectivity.manager.CacheManager.CacheLevel.STORAGELOCATION;

@Getter
public class CacheManager implements de.joachimsohn.collectivity.manager.CacheManager {

    @Setter(AccessLevel.NONE)
    private static CacheManager manager;

    static {
        manager = new CacheManager();
    }

    private @NonNull
    MediatorLiveData<List<Collection>> collectionCache = new MediatorLiveData<>();

    private @NonNull
    MediatorLiveData<List<StorageLocation>> storageLocationCache = new MediatorLiveData<>();

    private @NonNull
    MediatorLiveData<List<Tag>> tags = new MediatorLiveData<>();

    private @NonNull
    MediatorLiveData<List<Item>> itemCache = new MediatorLiveData<>();

    private @NonNull
    CacheLevel currentCacheLevel = COLLECTION;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private @NonNull
    Map<CacheLevel, Long> idMapper = new HashMap<>();

    @NonNull
    public static CacheManager getManager() {
        return manager;
    }

    @Override
    public void setCacheLevel(CacheDirection direction, long id) {
        CacheLevel newCacheLevel = COLLECTION;
        if (direction == DOWN) {
            switch (currentCacheLevel) {
                case COLLECTION:
                    newCacheLevel = STORAGELOCATION;
                    setIdForCacheLevel(COLLECTION, id);
                    updateStorageLocations();
                    updateTags();
                    break;
                case STORAGELOCATION:
                    newCacheLevel = ITEM;
                    idMapper.put(STORAGELOCATION, id);
                    updateItems();
                    updateTags();
                    break;
                case ITEM:
                    idMapper.put(ITEM, id);
                    break;
                default:
                    newCacheLevel = COLLECTION;
                    break;
            }
        } else {
            switch (currentCacheLevel) {
                case COLLECTION:
                    break;
                case ITEM:
                    newCacheLevel = STORAGELOCATION;
                    updateStorageLocations();
                    updateTags();
                    break;
                case STORAGELOCATION:
                default:
                    newCacheLevel = COLLECTION;
                    break;
            }
        }
        Logger.log(Logger.Priority.DEBUG, Logger.Marker.CACHEMANAGER, String.format("Current Cache level is: %s, setting Cache level to: %s", currentCacheLevel, newCacheLevel));
        currentCacheLevel = newCacheLevel;
    }

    @Override
    public long getIdForCacheLevel(CacheLevel level) {
        return idMapper.get(level);
    }

    @Override
    public void setIdForCacheLevel(CacheLevel level, long id) {
        idMapper.put(level, id);
    }

    public void loadCollectionsOnStartup() {
        collectionCache.addSource(getInstance().getAllCollections(), collectionCache::postValue);
    }

    public void loadDataForSearch() {
        storageLocationCache.addSource(getInstance().getAllStorageLocations(), storageLocationCache::postValue);
        tags.addSource(getInstance().getAllTags(), tags::postValue);
        itemCache.addSource(getInstance().getAllItems(), itemCache::postValue);
    }

    private void updateStorageLocations() {
        if (currentCacheLevel == ITEM) {
            storageLocationCache.addSource(getInstance().getAllStorageLocationsForID(getInstance().getCollectionIdFromItemId(idMapper.get(ITEM))), storageLocationCache::postValue);
        } else {
            storageLocationCache.addSource(getInstance().getAllStorageLocationsForID(getInstance().getCollectionIdFromItemId(idMapper.get(COLLECTION))), storageLocationCache::postValue);
        }
    }

    private void updateTags() {
        tags.addSource(getInstance().getAllTags(), tags::postValue);
    }

    private void updateItems() {
        itemCache.addSource(getInstance().getAllItemsForID(idMapper.get(STORAGELOCATION)), itemCache::postValue);
    }

}
