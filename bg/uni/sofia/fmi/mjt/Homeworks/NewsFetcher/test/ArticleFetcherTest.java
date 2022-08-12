package bg.uni.sofia.fmi.mjt.Homeworks.NewsFetcher.test;

import article.Article;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleFetcherTest {
    private static final String customResponseBody = "{\"status\":\"ok\",\"totalResults\":2,\"articles\":" +
            "[" +
            "{\"source\":{\"id\":\"id1\",\"name\":\"name1\"}," +
            "\"author\":\"author1\",\"title\":\"title1\"," +
            "\"description\":\"description1\",\"publishedAt\":\"date1\"," +
            "\"content\":\"content1\"}," +
            "{\"source\":{\"id\":\"id2\",\"name\":\"name2\"},\"author\":\"author2\",\"title\":\"title2\"," +
            "\"description\":\"description2\",\"publishedAt\":\"date2\"," +
            "\"content\":\"content2\"}]}";
    private static final HttpClient client = Mockito.mock(HttpClient.class);


    private static final CompletableFuture<HttpResponse<String>> response = Mockito.mock(CompletableFuture.class);
    private static ArticleFetcher testedFetcher;


    static {
        testedFetcher = new ArticleFetcher();
        testedFetcher.setCustomClient(client);

        when(response.join()).thenReturn(null);
        try {
            when(response.get()).thenReturn(Mockito.mock(HttpResponse.class));
            when(response.get().statusCode()).thenReturn(200);
            when(response.get().body()).thenReturn(customResponseBody);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    @AfterAll
    static void cleanUp() {
        testedFetcher.clean();
    }

    @Test
    void testFetch() {

        when(client.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(response);


        List<String> exp = List.of("title1", "title2");
        List<String> actual = new ArrayList<>();

        try {
            actual = testedFetcher.fetch("https://newsapi.org/docs/endpoints/top-headlines").stream().map(Article::getTitle).toList();

        } catch (Exception e) {
            fail();
        }
        assertTrue(exp.containsAll(actual) && actual.containsAll(exp));
    }

}