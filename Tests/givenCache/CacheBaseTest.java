package Tests.givenCache;

import bg.uni.sofia.fmi.mjt.Labs.givenCache.LeastRecentlyUsedCache;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class  CacheBaseTest {
    final int CAPACITY = 5;

    @Mock
    Storage<Integer, String> storageMock;

    @InjectMocks
    LeastRecentlyUsedCache<Integer, String> testedCache = new LeastRecentlyUsedCache<>(storageMock, 5);

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
    void testGetThrowsExceptionWithNullKey() {
        assertThrows(IllegalArgumentException.class, ()->testedCache.get(null));
    }

    @Test
    void testHits(){
        testedCache.clear();
        testedCache.addToCache(1,"a");

        assertEquals(0, testedCache.getHitRate());

        try {
            testedCache.get(1);
        }catch (Exception e){
            fail("Test should not throw exceptions");
        }

        assertEquals(1, testedCache.getHitRate());


        try {
            testedCache.get(2);
        }catch (Exception e){
            fail();
        }

        assertEquals(0.5, testedCache.getHitRate());
    }

    @Test
    void testSuccessFromStorage() {
        when(storageMock.retrieve(456)).thenReturn("foo");

        assertEquals("foo",storageMock.retrieve(456));
        try {
            assertEquals("foo", testedCache.get(456));
        }catch (Exception e){
            fail();
        }
        assertEquals(1, testedCache.getHitRate());
    }

    @Test
    void testSuccessfulGet() {

    }
}