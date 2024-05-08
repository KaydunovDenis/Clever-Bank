package com.github.kaydunov.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kaydunov.dao.AccountDao;
import com.github.kaydunov.entity.Account;
import lombok.Data;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {
    private final ObjectMapper mapper = new ObjectMapper();
    private final AccountDao accountDao = new AccountDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Read JSON payload from request
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        // Parse JSON payload into AccountOperation object
        AccountOperation operation = mapper.readValue(sb.toString(), AccountOperation.class);

        // Perform the requested operation
        try {
            switch (operation.getType()) {
                case "deposit":
                    deposit(operation.getAccountId(), operation.getAmount());
                    break;
                case "withdraw":
                    withdraw(operation.getAccountId(), operation.getAmount());
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operation type");
            }
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("Error: " + e.getMessage());
        }
    }

    private void deposit(Long accountId, BigDecimal amount) throws SQLException {
        // Logic to deposit funds into the account
        Account account = accountDao.findById(accountId);
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        accountDao.update(account);
    }

    private void withdraw(Long accountId, BigDecimal amount) throws InsufficientFundsException, SQLException {
        // Logic to withdraw funds from the account
        Account account = accountDao.findById(accountId);
        BigDecimal newBalance = account.getBalance().subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsException("Insufficient funds in account");
        }
        account.setBalance(newBalance);
        accountDao.update(account);
    }

    @Data
    private static class AccountOperation {
        private String type;
        private Long accountId;
        private BigDecimal amount;

        // Getters and setters
    }

    private static class InsufficientFundsException extends Exception {
        public InsufficientFundsException(String message) {
            super(message);
        }
    }
}
