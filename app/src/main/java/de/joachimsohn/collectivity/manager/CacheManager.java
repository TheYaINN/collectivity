package de.joachimsohn.collectivity.manager;

public interface CacheManager extends Manager<CacheManager> {

    void setCacheLevel(CacheDirection direction, long id);

    long getIdForCacheLevel(CacheLevel collection);

    void setIdForCacheLevel(CacheLevel level, long id);

    enum CacheLevel {
        COLLECTION,
        STORAGELOCATION,
        ITEM
    }

    enum CacheDirection {
        UP, DOWN
    }
}
