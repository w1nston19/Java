package bg.uni.sofia.fmi.mjt.Labs.cache;

import java.util.HashMap;
import java.util.Map;

public abstract class CacheAbstract<K,V> implements Cache<K,V>{
    long capacity;
    long currentItems;

    int totalHits;
    int successfulHits;
    Map<K,V> map;
    Map<K,Integer> uses;

    public CacheAbstract(long capacity) {
        this.capacity = capacity;
        this.currentItems = 0;
        this.totalHits = 0;
        this.successfulHits = 0;
        this.map = new HashMap<>();
        this.uses = new HashMap<>();
    }
    private void validateNotNull(Object o){
        if(o == null){
            throw new IllegalArgumentException();
        }
    }
    @Override
    public V get(K key) {
        validateNotNull(key);
        V result = map.get(key);
        if(result != null){
            successfulHits++;
            uses.put(key, uses.get(key) + 1);
        }
        totalHits++;

        return map.get(key);
    }

    public abstract void removeFromCache();

    @Override
    public void set(K key, V value) {
        validateNotNull(key);
        validateNotNull(value);

        if(this.currentItems >= capacity && map.get(key) == null){
            removeFromCache();
        }

        if(map.get(key) == null){
                map.put(key, value);
                uses.put(key,0);
                this.currentItems++;

        } else {
            this.map.put(key,value);
            uses.put(key, uses.get(key) + 1);
        }
    }


    @Override
    public boolean remove(K key) {
        if(this.map.get(key) != null){
            this.map.remove(key);
            return true;
        }
        return false;
    }

    @Override
    public long size() {
        return this.currentItems;
    }

    @Override
    public void clear() {
        this.map = new HashMap<>();
        this.uses = new HashMap<>();

        this.totalHits = 0;
        this.successfulHits = 0;
        this.currentItems = 0;
    }

    @Override
    public double getHitRate() {
        return totalHits != 0 ? (double) successfulHits/totalHits : 0.0;
    }

    @Override
    public int getUsesCount(K key) {
        return this.uses.get(key) == null ? 0 : uses.get(key);
    }
}
