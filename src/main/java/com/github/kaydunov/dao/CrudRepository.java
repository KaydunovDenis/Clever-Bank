package com.github.kaydunov.dao;

import java.util.Optional;

public interface CrudRepository<T, ID> {
    T create(T entity);
    void update(T entity);
    Optional<T> findById(ID id);

    Iterable<T> findAll();

    void deleteById(ID id);
}
