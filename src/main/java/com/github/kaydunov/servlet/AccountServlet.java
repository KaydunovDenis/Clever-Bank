package com.github.kaydunov.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kaydunov.dao.AccountDao;
import com.github.kaydunov.entity.Account;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;

//todo @WebServlet("/account")
public class AccountServlet extends HttpServlet {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final AccountDao accountDao = new AccountDao();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
        PrintWriter writer = resp.getWriter();

        writer.println("<html><title>Welcome</title><body>");
        writer.println("<h1>Have a Great Day!</h1>");
        writer.println("<h1>AccountServlet is available.</h1>");
        writer.println("</body></html>");
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {

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
            performRequestOperation(operation);
            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("Error: " + e.getMessage());
        }
    }

    private void performRequestOperation(AccountOperation operation) throws SQLException, InsufficientFundsException {
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
