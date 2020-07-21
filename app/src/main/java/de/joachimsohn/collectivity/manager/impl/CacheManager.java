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
    Map<CacheLevel, Long> idMapper = new HashMap<CacheLevel, Long>() {{
        put(COLLECTION, -1L);
        put(STORAGELOCATION, -1L);
        put(ITEM, -1L);
    }};

    @NonNull
    public static CacheManager getManager() {
        return manager;
    }

    @Override
    public void setCacheLevel(CacheDirection direction, int amount) {
        CacheLevel newCacheLevel = COLLECTION;
        if (direction == DOWN) {
            switch (currentCacheLevel) {
                case COLLECTION:
                    newCacheLevel = STORAGELOCATION;
                    updateStorageLocations();
                    updateTags();
                    break;
                case STORAGELOCATION:
                    newCacheLevel = ITEM;
                    if (amount == 1) {
                        updateItems();
                        updateTags();
                    }
                    break;
                case ITEM:
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
        if (amount > 1) {
            setCacheLevel(direction, amount - 1);
        }
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
        collectionCache.addSource(getInstance().getAllCollections(), collectionCache::setValue);
    }

    public void loadDataForSearch() {
        storageLocationCache.addSource(getInstance().getAllStorageLocations(), storageLocationCache::setValue);
        tags.addSource(getInstance().getAllTags(), tags::setValue);
        itemCache.addSource(getInstance().getAllItems(), itemCache::setValue);
    }

    private void updateStorageLocations() {
        if (currentCacheLevel == ITEM && idMapper.get(ITEM) != -1L) {
            long collectionId = getInstance().getCollectionIdFromItemId(idMapper.get(ITEM));
            storageLocationCache.addSource(getInstance().getAllStorageLocationsForID(collectionId), storageLocationCache::setValue);
        } else {
            storageLocationCache.addSource(getInstance().getAllStorageLocationsForID(idMapper.get(COLLECTION)), storageLocationCache::setValue);
        }
    }

    private void updateTags() {
        tags.addSource(getInstance().getAllTags(), tags::setValue);
    }

    private void updateItems() {
        itemCache.addSource(getInstance().getAllItemsForID(idMapper.get(STORAGELOCATION)), itemCache::setValue);
    }

}
