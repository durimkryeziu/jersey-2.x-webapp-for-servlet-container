package com.programmingskillz.samplejerseywebapp.util;

import com.programmingskillz.samplejerseywebapp.business.domain.Book;
import com.programmingskillz.samplejerseywebapp.business.service.BookService;
import com.programmingskillz.samplejerseywebapp.business.service.BookServiceImpl;
import com.programmingskillz.samplejerseywebapp.data.repository.BookRepository;
import com.programmingskillz.samplejerseywebapp.data.repository.Repository;

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
