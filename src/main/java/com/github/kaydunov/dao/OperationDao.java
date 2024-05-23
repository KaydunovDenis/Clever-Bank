package com.github.kaydunov.dao;

import com.github.kaydunov.entity.Operation;

import java.util.List;
import java.util.Optional;

public class OperationDao implements CrudRepository<Operation, Long> {
    @Override
    public Operation save(Operation entity) {
        return null;
    }

    @Override
    public Optional<Operation> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Operation> findAll() {
        return null;
    }

    @Override
    public void update(Operation entity) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
