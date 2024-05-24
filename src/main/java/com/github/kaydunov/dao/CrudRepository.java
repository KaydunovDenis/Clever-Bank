package com.github.kaydunov.dao;

import com.github.kaydunov.entity.Account;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
    T save(T entity);
    Optional<T> findById(ID id);
    Iterable<T> readAll();

    void update(T entity);

    void deleteById(ID id);
}
