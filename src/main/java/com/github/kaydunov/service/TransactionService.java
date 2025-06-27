package com.github.kaydunov.service;

import com.github.kaydunov.dao.crud.TransactionDao;
import com.github.kaydunov.entity.Transaction;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;
import javassist.NotFoundException;

import java.sql.Timestamp;
import java.util.List;

@Component
public class TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    public Transaction save(Transaction transaction) {
        return transactionDao.create(transaction);
    }

    public Transaction findById(Long id) throws NotFoundException {
        return transactionDao.findById(id).orElseThrow(() -> new NotFoundException("Transaction not found"));
    }

    public List<Transaction> findAll() {
        return transactionDao.findAll();
    }

    public void delete(Long id) {
        transactionDao.deleteById(id);
    }

    /**
     * Receives transactions according to account and date range.
     */
    public List<Transaction> findByAccountIdAndDateRange(String accountId, Timestamp startDate, Timestamp endDate) {
        return transactionDao.findByAccountIdAndDateRange(accountId, startDate, endDate);
    }
}