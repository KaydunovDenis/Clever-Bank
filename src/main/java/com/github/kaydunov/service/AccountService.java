package com.github.kaydunov.service;

import com.github.kaydunov.dao.AccountPercentDao;
import com.github.kaydunov.dao.crud.AccountDao;
import com.github.kaydunov.dto.Check;
import com.github.kaydunov.entity.Account;
import com.github.kaydunov.entity.Transaction;
import com.github.kaydunov.exporter.FileExporterFactory;
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
    @Autowired
    private CheckService checkService;
    @Autowired
    private  TransferService transferService;
    @Autowired
    private FileExporterFactory fileExporterFactory;

    public void transfer(BigDecimal amount, String accountSourceId, String accountDestinationId, String format) throws SQLException {
        Transaction transaction = transferService.transfer(amount, accountSourceId, accountDestinationId);
        //TODO add saving of check
        Check check = checkService.create(accountSourceId, accountDestinationId, amount, transaction);
    }


    public void withdraw(BigDecimal amount, String accountSourceId, String format) throws SQLException, NotFoundException {
        Transaction transaction = accountDao.withdraw(amount, accountSourceId);
        Check check = checkService.create(accountSourceId, null, amount, transaction);
        fileExporterFactory.getExporter(format).export(check);
    }

    public void deposit(BigDecimal amount, String accountDestinationId, String format) throws SQLException, NotFoundException {
        Transaction transaction = accountDao.deposit(amount, accountDestinationId);
        Check check = checkService.create(null, accountDestinationId, amount, transaction);
        fileExporterFactory.getExporter(format).export(check);
    }

    public List<Account> getAll(){
        return accountDao.findAll();
    }

    public Account getById(String accountId) throws NotFoundException {
        return accountDao.findById(accountId).orElseThrow(() -> new NotFoundException("Account not found"));
    }

    public void chargePercents(double percent) {
        accountPercentDao.chargePercents(percent);
    }

    public void deleteById(String accountId) {
        accountDao.deleteById(accountId);
    }
}
