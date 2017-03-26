package com.programmingskillz.repository;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Durim Kryeziu
 */
public interface Repository<T> {

    T save(T entity) throws SQLException;

    T findOne(String id) throws SQLException;

    List<T> findAll() throws SQLException;

    T update(T entity) throws SQLException;

    void delete(String id) throws SQLException;
}