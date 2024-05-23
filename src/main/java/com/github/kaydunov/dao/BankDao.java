package com.github.kaydunov.dao;

import com.github.kaydunov.entity.Bank;

import java.util.List;
import java.util.Optional;

public class BankDao implements CrudRepository <Bank, Long> {
    @Override
    public Bank save(Bank entity) {
        return null;
    }

    @Override
    public Optional<Bank> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Bank> findAll() {
        return null;
    }

    @Override
    public void update(Bank entity) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
