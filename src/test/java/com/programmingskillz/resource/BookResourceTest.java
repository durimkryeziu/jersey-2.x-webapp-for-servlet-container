package com.programmingskillz.resource;

import com.programmingskillz.SampleApplication;
import com.programmingskillz.domain.Book;
import com.programmingskillz.providers.SampleObjectMapperProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.message.GZipEncoder;
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
        return new SampleApplication();
    }

    @Override
    protected void configureClient(ClientConfig config) {
        config.register(SampleObjectMapperProvider.class);
        config.register(JacksonFeature.class);
        config.register(GZipEncoder.class);
        config.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_CLIENT, "INFO");
        config.property(LoggingFeature.LOGGING_FEATURE_VERBOSITY_CLIENT, LoggingFeature.Verbosity.HEADERS_ONLY);
    }

    @Before
    public void addBook() {
        Book book = new Book();
        book.setTitle("How to Win Friends & Influence People");
        book.setAuthor("Dale Carnegie");
        book.setIsbn("067142517X");
        book.setPages(299);
        book.setPublished(Instant.now());

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
        book.setTitle("How to Win Friends & Influence People");
        book.setAuthor("Dale Carnegie");
        book.setIsbn("067142517X");
        book.setPages(299);

        Entity<Book> bookEntity = Entity.entity(book, MediaType.APPLICATION_JSON);
        Response response = target("books")
                .request(MediaType.APPLICATION_JSON)
                .post(bookEntity);

        assertEquals(201, response.getStatus());
        assertNotNull(response.getHeaderString("Location"));

        Book bookResponse = response.readEntity(Book.class);

        assertEquals("How to Win Friends & Influence People", bookResponse.getTitle());
        assertEquals("Dale Carnegie", bookResponse.getAuthor());
        assertEquals("067142517X", bookResponse.getIsbn());
        assertEquals(299, bookResponse.getPages().intValue());
    }

    @Test
    public void test2AddBookFull() {
        Book book = new Book();
        book.setTitle("The Clean Coder: A Code of Conduct for Professional Programmers");
        book.setAuthor("Robert C. Martin");
        book.setDescription("In The Clean Coder: A Code of Conduct for Professional Programmers, " +
                "legendary software expert Robert C. Martin introduces the disciplines, techniques, " +
                "tools, and practices of true software craftsmanship. This book is packed with " +
                "practical advice–about everything from estimating and coding to refactoring and " +
                "testing.");
        book.setIsbn("9780137081073");
        book.setPages(256);
        book.setPublisher("Prentice Hall");

        Instant date = Instant.parse("2011-05-23T00:00:00.00Z");
        book.setPublished(date);

        Entity<Book> bookEntity = Entity.entity(book, MediaType.APPLICATION_JSON);
        Response response = target("books")
                .request(MediaType.APPLICATION_JSON)
                .post(bookEntity);

        assertEquals(201, response.getStatus());
        assertNotNull(response.getHeaderString("Location"));

        Book bookResponse = response.readEntity(Book.class);

        assertEquals("The Clean Coder: A Code of Conduct for Professional Programmers", bookResponse.getTitle());
        assertEquals("Robert C. Martin", bookResponse.getAuthor());
        assertEquals("In The Clean Coder: A Code of Conduct for Professional Programmers, " +
                "legendary software expert Robert C. Martin introduces the disciplines, techniques, " +
                "tools, and practices of true software craftsmanship. This book is packed with " +
                "practical advice–about everything from estimating and coding to refactoring and " +
                "testing.", bookResponse.getDescription());
        assertEquals("9780137081073", bookResponse.getIsbn());
        assertEquals(256, bookResponse.getPages().intValue());
        assertEquals("Prentice Hall", bookResponse.getPublisher());
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

    @Test
    public void test8RequestBodyNull() {
        Response response = target("books")
                .request(MediaType.APPLICATION_JSON)
                .post(null);

        assertEquals(400, response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaderString("Content-Type"));

        String entity = response.readEntity(String.class);

        assertTrue(entity.contains("Request body does not exist."));
    }

    @Test
    public void test9UpdateWithoutId() {
        Book book = new Book();
        book.setTitle("Effective Java (2nd Edition)");
        book.setAuthor("Joshua Bloch");
        book.setIsbn("9780321356680");
        book.setPages(346);

        Entity<Book> bookEntity = Entity.entity(book, MediaType.APPLICATION_JSON);

        Response response = target("books")
                .request(MediaType.APPLICATION_JSON)
                .put(bookEntity);

        assertEquals(400, response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaderString("Content-Type"));

        String entity = response.readEntity(String.class);

        assertTrue(entity.contains("Id cannot be null when you want to update the Book"));
    }

    @Test
    public void test10UriBasedContentNegotiation() {
        Response jsonResponse = target("books").path(".json").request().get();

        assertEquals(MediaType.APPLICATION_JSON, jsonResponse.getHeaderString("Content-Type"));

        Response xmlResponse = target("books").path(".xml").request().get();

        assertEquals(MediaType.APPLICATION_XML, xmlResponse.getHeaderString("Content-Type"));
    }

    @Test
    public void test11PoweredByHeader() {
        Response response = target("books").request(MediaType.APPLICATION_JSON).get();

        assertNotNull(response.getHeaderString("X-Powered-By"));
        assertEquals("Jersey Framework", response.getHeaderString("X-Powered-By"));
    }
}