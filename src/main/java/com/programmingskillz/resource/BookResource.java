package com.programmingskillz.resource;

import com.programmingskillz.domain.Book;
import com.programmingskillz.service.BookService;
import com.programmingskillz.service.BookServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

/**
 * @author Durim Kryeziu
 */
@Path("books")
public class BookResource {

    private BookService bookService = new BookServiceImpl();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
        List<Book> allBooks = bookService.getAll();

        return Response.ok(new GenericEntity<List<Book>>(allBooks) {
        }).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("id") String id) {
        Book book = bookService.get(id);

        return Response.ok(book).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(@Context UriInfo uriInfo, Book book) {
        Book savedBook = bookService.add(book);
        URI createdUri = uriInfo.getRequestUriBuilder().path(savedBook.getId()).build();
        return Response.created(createdUri).entity(savedBook).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(Book book) {
        Book updatedBook = bookService.update(book);
        return Response.ok(updatedBook).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteBook(@PathParam("id") String id) {
        bookService.delete(id);
        return Response.noContent().build();
    }

    @DELETE
    public Response deleteBooks() {
        bookService.deleteAll();
        return Response.noContent().build();
    }
}