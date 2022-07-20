package Tests.givenCache;

import bg.uni.sofia.fmi.mjt.Labs.givenCache.LeastRecentlyUsedCache;
import bg.uni.sofia.fmi.mjt.Labs.givenCache.storage.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class LeastRecentlyUsedCacheTest {

    final int CAPACITY = 5;

    @Mock
    static Storage<Integer, String> storageMock;

    @InjectMocks
    static LeastRecentlyUsedCache<Integer, String> testedCache = new LeastRecentlyUsedCache<>(storageMock, 5);

    static Collection<String> expectedValues;

    @BeforeAll
    static void fillValues() {
        expectedValues = new ArrayList<>();
        expectedValues.add("a");
        expectedValues.add("b");
        expectedValues.add("c");
        expectedValues.add("d");

    }
    @BeforeEach
     void fillCache() {
        testedCache.clear();
        testedCache.put(1, "a");
        testedCache.put(2,"b");
        testedCache.put(3,"c");
        testedCache.put(4,"d");
    }

    @Test
    void testSize() {
        assertEquals(4, testedCache.size(), "Size should be 4" );
    }

    @Test
    void testClear() {
        testedCache.clear();

        assertEquals(0, testedCache.size());
        assertEquals(0, testedCache.getHitRate());
    }

    @Test
    void testGetFromCache() {
        assertEquals("b", testedCache.getFromCache(2));
    }

    @Test
    void put() {
        testedCache.put(256, "test");

        assertEquals("test", testedCache.getFromCache(256));
    }

    @Test
    void testContainKeyTrue() {
        assertTrue(testedCache.containsKey(3));
    }

    @Test
    void testContainKeyFalse() {
        assertFalse(testedCache.containsKey(37));
    }

    @Test
    void testGetValues() {
        assertTrue(testedCache.values().containsAll(expectedValues));
    }

    @Test
    void testEvictFromCache() {
        expectedValues.remove("a");
        testedCache.evictFromCache();

        assertTrue(testedCache.values().containsAll(expectedValues));
    }

    @Test
    void testAddToCacheEvictsWithFullCache() {
        testedCache.addToCache(5,"e");
        testedCache.addToCache(6,"f");

        expectedValues.add("e");
        expectedValues.add("f");
        expectedValues.remove("a");

        assertTrue(testedCache.values().containsAll(expectedValues));
    }
}