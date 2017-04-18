package com.programmingskillz.samplejerseywebapp.business.service;

import com.programmingskillz.samplejerseywebapp.business.domain.Book;
import com.programmingskillz.samplejerseywebapp.data.repository.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Durim Kryeziu
 */
public class BookServiceImpl implements BookService {

    private Repository<Book> repository;

    public BookServiceImpl(Repository<Book> repository) {
        this.repository = repository;
    }

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
}