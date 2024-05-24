package com.github.kaydunov.dao.impl;

import com.github.kaydunov.dao.CrudRepository;
import com.github.kaydunov.entity.Bank;

import java.util.List;
import java.util.Optional;

public class BankDao implements CrudRepository<Bank, Long> {
    @Override
    public Bank save(Bank entity) {
        return null;
    }

    @Override
    public Optional<Bank> findById(Long aLong) {
        return null;
    }

    @Override
    public List<Bank> readAll() {
        return null;
    }

    @Override
    public void update(Bank entity) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
