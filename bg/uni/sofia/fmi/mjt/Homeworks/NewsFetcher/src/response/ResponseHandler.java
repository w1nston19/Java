package bg.uni.sofia.fmi.mjt.Homeworks.NewsFetcher.src.response;

import article.Article;

import java.util.List;

public interface ResponseHandler {

    int totalResults();

    List<Article> getArticles();


}
