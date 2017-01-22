package com.programmingskillz.service;

import com.programmingskillz.domain.Book;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Durim Kryeziu
 */
public interface BookService {
    Book add(Book entity) throws SQLException;

    Book get(String id) throws SQLException;

    List<Book> getAll() throws SQLException;

    Book update(Book entity) throws SQLException;

    void delete(String id) throws SQLException;

    void deleteAll() throws SQLException;
}