package com.programmingskillz.resource;

import com.programmingskillz.constraint.ValidBookToUpdate;
import com.programmingskillz.domain.Book;
import com.programmingskillz.service.BookService;
import com.programmingskillz.service.BookServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Durim Kryeziu
 */
@Path("books")
public class BookResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookResource.class);

    private BookService bookService = new BookServiceImpl();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() throws SQLException {

        LOGGER.debug("Getting all books...");
        List<Book> allBooks = bookService.getAll();

        return Response.ok(new GenericEntity<List<Book>>(allBooks) {
        }).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("id") String id) throws SQLException {

        LOGGER.debug("Getting book with id '{}'", id);
        Book book = bookService.get(id);

        return Response.ok(book).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(@Context UriInfo uriInfo,
                               @NotNull(message = "{requestBody.does.not.exist}")
                               @Valid Book book) throws SQLException {

        LOGGER.debug("Inserting book {}", book);
        Book savedBook = bookService.add(book);

        URI createdUri = uriInfo.getRequestUriBuilder().path(savedBook.getId()).build();
        return Response.created(createdUri).entity(savedBook).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@NotNull(message = "{requestBody.does.not.exist}")
                               @ValidBookToUpdate
                               @Valid Book book) throws SQLException {

        LOGGER.debug("Updating book {}", book);
        Book updatedBook = bookService.update(book);

        return Response.ok(updatedBook).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteBook(@PathParam("id") String id) throws SQLException {

        LOGGER.debug("Deleting book with id '{}'", id);
        bookService.delete(id);

        return Response.noContent().build();
    }

    @DELETE
    public Response deleteBooks() throws SQLException {

        LOGGER.debug("Deleting all books");
        bookService.deleteAll();

        return Response.noContent().build();
    }
}