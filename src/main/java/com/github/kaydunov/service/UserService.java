package com.github.kaydunov.service;

import com.github.kaydunov.dao.crud.UserDao;
import com.github.kaydunov.entity.User;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;
import javassist.NotFoundException;

@Component
public class UserService {
    @Autowired
    private UserDao userDao;

    public User create(User entity) {
        return userDao.create(entity);
    }

    public void update(User entity) {
        userDao.update(entity);
    }

    public User findById(Long id) throws NotFoundException {
        return userDao.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public Iterable<User> findAll() {
        return userDao.findAll();
    }

    public void deleteById(Long id) {
        userDao.deleteById(id);
    }
}