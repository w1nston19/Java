package bg.uni.sofia.fmi.mjt.Homeworks.NewsFetcher.test.response;

import article.Article;
import exceptions.ServerErrorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultResponseHandlerTest {
    private static final String customResponseBody = "{\"status\":\"ok\",\"totalResults\":2,\"articles\":" +
            "[" +
            "{\"source\":{\"id\":\"id1\",\"name\":\"name1\"}," +
            "\"author\":\"author1\",\"title\":\"title1\"," +
            "\"description\":\"description1\",\"publishedAt\":\"date1\"," +
            "\"content\":\"content1\"}," +
            "{\"source\":{\"id\":\"id2\",\"name\":\"name2\"},\"author\":\"author2\",\"title\":\"title2\"," +
            "\"description\":\"description2\",\"publishedAt\":\"date2\"," +
            "\"content\":\"content2\"}]}";

    @SuppressWarnings("unchecked")
    private static final HttpResponse<String> mockRequest =
            (HttpResponse<String>) Mockito.mock(HttpResponse.class);

    private DefaultResponseHandler testedHandler;

    static {
        when(mockRequest.statusCode()).thenReturn(200);
        when(mockRequest.body()).thenReturn(customResponseBody);
    }

    {
        try {
            testedHandler = DefaultResponseHandler.of(mockRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testShouldNotThrowErrors() {
        assertDoesNotThrow(() -> DefaultResponseHandler.of(mockRequest));
    }

    @Test
    void testShouldThrowErrors() {
        when(mockRequest.statusCode()).thenReturn(500);
        when(mockRequest.body()).thenReturn("""
                {
                "status": "error",
                "code": "customCode",
                "message": "custom message"
                }""");
        assertThrows(ServerErrorException.class, () -> DefaultResponseHandler.of(mockRequest));
        when(mockRequest.statusCode()).thenReturn(200);
        when(mockRequest.body()).thenReturn(customResponseBody);
    }

    @Test
    void testTotalResults() {
        assertEquals(2, testedHandler.totalResults());
    }


    @Test
    void testArticles() {

        List<String> exp = List.of("title1", "title2");
        List<String> actual =
                testedHandler.getArticles()
                        .stream().map(Article::getTitle).toList();

        assertTrue(exp.containsAll(actual) && actual.containsAll(exp));

    }


}