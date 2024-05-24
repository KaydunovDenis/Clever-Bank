package com.github.kaydunov.dao.impl;

import com.github.kaydunov.dao.CrudRepository;
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
        return null;
    }

    @Override
    public List<Operation> readAll() {
        return null;
    }

    @Override
    public void update(Operation entity) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    public List<Operation> getOperationsByAccountId(Long accountId) {
        return null;
    }
}
