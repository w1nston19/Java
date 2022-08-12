package bg.uni.sofia.fmi.mjt.Homeworks.NewsFetcher.src.response;

import article.Article;
import com.google.gson.Gson;

import java.net.http.HttpResponse;
import java.util.List;

public class DefaultResponseHandler implements ResponseHandler {

    private String status;
    private List<Article> articles;
    private int totalResults;

    public static DefaultResponseHandler of(HttpResponse<String> response) throws Exception {
        Gson gson = new Gson();
        var result = gson.fromJson(response.body(), DefaultResponseHandler.class);
        if (result.status.equals("error")) {
            ErrorHandler handler = ErrorHandler.of(response);
            handler.handleError();
        }
        return result;
    }

    @Override
    public int totalResults() {
        return totalResults;
    }

    @Override
    public List<Article> getArticles() {
        return articles;
    }


}
