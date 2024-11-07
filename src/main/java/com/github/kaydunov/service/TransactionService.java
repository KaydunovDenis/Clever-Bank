package com.github.kaydunov.service;

import com.github.kaydunov.dao.crud.TransactionDao;
import com.github.kaydunov.entity.Transaction;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class TransactionService {

    @Autowired
    TransactionDao transactionDao;
    @Autowired
    AccountService accountService;

    /**
     * Receives transaction according account and date range.
     */
    public List<Transaction> findByAccountIdAndDateRange(String accountId, Timestamp startDate, Timestamp endDate) {
        return transactionDao.findByAccountIdAndDateRange(accountId, startDate, endDate);
    }
}
