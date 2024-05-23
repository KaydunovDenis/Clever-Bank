package com.github.kaydunov.dao;

import com.github.kaydunov.entity.User;

import java.util.List;
import java.util.Optional;

public class UserDao implements CrudRepository<User, Long> {
    @Override
    public User save(User entity) {
        return null;
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
