package bg.uni.sofia.fmi.mjt.Labs.cache;

public class LestFrequentlyUsedCache<K,V> extends CacheAbstract<K,V> implements Cache<K,V>{
    public LestFrequentlyUsedCache(long capacity) {
        super(capacity);
    }

    @Override
    public void removeFromCache() {
        int use = 1;
        K rmKey = null;

        for(K key : this.uses.keySet()){
            if(rmKey == null || uses.get(key) < use){
                use = uses.get(key);
                rmKey = key;
            }
        }

        this.uses.remove(rmKey);
        this.map.remove(rmKey);
    }
}
