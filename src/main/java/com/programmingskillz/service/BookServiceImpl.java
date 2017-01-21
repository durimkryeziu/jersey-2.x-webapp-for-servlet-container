package com.programmingskillz.service;

import com.programmingskillz.domain.Book;
import com.programmingskillz.repository.BookRepository;
import com.programmingskillz.repository.Repository;

import java.util.List;

/**
 * @author Durim Kryeziu
 */
public class BookServiceImpl implements BookService {

    private Repository<Book> repository = new BookRepository();

    @Override
    public Book add(Book entity) {
        return repository.save(entity);
    }

    @Override
    public Book get(String id) {
        return repository.findOne(id);
    }

    @Override
    public List<Book> getAll() {
        return repository.findAll();
    }

    @Override
    public Book update(Book entity) {
        return repository.update(entity);
    }

    @Override
    public void delete(String id) {
        repository.delete(id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}