package com.programmingskillz.util;

import com.programmingskillz.domain.Book;
import com.programmingskillz.repository.BookRepository;
import com.programmingskillz.repository.Repository;
import com.programmingskillz.service.BookService;
import com.programmingskillz.service.BookServiceImpl;

/**
 * @author Durim Kryeziu
 */
public class DependenciesFactory {

    private static Repository<Book> bookRepository;
    private static BookService bookService;

    private static Repository<Book> getBookRepository() {
        if (bookRepository == null) {
            bookRepository = new BookRepository();
        }

        return bookRepository;
    }

    public static BookService getBookService() {
        if (bookService == null) {
            bookService = new BookServiceImpl(getBookRepository());
        }

        return bookService;
    }
}