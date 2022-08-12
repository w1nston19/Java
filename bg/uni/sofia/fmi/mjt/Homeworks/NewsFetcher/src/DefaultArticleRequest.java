package bg.uni.sofia.fmi.mjt.Homeworks.NewsFetcher.src;

import exceptions.WrongRequestParameterException;

import java.util.List;

public class DefaultArticleRequest implements ArticleRequest {
    List<String> keywords;

    //Optional
    private int pageSize;
    private int page;
    private String category;
    private String country;

    public DefaultArticleRequest(List<String> keywords) {
        this.keywords = keywords;

        this.category = null;
        this.country = null;

        this.page = 0;
        this.pageSize = 0;
    }


    public DefaultArticleRequest setPage(int page) throws WrongRequestParameterException {
        if (page <= MAX_PAGE) {
            this.page = page;
        } else {
            throw new WrongRequestParameterException(
                    "Entered page count is more than the maximum(%d).%n%n"
                            .formatted(MAX_PAGE));
        }
        return this;
    }

    public DefaultArticleRequest setPageSize(int pageSize) throws WrongRequestParameterException {
        if (pageSize <= MAX_PAGE_SIZE) {
            this.pageSize = pageSize;
        } else {
            throw new WrongRequestParameterException(
                    "Entered page size is more than the maximum(%d).%n%n"
                            .formatted(MAX_PAGE_SIZE));
        }
        return this;
    }


    public DefaultArticleRequest setCountry(String country) {
        this.country = country;
        return this;
    }

    public DefaultArticleRequest setCategory(String category) {
        this.category = category;
        return this;
    }


    public String build() {

        StringBuilder builder = new StringBuilder();

        String keywords = String.join("+", this.keywords);

        builder.append(KEYWORD_FORMAT.formatted(keywords));

        if (category != null) {
            builder.append(FIELDS_SEPARATOR).append("category=%s".formatted(category));
        }

        if (country != null) {
            builder.append(FIELDS_SEPARATOR).append("country=%s".formatted(country));
        }

        if (page > 0 && pageSize > 0) {
            builder.append(FIELDS_SEPARATOR).append("pageSize=%d".formatted(pageSize))
                    .append(FIELDS_SEPARATOR).append("page=%d".formatted(page));
        }

        builder.append(FIELDS_SEPARATOR).append("apiKey=%s".formatted(API_KEY));

        return builder.toString();
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPage() {
        return page;
    }

    public String getCategory() {
        return category;
    }

    public String getCountry() {
        return country;
    }
}
