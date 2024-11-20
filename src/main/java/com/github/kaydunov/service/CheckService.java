package com.github.kaydunov.service;

import com.github.kaydunov.dto.Check;
import com.github.kaydunov.entity.TransactionType;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;
import com.github.kaydunov.util.DateConverter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Component
public class CheckService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private BankService bankService;

    public Check create(String accountSourceId,
                        String accountDestinationId,
                        BigDecimal amount,
                        TransactionType transactionType,
                        String currency) {
        Check check = new Check();
        int id = check.getNextNumber();
        check.setNumber(id);
        Timestamp date = DateConverter.getCurrentTimestamp();
        check.setDate(date);
        check.setAccountSourceId(accountSourceId);
        check.setAccountDestinationId(accountDestinationId);
        check.setAmount(amount);
        check.setSenderBankName(bankService.getBankName(accountSourceId));
        check.setRecipientBankName(bankService.getBankName(accountDestinationId));
        check.setType(transactionType);
        check.setCurrency(currency);
        return check;
    }
}
