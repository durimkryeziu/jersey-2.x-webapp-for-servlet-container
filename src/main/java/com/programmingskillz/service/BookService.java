package com.programmingskillz.service;

import com.programmingskillz.domain.Book;

import java.util.List;

/**
 * @author Durim Kryeziu
 */
public interface BookService {
    Book add(Book entity);

    Book get(String id);

    List<Book> getAll();

    Book update(Book entity);

    void delete(String id);

    void deleteAll();
}