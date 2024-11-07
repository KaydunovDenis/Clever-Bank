package com.github.kaydunov.service;

import com.github.kaydunov.dto.Statement;
import com.github.kaydunov.entity.Account;
import com.github.kaydunov.entity.Transaction;
import com.github.kaydunov.entity.User;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;
import javassist.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class StatementService {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    public Statement findByAccountId(String accountId) throws NotFoundException {
        List<Transaction> accountTransactions = transactionService.getFullPeriodStatement(accountId);
        return createStatement(accountId, accountTransactions);
    }

    public Statement findByAccountIdAndYear(String accountId, int year) throws NotFoundException {
        List<Transaction> accountTransactions = transactionService.getYearlyStatement(accountId, year);
        return createStatement(accountId, accountTransactions);
    }

    /**
     *
     * @param accountId
     * @param month of year 1-12
     */
    public Statement findByAccountIdAndMonth(String accountId, int year, int month) throws NotFoundException {
        List<Transaction> accountTransactions = transactionService.getMonthlyStatement(accountId, year, month);
        return createStatement(accountId, accountTransactions);
    }

    private Statement createStatement(String accountId, List<Transaction> transactions) throws NotFoundException {
        Account account = accountService.getById(accountId);
        Long userId = account.getUserId();
        User user = userService.findById(userId);

        Statement statement = new Statement();
        statement.setClientName(user.getName());
        statement.setAccountNumber(accountId);
        statement.setCurrency(account.getCurrency());
        statement.setBalance(account.getBalance());
        statement.setAccountOpeningDate(account.getCreatedAtAsLocalDate());
        statement.setStartOfPeriod(account.getCreatedAtAsLocalDate());
        statement.setEndOfPeriod(LocalDate.now());//todo
        statement.setGenerationDate(LocalDateTime.now());//todo
        statement.setTransactions(transactions);

        return statement;
    }
}
