package com.github.kaydunov.service;

import com.github.kaydunov.dto.Statement;
import com.github.kaydunov.entity.Account;
import com.github.kaydunov.entity.Transaction;
import com.github.kaydunov.entity.User;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;
import com.github.kaydunov.util.DateConverter;
import javassist.NotFoundException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


/**
 * This service create different statements for a defined account.
 */
@Component
public class StatementService {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    public Statement getStatement(String userId, int year, int month) throws NotFoundException {
        if (month > 0) {
            return createMonthlyStatement(userId, year, month);
        } else if (year > 0) {
            return createYearlyStatement(userId, year);
        } else {
            return createFullPeriodStatement(userId);
        }
    }



    /**
     * Creates a statement with the specified account id and
     * the current date.
     */
    public Statement createFullPeriodStatement(String accountId) throws NotFoundException {
        Account account = accountService.getById(accountId);
        Timestamp startDate = account.getCreatedAt();
        Timestamp endDate = DateConverter.getCurrentTimestamp();
        return createStatement(accountId, startDate, endDate);
    }

    /**
     * Creates a statement with the specified account id and
     * the current year.
     *
     * @param accountId
     * @param year
     */
    public Statement createYearlyStatement(String accountId, int year) throws NotFoundException {
        Timestamp startDate = DateConverter.toTimestamp(year);
        Timestamp endDate = DateConverter.toTimestamp(++year);
        return createStatement(accountId, startDate, endDate);
    }

    /**
     * Creates a statement with the specified account id and
     * the specified date range.
     *
     * @param accountId
     * @param month of year 1-12
     */
    public Statement createMonthlyStatement(String accountId, int year, int month) throws NotFoundException {
        if (year == 0) {
            year = LocalDateTime.now().getYear();
        }
        Timestamp startDate = DateConverter.toTimestamp(year, month);
        Timestamp endDate = DateConverter.toTimestamp(year, ++month);
        return createStatement(accountId, startDate, endDate);
    }

    private Statement createStatement(String accountId, Timestamp startDate, Timestamp endDate) throws NotFoundException {
        Account account = accountService.getById(accountId);
        Long userId = account.getUserId();
        User user = userService.findById(userId);
        List<Transaction> transactions = transactionService.findByAccountIdAndDateRange(accountId, startDate, endDate);

        Statement statement = new Statement();
        statement.setClientName(user.getName());
        statement.setAccountNumber(accountId);
        statement.setCurrency(account.getCurrency());
        statement.setBalance(account.getBalance());
        statement.setAccountOpeningDate(DateConverter.toLocalDate(account.getCreatedAt()));
        statement.setStartOfPeriod(DateConverter.toLocalDate(startDate));
        statement.setEndOfPeriod(DateConverter.toLocalDate(endDate));
        statement.setGenerationDate(LocalDateTime.now());
        statement.setTransactions(transactions);
        return statement;
    }
}
