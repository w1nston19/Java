package Tests.givenCache;

import bg.uni.sofia.fmi.mjt.Labs.givenCache.LeastFrequentlyUsedCache;
import bg.uni.sofia.fmi.mjt.Labs.givenCache.exception.ItemNotFound;
import bg.uni.sofia.fmi.mjt.Labs.givenCache.storage.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LeastFrequentlyUsedCacheTest {

    final int CAPACITY = 5;

    @Mock
    static Storage<Integer, String> storageMock;

    @InjectMocks
    static LeastFrequentlyUsedCache<Integer, String> testedCache = new LeastFrequentlyUsedCache<>(storageMock, 5);

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

    void fillUses() throws ItemNotFound {
        testedCache.get(1);
        testedCache.get(1);
        testedCache.get(2);
        testedCache.get(2);
        testedCache.get(2);
        testedCache.get(3);
        testedCache.get(4);
        testedCache.get(4);
    }

    @Test
    void testSize(){
        assertEquals(4, testedCache.size());
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
    void testGetFromCacheWithNoExistingKey() {
        assertNull(testedCache.getFromCache(256));
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

        assertTrue(testedCache.values().containsAll(expectedValues));

        try{
            fillUses();

        }catch (Exception e){
            fail();
        }
        testedCache.evictFromCache();

        expectedValues.remove("c");

        assertTrue(testedCache.values().containsAll(expectedValues));
    }

}