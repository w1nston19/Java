package bg.uni.sofia.fmi.mjt.Homeworks.NewsFetcher.src.article;

import com.google.gson.Gson;

public class ArticleFactory {

    public static Article of(String article) {
        Gson gson = new Gson();
        return gson.fromJson(article, Article.class);
    }

}
