package bg.uni.sofia.fmi.mjt.Labs.cache;

import bg.uni.sofia.fmi.mjt.Labs.cache.enums.EvictionPolicy;

public class CacheFactory {
    private final int MAXIMUM_CAPACITY = 10_000;

    /**
     * Constructs a new Cache<K, V> with the specified maximum capacity and eviction policy
     * @throws IllegalArgumentException if the given capacity is less than or equal to zero.
     * Note that IllegalArgumentException is a `RuntimeException` from the JDK
     */
    static <K, V> Cache<K, V> getInstance(long capacity, EvictionPolicy policy){
        if(capacity <= 0){
            throw new IllegalArgumentException();
        }

        return switch (policy){
            case RANDOM_REPLACEMENT -> new RandomReplacementCache<>(capacity);
            case LEAST_FREQUENTLY_USED -> new LestFrequentlyUsedCache<>(capacity);
        };
    }

    /**
     * Constructs a new Cache<K, V> with maximum capacity of 10_000 items and the specified eviction policy
     */
    static <K, V> Cache<K, V> getInstance(EvictionPolicy policy){
        final int capacity = 10_000;

        return switch (policy){
            case RANDOM_REPLACEMENT -> new RandomReplacementCache<>(capacity);
            case LEAST_FREQUENTLY_USED -> new LestFrequentlyUsedCache<>(capacity);
        };
    }

}
