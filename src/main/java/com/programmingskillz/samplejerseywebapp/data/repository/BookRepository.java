package com.programmingskillz.samplejerseywebapp.data.repository;

import static com.programmingskillz.samplejerseywebapp.util.Utils.toInstant;
import static com.programmingskillz.samplejerseywebapp.util.Utils.toSQLDate;

import com.programmingskillz.samplejerseywebapp.business.domain.Book;
import com.programmingskillz.samplejerseywebapp.business.exception.BookNotFoundException;
import com.programmingskillz.samplejerseywebapp.data.DataSource;
import org.adeptnet.sql.NamedParameterStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Durim Kryeziu
 */
public class BookRepository implements Repository<Book> {

  private static final String TITLE = "title";
  private static final String AUTHOR = "author";
  private static final String DESCRIPTION = "description";
  private static final String ISBN = "isbn";
  private static final String PAGES = "pages";
  private static final String PUBLISHER = "publisher";
  private static final String PUBLISHED = "published";

  @Override
  public Book save(Book entity) throws SQLException {
    String sql
        = "INSERT INTO books (id, title, author, description, isbn, pages, publisher, published) " +
        "VALUES(:id, :title, :author, :description, :isbn, :pages, :publisher, :published);";

    try (Connection conn = DataSource.getInstance().getConnection();
         NamedParameterStatement nps = new NamedParameterStatement(conn, sql)) {

      entity.setId(UUID.randomUUID().toString());

      Map<String, Object> params = new HashMap<>();
      params.put("id", entity.getId());
      params.put(TITLE, entity.getTitle());
      params.put(AUTHOR, entity.getAuthor());
      params.put(DESCRIPTION, entity.getDescription());
      params.put(ISBN, entity.getIsbn());
      params.put(PAGES, entity.getPages());
      params.put(PUBLISHER, entity.getPublisher());
      params.put(
          PUBLISHED, entity.getPublished() != null ? toSQLDate(entity.getPublished()) : null
      );
      nps.setAll(params);

      nps.executeUpdate();
      return entity;
    }
  }

  @Override
  public Book findOne(String id) throws SQLException {
    String sql = "SELECT * FROM books WHERE id = :id";

    try (Connection conn = DataSource.getInstance().getConnection();
         NamedParameterStatement nps = new NamedParameterStatement(conn, sql)) {
      nps.setString("id", id);

      try (ResultSet rs = nps.executeQuery()) {
        if (rs.next()) {
          Book book = new Book();
          book.setId(id);
          book.setTitle(rs.getString(TITLE));
          book.setAuthor(rs.getString(AUTHOR));
          book.setDescription(rs.getString(DESCRIPTION));
          book.setIsbn(rs.getString(ISBN));
          book.setPages(rs.getInt(PAGES));
          book.setPublisher(rs.getString(PUBLISHER));
          book.setPublished(
              rs.getDate(PUBLISHED) != null ? toInstant(rs.getDate(PUBLISHED)) : null
          );
          return book;
        } else {
          throw new BookNotFoundException("Book with id '" + id + "' not found.");
        }
      }
    }
  }

  @Override
  public List<Book> findAll() throws SQLException {
    List<Book> books = new ArrayList<>();

    String sql = "SELECT * FROM books";

    try (Connection conn = DataSource.getInstance().getConnection();
         NamedParameterStatement nps = new NamedParameterStatement(conn, sql);
         ResultSet rs = nps.executeQuery()) {

      while (rs.next()) {
        Book book = new Book();
        book.setId(rs.getString("id"));
        book.setTitle(rs.getString(TITLE));
        book.setAuthor(rs.getString(AUTHOR));
        book.setDescription(rs.getString(DESCRIPTION));
        book.setIsbn(rs.getString(ISBN));
        book.setPages(rs.getInt(PAGES));
        book.setPublisher(rs.getString(PUBLISHER));
        book.setPublished(
            rs.getDate(PUBLISHED) != null ? toInstant(rs.getDate(PUBLISHED)) : null
        );
        books.add(book);
      }
    }
    return books;
  }

  @Override
  public Book update(Book entity) throws SQLException {
    String sql = "UPDATE books SET title=:title, author=:author, " +
        "description=:description, isbn=:isbn, pages=:pages, " +
        "publisher=:publisher, published=:published WHERE id=:id";

    try (Connection conn = DataSource.getInstance().getConnection();
         NamedParameterStatement nps = new NamedParameterStatement(conn, sql)) {

      Map<String, Object> params = new HashMap<>();
      params.put("id", entity.getId());
      params.put(TITLE, entity.getTitle());
      params.put(AUTHOR, entity.getAuthor());
      params.put(DESCRIPTION, entity.getDescription());
      params.put(ISBN, entity.getIsbn());
      params.put(PAGES, entity.getPages());
      params.put(PUBLISHER, entity.getPublisher());
      params.put(
          PUBLISHED, entity.getPublished() != null ? toSQLDate(entity.getPublished()) : null
      );
      nps.setAll(params);

      nps.executeUpdate();
      return entity;
    }
  }

  @Override
  public void delete(String id) throws SQLException {
    String sql = "DELETE FROM books WHERE id=:id";

    try (Connection conn = DataSource.getInstance().getConnection();
         NamedParameterStatement nps = new NamedParameterStatement(conn, sql)) {
      nps.setString("id", id);
      nps.executeUpdate();
    }
  }
}
