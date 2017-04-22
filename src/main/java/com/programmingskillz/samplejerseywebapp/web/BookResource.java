package com.programmingskillz.samplejerseywebapp.web;

import com.programmingskillz.samplejerseywebapp.business.constraint.ValidBookToUpdate;
import com.programmingskillz.samplejerseywebapp.business.domain.Book;
import com.programmingskillz.samplejerseywebapp.business.exception.ErrorResponse;
import com.programmingskillz.samplejerseywebapp.business.service.BookService;
import com.programmingskillz.samplejerseywebapp.config.providers.Compress;
import com.programmingskillz.samplejerseywebapp.util.DependenciesFactory;
import io.swagger.annotations.*;
import org.glassfish.jersey.server.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import static com.programmingskillz.samplejerseywebapp.util.CustomMediaType.APPLICATION_JSON;
import static com.programmingskillz.samplejerseywebapp.util.CustomMediaType.APPLICATION_XML;

/**
 * @author Durim Kryeziu
 */
@Path("books")
@Api("books")
public class BookResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookResource.class);

    private BookService bookService;

    public BookResource() {
        bookService = DependenciesFactory.getBookService();
    }

    @GET
    @Compress
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @ApiOperation(
            value = "Find all books",
            response = Book.class,
            responseContainer = "List"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Successful retrieval of books",
                    response = Book.class,
                    responseContainer = "List"
            ),
            @ApiResponse(
                    code = 401,
                    message = "'Authorization' header is missing or wrong username/password",
                    response = ErrorResponse.class,
                    responseHeaders = @ResponseHeader(
                            name = "WWW-Authenticate",
                            description = "Defines the authentication method that should be used to gain access to a resource.",
                            response = String.class
                    )
            ),
            @ApiResponse(
                    code = 404,
                    message = "Invalid request URL.",
                    response = ErrorResponse.class
            ),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error",
                    response = ErrorResponse.class
            )
    })
    public Response getBooks() throws SQLException {

        LOGGER.debug("Getting all books...");
        List<Book> allBooks = bookService.getAll();

        return Response.ok(new GenericEntity<List<Book>>(allBooks) {
        }).build();
    }

    @GET
    @Compress
    @Path("{id}")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @ApiOperation(
            value = "Find book by id",
            response = Book.class
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "OK",
                    response = Book.class
            ),
            @ApiResponse(
                    code = 401,
                    message = "'Authorization' header is missing or wrong username/password",
                    response = ErrorResponse.class,
                    responseHeaders = @ResponseHeader(
                            name = "WWW-Authenticate",
                            description = "Defines the authentication method that should be used to gain access to a resource.",
                            response = String.class
                    )
            ),
            @ApiResponse(
                    code = 404,
                    message = "Book with such id not found.",
                    response = ErrorResponse.class
            ),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error",
                    response = ErrorResponse.class
            )
    })
    public Response getBook(
            @ApiParam(
                    value = "Id of the Book you want to retrieve",
                    required = true,
                    example = "767a463c-4cc3-48c1-b93e-25c0d216032b"
            )
            @PathParam("id") String id) throws SQLException {

        LOGGER.debug("Getting book with id '{}'", id);
        Book book = bookService.get(id);

        return Response.ok(book).build();
    }

    @POST
    @Compress
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @ApiOperation(
            code = 201,
            value = "Add a new book",
            response = Book.class
    )
    @ApiResponses({
            @ApiResponse(
                    code = 201,
                    message = "Book created successfully",
                    response = Book.class,
                    responseHeaders = @ResponseHeader(
                            name = "Location",
                            description = "URL of the newly created book",
                            response = String.class
                    )
            ),
            @ApiResponse(
                    code = 400,
                    message = "Bad request",
                    response = ValidationError.class
            ),
            @ApiResponse(
                    code = 401,
                    message = "'Authorization' header is missing or wrong username/password",
                    response = ErrorResponse.class,
                    responseHeaders = @ResponseHeader(
                            name = "WWW-Authenticate",
                            description = "Defines the authentication method that should be used to gain access to a resource.",
                            response = String.class
                    )
            ),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error",
                    response = ErrorResponse.class
            )
    })
    public Response createBook(@Context UriInfo uriInfo,
                               @ApiParam(
                                       value = "Book object you want to add",
                                       required = true
                               )
                               @NotNull(message = "{requestBody.does.not.exist}")
                               @Valid Book book) throws SQLException {

        LOGGER.debug("Inserting book {}", book);
        Book savedBook = bookService.add(book);

        URI createdUri = uriInfo.getRequestUriBuilder().path(savedBook.getId()).build();
        return Response.created(createdUri).entity(savedBook).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Update an existing book",
            response = Book.class
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Book updated successfully"
            ),
            @ApiResponse(
                    code = 400,
                    message = "Bad request",
                    response = ValidationError.class
            ),
            @ApiResponse(
                    code = 401,
                    message = "'Authorization' header is missing or wrong username/password",
                    response = ErrorResponse.class,
                    responseHeaders = @ResponseHeader(
                            name = "WWW-Authenticate",
                            description = "Defines the authentication method that should be used to gain access to a resource.",
                            response = String.class
                    )
            ),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error",
                    response = ErrorResponse.class
            )
    })
    public Response updateBook(@NotNull(message = "{requestBody.does.not.exist}")
                               @ApiParam(
                                       value = "Book object you want to update",
                                       required = true
                               )
                               @ValidBookToUpdate
                               @Valid Book book) throws SQLException {

        LOGGER.debug("Updating book {}", book);
        Book updatedBook = bookService.update(book);

        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    @ApiOperation(
            code = 204,
            value = "Delete book by id"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 204,
                    message = "Book deleted successfully"
            ),
            @ApiResponse(
                    code = 401,
                    message = "'Authorization' header is missing or wrong username/password",
                    response = ErrorResponse.class,
                    responseHeaders = @ResponseHeader(
                            name = "WWW-Authenticate",
                            description = "Defines the authentication method that should be used to gain access to a resource.",
                            response = String.class
                    )
            ),
            @ApiResponse(
                    code = 404,
                    message = "Invalid request URL.",
                    response = ErrorResponse.class
            ),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error",
                    response = ErrorResponse.class
            )
    })
    public Response deleteBook(
            @ApiParam(
                    value = "Id of the Book you want to delete",
                    required = true,
                    example = "767a463c-4cc3-48c1-b93e-25c0d216032b"
            )
            @PathParam("id") String id) throws SQLException {

        LOGGER.debug("Deleting book with id '{}'", id);
        bookService.delete(id);

        return Response.noContent().build();
    }
}