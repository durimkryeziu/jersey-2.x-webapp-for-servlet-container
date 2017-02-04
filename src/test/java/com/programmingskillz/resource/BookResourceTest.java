package com.programmingskillz.resource;

import com.programmingskillz.SampleApplication;
import com.programmingskillz.domain.Book;
import com.programmingskillz.providers.SampleObjectMapperProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Durim Kryeziu
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookResourceTest extends JerseyTest {

    private String bookId;

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new SampleApplication();
    }

    @Override
    protected void configureClient(ClientConfig config) {
        config.register(SampleObjectMapperProvider.class);
        config.register(JacksonFeature.class);
        config.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_CLIENT, "INFO");
        config.property(LoggingFeature.LOGGING_FEATURE_VERBOSITY_CLIENT, LoggingFeature.Verbosity.PAYLOAD_ANY);
    }

    @Before
    public void addBook() {
        Book book = new Book();
        book.setTitle("Before Book's title");
        book.setAuthor("Before Book's author");
        book.setIsbn("123-1234-2333");
        book.setPages(123);

        Entity<Book> bookEntity = Entity.entity(book, MediaType.APPLICATION_JSON);
        Response response = target("books")
                .request(MediaType.APPLICATION_JSON)
                .post(bookEntity);

        Book bookResponse = response.readEntity(Book.class);
        this.bookId = bookResponse.getId();
    }

    @Test
    public void test1AddBookWithNecessaryFields() {
        Book book = new Book();
        book.setTitle("This is Book's title");
        book.setAuthor("This is Book's author");
        book.setIsbn("123-1234-2333");
        book.setPages(123);

        Entity<Book> bookEntity = Entity.entity(book, MediaType.APPLICATION_JSON);
        Response response = target("books")
                .request(MediaType.APPLICATION_JSON)
                .post(bookEntity);

        assertEquals(201, response.getStatus());
        assertNotNull(response.getHeaderString("Location"));

        Book bookResponse = response.readEntity(Book.class);

        assertEquals("This is Book's title", bookResponse.getTitle());
        assertEquals("This is Book's author", bookResponse.getAuthor());
        assertEquals("123-1234-2333", bookResponse.getIsbn());
        assertEquals(123, bookResponse.getPages().intValue());
    }

    @Test
    public void test2AddBookFull() {
        Book book = new Book();
        book.setTitle("Full Book's title");
        book.setAuthor("Full Book's author");
        book.setDescription("Full Book's description");
        book.setIsbn("12-13-12-1322");
        book.setPages(254);
        book.setPublisher("Wox");

        Instant date = Instant.now();
        book.setPublished(date);

        Entity<Book> bookEntity = Entity.entity(book, MediaType.APPLICATION_JSON);
        Response response = target("books")
                .request(MediaType.APPLICATION_JSON)
                .post(bookEntity);

        assertEquals(201, response.getStatus());
        assertNotNull(response.getHeaderString("Location"));

        Book bookResponse = response.readEntity(Book.class);

        assertEquals("Full Book's title", bookResponse.getTitle());
        assertEquals("Full Book's author", bookResponse.getAuthor());
        assertEquals("Full Book's description", bookResponse.getDescription());
        assertEquals("12-13-12-1322", bookResponse.getIsbn());
        assertEquals(254, bookResponse.getPages().intValue());
        assertEquals("Wox", bookResponse.getPublisher());
        assertEquals(date, bookResponse.getPublished());
    }

    @Test
    public void test3GetOneBook() {
        Response response = target("books")
                .path(bookId)
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(200, response.getStatus());

        Book book = response.readEntity(Book.class);

        assertNotNull(book);
        assertEquals(bookId, book.getId());
    }

    @Test
    public void test4GetAllBooks() {
        Response response = target("books")
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(200, response.getStatus());

        List<Book> books = response.readEntity(new GenericType<List<Book>>() {
        });

        assertNotNull(books);
        assertTrue(books.size() > 0);
        assertTrue(books.stream().anyMatch(b -> b.getId().equals(bookId)));
    }

    @Test
    public void test5UpdateBook() {
        Book book = target("books")
                .path(bookId)
                .request(MediaType.APPLICATION_JSON)
                .get(Book.class);
        book.setDescription("Description is updated");

        Response response = target("books")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(book, MediaType.APPLICATION_JSON));

        assertEquals(200, response.getStatus());

        Book updatedBook = response.readEntity(Book.class);

        assertEquals(book, updatedBook);
    }

    @Test
    public void test6DeleteOneBook() {
        Response response = target("books")
                .path(bookId)
                .request()
                .delete();

        assertEquals(204, response.getStatus());

        Response notFoundResponse = target("books")
                .path(bookId)
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertEquals(404, notFoundResponse.getStatus());
    }

    @Test
    public void test7DeleteAllBooks() {
        Response response = target("books")
                .request()
                .delete();

        assertEquals(204, response.getStatus());

        List<Book> books = target("books")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Book>>() {
                });

        assertNotNull(books);
        assertTrue(books.size() == 0);
    }
}