package com.programmingskillz.repository;

import com.programmingskillz.domain.Book;
import com.programmingskillz.exceptions.BookNotFoundException;
import org.adeptnet.sql.NamedParameterStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author Durim Kryeziu
 */
public class BookRepository implements Repository<Book> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookRepository.class);

    @Override
    public Book save(Book entity) throws SQLException {

        String sql = "INSERT INTO books (id, title, author, description, isbn, pages, publisher, published) " +
                "VALUES(:id, :title, :author, :description, :isbn, :pages, :publisher, :published);";

        try (Connection conn = DataSource.getConnection();
             NamedParameterStatement nps = new NamedParameterStatement(conn, sql)) {

            entity.setId(UUID.randomUUID().toString());

            Map<String, Object> params = new HashMap<>();
            params.put("id", entity.getId());
            params.put("title", entity.getTitle());
            params.put("author", entity.getAuthor());
            params.put("description", entity.getDescription());
            params.put("isbn", entity.getIsbn());
            params.put("pages", entity.getPages());
            params.put("publisher", entity.getPublisher());
            params.put("published", entity.getPublished() != null ? Timestamp.from(entity.getPublished()) : null);
            nps.setAll(params);

            nps.executeUpdate();
            return entity;
        } catch (SQLException e) {
            LOGGER.error("SQLException:", e);
            throw e;
        }
    }

    @Override
    public Book findOne(String id) throws SQLException {
        Book book;

        String sql = "SELECT * FROM books WHERE id = :id";

        try (Connection conn = DataSource.getConnection();
             NamedParameterStatement nps = new NamedParameterStatement(conn, sql)) {
            nps.setString("id", id);

            ResultSet rs = nps.executeQuery();
            if (rs.next()) {
                book = new Book();
                book.setId(id);
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setDescription(rs.getString("description"));
                book.setIsbn(rs.getString("isbn"));
                book.setPages(rs.getInt("pages"));
                book.setPublisher(rs.getString("publisher"));
                book.setPublished(rs.getTimestamp("published") != null ? rs.getTimestamp("published").toInstant() : null);
            } else {
                throw new BookNotFoundException("Book with id '" + id + "' not found.");
            }
            rs.close();
            return book;
        } catch (SQLException e) {
            LOGGER.error("SQLException:", e);
            throw e;
        }
    }

    @Override
    public List<Book> findAll() throws SQLException {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT * FROM books";

        try (Connection conn = DataSource.getConnection();
             NamedParameterStatement nps = new NamedParameterStatement(conn, sql);
             ResultSet rs = nps.executeQuery()) {

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getString("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setDescription(rs.getString("description"));
                book.setIsbn(rs.getString("isbn"));
                book.setPages(rs.getInt("pages"));
                book.setPublisher(rs.getString("publisher"));
                book.setPublished(rs.getTimestamp("published") != null ? rs.getTimestamp("published").toInstant() : null);
                books.add(book);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException:", e);
            throw e;
        }

        return books;
    }

    @Override
    public Book update(Book entity) throws SQLException {

        String sql = "UPDATE books SET title=:title, author=:author, " +
                "description=:description, isbn=:isbn, pages=:pages, " +
                "publisher=:publisher, published=:published WHERE id=:id";

        try (Connection conn = DataSource.getConnection();
             NamedParameterStatement nps = new NamedParameterStatement(conn, sql)) {

            Map<String, Object> params = new HashMap<>();
            params.put("id", entity.getId());
            params.put("title", entity.getTitle());
            params.put("author", entity.getAuthor());
            params.put("description", entity.getDescription());
            params.put("isbn", entity.getIsbn());
            params.put("pages", entity.getPages());
            params.put("publisher", entity.getPublisher());
            params.put("published", entity.getPublished() != null ? Timestamp.from(entity.getPublished()) : null);
            nps.setAll(params);

            nps.executeUpdate();
            return entity;
        } catch (SQLException e) {
            LOGGER.error("SQLException:", e);
            throw e;
        }
    }

    @Override
    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM books WHERE id=:id";

        try (Connection conn = DataSource.getConnection();
             NamedParameterStatement nps = new NamedParameterStatement(conn, sql)) {

            nps.setString("id", id);
            nps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException:", e);
            throw e;
        }
    }

    @Override
    public void deleteAll() throws SQLException {
        String sql = "DELETE FROM books";

        try (Connection conn = DataSource.getConnection();
             NamedParameterStatement nps = new NamedParameterStatement(conn, sql)) {

            nps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException:", e);
            throw e;
        }
    }
}