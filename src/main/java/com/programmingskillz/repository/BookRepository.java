package com.programmingskillz.repository;

import com.programmingskillz.domain.Book;
import org.adeptnet.sql.NamedParameterStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Durim Kryeziu
 */
public class BookRepository implements Repository<Book> {

    @Override
    public Book save(Book entity) {
        Book book = null;

        Connection connection = null;
        NamedParameterStatement nps = null;

        try {
            connection = DataSource.getConnection();

            if (connection != null && !connection.isClosed()) {
                String sql = "INSERT INTO books (id, title, author, description, isbn, pages, publisher, published) " +
                        "VALUES(:id, :title, :author, :description, :isbn, :pages, :publisher, :published);";
                nps = new NamedParameterStatement(connection, sql);

                entity.setId(UUID.randomUUID().toString());

                Map<String, Object> params = new HashMap<>();
                params.put("id", entity.getId());
                params.put("title", entity.getTitle());
                params.put("author", entity.getAuthor());
                params.put("description", entity.getDescription());
                params.put("isbn", entity.getIsbn());
                params.put("pages", entity.getPages());
                params.put("publisher", entity.getPublisher());
                params.put("published", entity.getPublished());
                nps.setAll(params);

                nps.executeUpdate();
                book = entity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (nps != null) {
                    nps.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return book;
    }

    @Override
    public Book findOne(String id) {
        Book book = null;

        Connection connection = null;
        NamedParameterStatement nps = null;
        ResultSet rs = null;

        try {
            connection = DataSource.getConnection();

            if (connection != null && !connection.isClosed()) {
                String sql = "SELECT * FROM books WHERE id = :id";
                nps = new NamedParameterStatement(connection, sql);
                nps.setString("id", id);

                rs = nps.executeQuery();
                while (rs.next()) {
                    book = new Book();
                    book.setId(id);
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setDescription(rs.getString("description"));
                    book.setIsbn(rs.getString("isbn"));
                    book.setPages(rs.getInt("pages"));
                    book.setPublisher(rs.getString("publisher"));
                    book.setPublished(rs.getDate("published"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (nps != null) {
                    nps.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return book;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();

        Connection connection = null;
        NamedParameterStatement nps = null;
        ResultSet rs = null;

        try {
            connection = DataSource.getConnection();

            if (connection != null && !connection.isClosed()) {
                String sql = "SELECT * FROM books";
                nps = new NamedParameterStatement(connection, sql);

                rs = nps.executeQuery();
                while (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getString("id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setDescription(rs.getString("description"));
                    book.setIsbn(rs.getString("isbn"));
                    book.setPages(rs.getInt("pages"));
                    book.setPublisher(rs.getString("publisher"));
                    book.setPublished(rs.getDate("published"));
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (nps != null) {
                    nps.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return books;
    }

    @Override
    public Book update(Book entity) {
        Book book = null;

        Connection connection = null;
        NamedParameterStatement nps = null;

        try {
            connection = DataSource.getConnection();

            if (connection != null && !connection.isClosed()) {
                String sql = "UPDATE books SET title=:title, author=:author, " +
                        "description=:description, isbn=:isbn, pages=:pages, " +
                        "publisher=:publisher, published=:published WHERE id=:id";
                nps = new NamedParameterStatement(connection, sql);

                Map<String, Object> params = new HashMap<>();
                params.put("id", entity.getId());
                params.put("title", entity.getTitle());
                params.put("author", entity.getAuthor());
                params.put("description", entity.getDescription());
                params.put("isbn", entity.getIsbn());
                params.put("pages", entity.getPages());
                params.put("publisher", entity.getPublisher());
                params.put("published", entity.getPublished());
                nps.setAll(params);

                nps.executeUpdate();
                book = entity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (nps != null) {
                    nps.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return book;
    }

    @Override
    public void delete(String id) {
        Connection connection = null;
        NamedParameterStatement nps = null;

        try {
            connection = DataSource.getConnection();

            if (connection != null && !connection.isClosed()) {
                String sql = "DELETE FROM books WHERE id=:id";
                nps = new NamedParameterStatement(connection, sql);
                nps.setString("id", id);
                nps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (nps != null) {
                    nps.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteAll() {
        Connection connection = null;
        NamedParameterStatement nps = null;

        try {
            connection = DataSource.getConnection();

            if (connection != null && !connection.isClosed()) {
                String sql = "DELETE FROM books";
                nps = new NamedParameterStatement(connection, sql);
                nps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (nps != null) {
                    nps.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}