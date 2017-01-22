package com.programmingskillz.service;

import com.programmingskillz.domain.Book;
import com.programmingskillz.repository.BookRepository;
import com.programmingskillz.repository.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Durim Kryeziu
 */
public class BookServiceImpl implements BookService {

    private Repository<Book> repository = new BookRepository();

    @Override
    public Book add(Book entity) throws SQLException {
        return repository.save(entity);
    }

    @Override
    public Book get(String id) throws SQLException {
        return repository.findOne(id);
    }

    @Override
    public List<Book> getAll() throws SQLException {
        return repository.findAll();
    }

    @Override
    public Book update(Book entity) throws SQLException {
        return repository.update(entity);
    }

    @Override
    public void delete(String id) throws SQLException {
        repository.delete(id);
    }

    @Override
    public void deleteAll() throws SQLException {
        repository.deleteAll();
    }
}