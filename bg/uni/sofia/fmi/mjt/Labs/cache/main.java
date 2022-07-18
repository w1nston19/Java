package bg.uni.sofia.fmi.mjt.Labs.cache;

import bg.uni.sofia.fmi.mjt.Labs.cache.CacheFactory;
import bg.uni.sofia.fmi.mjt.Labs.cache.RandomReplacementCache;
import bg.uni.sofia.fmi.mjt.Labs.cache.enums.EvictionPolicy;

public class main {
    public static void main(String[] args) {
        Cache<Integer, String> rr = CacheFactory.getInstance(5, EvictionPolicy.RANDOM_REPLACEMENT);
        Cache<Integer, String> lfu = CacheFactory.getInstance(5, EvictionPolicy.LEAST_FREQUENTLY_USED);


        rr.set(1, "a");
        rr.set(2, "b");
        rr.set(3, "c");
        rr.set(4, "d");
        rr.set(5, "e");
        rr.set(6, "f");
        rr.set(7, "f");

        rr.get(5);
        rr.get(5);
        rr.get(5);

        lfu.set(1, "a");
        lfu.set(2, "b");
        lfu.set(3, "c");
        lfu.set(4, "d");
        lfu.set(5, "e");

        lfu.get(1);
        lfu.get(1);
        lfu.get(1);
        lfu.get(2);
        lfu.get(2);
        lfu.get(3);
        lfu.get(4);
        lfu.get(4);
        lfu.get(5);
        lfu.get(5);
        lfu.get(5);

        lfu.set(6, "k");
        lfu.set(7, "l");

        System.out.println("Random cache : ");
        for (int i = 1; i <= 7; i++) {
            System.out.println(rr.get(i));
        }
        System.out.println("Success hitRate : " + rr.getHitRate());

        System.out.println("LFU cache : ");
        for (int i = 1; i <= 7; i++) {
            System.out.println(lfu.get(i));
        }
        System.out.println("Success hitRate : " + rr.getHitRate());

    }
}
