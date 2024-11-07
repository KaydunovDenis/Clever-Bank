package com.github.kaydunov.service;

import com.github.kaydunov.dao.crud.TransactionDao;
import com.github.kaydunov.entity.Account;
import com.github.kaydunov.entity.Transaction;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;
import com.github.kaydunov.util.DateConverter;
import javassist.NotFoundException;

import java.sql.Timestamp;
import java.util.List;

@Component
public class TransactionService {

    @Autowired
    TransactionDao transactionDao;
    @Autowired
    AccountService accountService;

    /**
     * Receives monthly transaction log.
     */
    public List<Transaction> getMonthlyStatement(String accountId, int year, int month) {
        Timestamp startDate = DateConverter.toTimestamp(year, month);
        Timestamp endDate = DateConverter.toTimestamp(year, ++month);
        return transactionDao.findByAccountIdAndDateRange(accountId, startDate, endDate);
    }

    /**
     * Receives a statement of the user’s transactions for the year.
     */
    public List<Transaction> getYearlyStatement(String accountId, int year) {
        Timestamp startDate = DateConverter.toTimestamp(year);
        Timestamp endDate = DateConverter.toTimestamp(++year);
        return transactionDao.findByAccountIdAndDateRange(accountId, startDate, endDate);
    }

    /**
     * Receives the user transaction statement for the entire service period.
     */
    public List<Transaction> getFullPeriodStatement(String accountId) throws NotFoundException {
        Account account = accountService.getById(accountId);
        Timestamp accountOpenDate = account.getCreatedAt();
        Timestamp currentDate = DateConverter.getCurrentTimestamp();
        return transactionDao.findByAccountIdAndDateRange(accountId, accountOpenDate, currentDate);
    }
}
