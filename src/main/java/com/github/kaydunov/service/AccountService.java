package com.github.kaydunov.service;

import com.github.kaydunov.dao.impl.AccountDao;
import com.github.kaydunov.exception.DaoException;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;

import java.math.BigDecimal;
import java.sql.SQLException;

@Component
public class AccountService {
    @Autowired
    private AccountDao accountDao;


    private void deposit(BigDecimal amount, Long accountSourceId, Long accountDestinationId) {
        try {
            accountDao.transfer(amount, accountSourceId, accountDestinationId);
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

}
