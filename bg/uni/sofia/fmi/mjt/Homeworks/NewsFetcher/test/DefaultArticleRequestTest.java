package bg.uni.sofia.fmi.mjt.Homeworks.NewsFetcher.test;

import exceptions.WrongRequestParameterException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DefaultArticleRequestTest {
    private static final String API_KEY = "09c9dcc966e4469caf2257a30bc98436";

    List<String> expectedKeywords;
    ArticleRequest testedArticleRequest;

    public DefaultArticleRequestTest() {
        expectedKeywords = List.of("social", "media");
        testedArticleRequest = new DefaultArticleRequest(expectedKeywords);
    }


    @Test
    void testWithOnlyKeywords() {
        String expected = "q=social+media&apiKey=%s".formatted(API_KEY);

        assertTrue(expectedKeywords.containsAll(testedArticleRequest.getKeywords())
                && testedArticleRequest.getKeywords().containsAll(expectedKeywords));

        assertEquals(expected, testedArticleRequest.build());
    }

    @Test
    void testWithCustomPageAndPageCount() {
        try {
            String expected = "q=social+media&pageSize=10&page=2&apiKey=%s".formatted(API_KEY);
            testedArticleRequest.setPage(2).setPageSize(10);

            assertEquals(2, testedArticleRequest.getPage());
            assertEquals(10, testedArticleRequest.getPageSize());

            assertEquals(expected, testedArticleRequest.build());

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testWithCategory() {
        testedArticleRequest.setCategory("Technology");

        assertEquals("Technology", testedArticleRequest.getCategory());
    }


    @Test
    void testWithCountry() {
        testedArticleRequest.setCountry("us");

        assertEquals("us", testedArticleRequest.getCountry());
    }

    @Test
    void testShouldThrowErrors() {
        assertThrows(WrongRequestParameterException.class,
                () -> testedArticleRequest.setPage(5));

        assertThrows(WrongRequestParameterException.class,
                () -> testedArticleRequest.setPageSize(51));
    }

    @Test
    void testCorrectString() {
        try {
            String expected = "q=social+media&category=Technology&country=us&pageSize=10&page=2&apiKey=%s".formatted(API_KEY);

            String actual = testedArticleRequest.setPageSize(10).setPage(2)
                    .setCountry("us").setCategory("Technology")
                    .build();
            assertEquals(expected, actual);
        } catch (WrongRequestParameterException e) {
            fail();
        }
    }
}