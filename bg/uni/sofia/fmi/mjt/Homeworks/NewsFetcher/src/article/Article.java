package bg.uni.sofia.fmi.mjt.Homeworks.NewsFetcher.src.article;

import com.google.gson.annotations.SerializedName;

public class Article {
    private Source source;
    private String author;
    private String title;

    private String description;
    private String content;

    private Article() {
    }

    @SerializedName("publishedAt")
    private String date;

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }


    public String getDate() {
        return date;
    }

    public String getSourceName() {
        return this.source.name;
    }

    public String toString() {
        String result = "Title : " + this.getTitle() +
                "\nDescription : " + this.getDescription() +
                "\nAuthor : " + this.getAuthor() +
                "\nSource : " + this.getSourceName() +
                "\nPublishedAt : " + this.getDate();
        if (getContent() != null) {
            result = result + "\n" + this.getContent();
        }
        return result;
    }

    private static class Source {
        String name;
    }

}
