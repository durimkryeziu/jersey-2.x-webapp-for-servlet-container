package com.programmingskillz.repository;

import java.util.List;

/**
 * @author Durim Kryeziu
 */
public interface Repository<T> {

    T save(T entity);

    T findOne(String id);

    List<T> findAll();

    T update(T entity);

    void delete(String id);

    void deleteAll();
}