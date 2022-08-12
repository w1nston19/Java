package bg.uni.sofia.fmi.mjt.Homeworks.NewsFetcher.src;

import exceptions.WrongRequestParameterException;

import java.util.List;

public interface ArticleRequest {
    String API_KEY = "09c9dcc966e4469caf2257a30bc98436";
    char FIELDS_SEPARATOR = '&';
    String KEYWORD_FORMAT = "q=%s";
    int MAX_PAGE_SIZE = 50;
    int MAX_PAGE = 3;

    List<String> getKeywords();

    int getPageSize();

    int getPage();

    String getCategory();

    String getCountry();

    ArticleRequest setPage(int page) throws WrongRequestParameterException;

    ArticleRequest setPageSize(int pageSize) throws WrongRequestParameterException;

    ArticleRequest setCountry(String country);

    ArticleRequest setCategory(String category);

    String build();
}
