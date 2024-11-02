package com.github.kaydunov.dao;

import com.github.kaydunov.dao.crud.AccountDao;
import com.github.kaydunov.dao.crud.TransactionDao;
import com.github.kaydunov.dto.Statement;
import com.github.kaydunov.entity.Account;
import com.github.kaydunov.entity.Transaction;
import com.github.kaydunov.entity.User;
import com.github.kaydunov.service.AccountService;
import com.github.kaydunov.service.UserService;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;
import javassist.NotFoundException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class StatementDao {
    @Autowired
    private TransactionDao transactionDao;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    String SQL_SELECT_BY_ACCOUNT_ID = "SELECT";


    public List<Statement> findAll(String accountId) throws NotFoundException {
        Account account = accountService.getById(accountId);
        List<Transaction> accountTransactions = transactionDao.getTransactionsByAccountId(accountId);
        Long userId = account.getUserId();
        User user = userService.findById(userId);


        List<Statement> statements = new ArrayList<>();
        for (Transaction transaction : accountTransactions) {
            Statement statement = new Statement();
            statement.setNumber(transaction.getId());
            statement.setClientName(user.getName());
            statement.setAccountNumber(accountId);
            statement.setCurrency(account.getCurrency());



            statements.add(statement);
        }
        return statements;
    }

    //todo write test
   public List<Statement> findAllByAccountIdAndYear(String accountId, int year) throws NotFoundException {
    Account account = accountService.getById(accountId);
    Timestamp date = Timestamp.valueOf(Integer.toString(year));
    List<Transaction> accountTransactions = transactionDao.findByAccountIdAndData(accountId, date);
    Long userId = account.getUserId();
    User user = userService.findById(userId);

    List<Statement> statements = new ArrayList<>();
    for (Transaction transaction : accountTransactions) {
        Statement statement = new Statement();
        statement.setNumber(transaction.getId());
        statement.setClientName(user.getName());
        statement.setAccountNumber(accountId);
        statement.setCurrency(account.getCurrency());

        statements.add(statement);
    }
    return statements;
}
    
}
