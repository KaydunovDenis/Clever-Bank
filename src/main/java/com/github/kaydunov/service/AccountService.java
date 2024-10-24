package com.github.kaydunov.service;

import com.github.kaydunov.dao.AccountPercentDao;
import com.github.kaydunov.dao.impl.AccountDao;
import com.github.kaydunov.entity.Account;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;
import javassist.NotFoundException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@Component
public class AccountService {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private AccountPercentDao accountPercentDao;

    public void transfer(BigDecimal amount, Long accountSourceId, Long accountDestinationId) throws SQLException {
        accountDao.transfer(amount, accountSourceId, accountDestinationId);
    }

    public void withdraw(BigDecimal amount, Long accountSourceId) throws SQLException, NotFoundException {
        accountDao.withdraw(amount, accountSourceId);
    }

    public void deposit(BigDecimal amount, Long accountDestinationId) throws SQLException, NotFoundException {
        accountDao.deposit(amount, accountDestinationId);
    }

    public List<Account> getAll(){
        return accountDao.findAll();
    }

    public Account getById(Long accountId) throws NotFoundException {
        return accountDao.findById(accountId).orElseThrow(() -> new NotFoundException("Account not found"));
    }

    public void chargePercents(double percent) {
        accountPercentDao.chargePercents(percent);
    }

    public void deleteById(Long accountId) {
        accountDao.deleteById(accountId);
    }
}
