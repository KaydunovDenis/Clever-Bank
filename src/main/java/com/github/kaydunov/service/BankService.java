package com.github.kaydunov.service;

import com.github.kaydunov.dao.crud.BankDao;
import com.github.kaydunov.entity.Bank;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BankService {
    @Autowired
    private BankDao bankDao;

    public Bank findById(Long bankId) {
        return bankDao.findById(bankId).orElse(null);
    }

    public Bank create(Bank bank) {
        return bankDao.create(bank);
    }

    public Bank update(Bank bank) {
        bankDao.update(bank);
        return bank;
    }

    public void delete(Long bankId) {
        bankDao.deleteById(bankId);
    }

    public List<Bank> findAll() {
        return bankDao.findAll();
    }

    public Optional<Bank> getByAccountId(String accountId) {
        return bankDao.getByAccountId(accountId);
    }

    public String getBankName(String accountId) {
        return bankDao.getByAccountId(accountId)
                .map(Bank::getName)
                .orElse("-");
    }

}
