package bg.uni.sofia.fmi.mjt.Homeworks.NewsFetcher.test.article;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ArticleFactoryTest {
    private static Article article;

    @BeforeAll
    static void generateInput() throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of("article.json"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
        }
        String inputString = builder.toString();
        article = ArticleFactory.of(inputString);
    }

    @Test
    void testOf() {
        assertEquals("customNews", article.getSourceName());
        assertEquals("customAuthor", article.getAuthor());
        assertEquals("customTitle", article.getTitle());
        assertEquals("customDate", article.getDate());
        assertNotNull(article.getDescription());
        assertNotNull(article.getContent());
        assertFalse(article.getDescription().isEmpty());
        assertFalse(article.getContent().isEmpty());
    }

    @Test
    void testToString() {
        String expected = "Title : %s%nDescription : %s%nAuthor : %s%nSource : %s%nPublishedAt : %s%n%s"
                .formatted("customTitle", "customDescription", "customAuthor", "customNews", "customDate", "customContent");

        assertEquals(expected, article.toString());
    }
}