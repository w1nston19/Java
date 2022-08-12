package bg.uni.sofia.fmi.mjt.Homeworks.NewsFetcher.src;

import article.Article;
import response.DefaultResponseHandler;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArticleFetcher {
    private static final String HOST = "https://newsapi.org/";
    private static final String END_POINT = "v2/top-headlines";
    private static final String URI_FORMAT = "%s%s?%s";
    private static final ExecutorService EXECUTOR;
    private static HttpClient client;

    static {
        EXECUTOR = Executors.newCachedThreadPool();
        client = HttpClient.newBuilder().executor(EXECUTOR).build();
    }

    public ArticleFetcher() {
    }

    public List<Article> fetch(String queryString) throws Exception {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(getUri(queryString)).build();
        CompletableFuture<HttpResponse<String>> future =
                client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

        future.join();
        DefaultResponseHandler responseHandler = DefaultResponseHandler.of(future.get());
        return responseHandler.getArticles();
    }


    private static URI getUri(String queryString) {
        return URI.create(URI_FORMAT.formatted(HOST, END_POINT, queryString));
    }

    public void clean() {
        EXECUTOR.shutdown();
    }

    public void setCustomClient(HttpClient customClient) {
        client = customClient;
    }
}
