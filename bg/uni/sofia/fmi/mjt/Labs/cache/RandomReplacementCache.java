package bg.uni.sofia.fmi.mjt.Labs.cache;

public class RandomReplacementCache<K,V> extends CacheAbstract<K,V> implements  Cache<K,V>{
    public RandomReplacementCache(long capacity) {
        super(capacity);
    }

    @Override
    public void removeFromCache() {
        for(K keyT : map.keySet()){
            remove(keyT);
            return;
        }
    }
}
