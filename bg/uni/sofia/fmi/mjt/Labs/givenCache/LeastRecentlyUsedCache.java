package bg.uni.sofia.fmi.mjt.Labs.givenCache;



import bg.uni.sofia.fmi.mjt.Labs.givenCache.storage.Storage;

import java.util.Collection;
import java.util.LinkedHashMap;

public class LeastRecentlyUsedCache<K, V> extends CacheBase<K, V> {
    private final LinkedHashMap<K, V> cache;

    public LeastRecentlyUsedCache(Storage<K, V> storage, int capacity) {
        super(storage, capacity);
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true);
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public void clear() {
        super.resetHitRate();
        cache.clear();
    }

    @Override
    public Collection<V> values() {
        return this.cache.values();
    }

    public V getFromCache(K k) {
        var val = cache.get(k);
        if (val == null) {
            return null;
        }
        return val;
    }

    public V put(K k, V v) {
        return cache.put(k, v);
    }

    public boolean containsKey(K k) {
        return cache.containsKey(k);
    }

    public void evictFromCache() {
        for (K keyT : this.cache.keySet()) {
            cache.remove(keyT);
            return;
        }
    }


}
