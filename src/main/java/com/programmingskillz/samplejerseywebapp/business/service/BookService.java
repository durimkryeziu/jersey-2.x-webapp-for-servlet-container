package com.programmingskillz.samplejerseywebapp.business.service;

import com.programmingskillz.samplejerseywebapp.business.domain.Book;

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
}