package com.github.kaydunov.servlet.impl;

import com.github.kaydunov.entity.Account;
import com.github.kaydunov.entity.Transaction;
import com.github.kaydunov.service.AccountService;
import com.github.kaydunov.servlet.ObjectMapperWrapper;
import com.github.kaydunov.servlet.ServletMarker;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javassist.NotFoundException;
import lombok.SneakyThrows;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Component
@WebServlet("/account")
public class AccountServlet extends ServletMarker {
    @Autowired
    private ObjectMapperWrapper mapper;
    @Autowired
    private AccountService accountService;



    @SneakyThrows(IOException.class)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String json;
        int status;
        String idParam = request.getParameter("id");
        try {
            json = idParam == null ? processAllAccounts() : processAccountById(idParam);
            status = HttpServletResponse.SC_OK;
        } catch (NotFoundException e) {
            json = "Account not found";
            status = HttpServletResponse.SC_NOT_FOUND;
        }
        prepareResponse(response, status, json);
    }

    private String processAllAccounts() {
        List<Account> accounts = accountService.getAll();
        return convertToJson(accounts);
    }

    private String processAccountById(String accountId) throws NotFoundException {
        Account account = accountService.getById(accountId);
        return convertToJson(account);
    }

    private void prepareResponse(HttpServletResponse response, int status, String json) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.getWriter().write(json);
    }

    @SneakyThrows
    private String convertToJson(Object object) {
        return mapper.writeValueAsString(object);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Parse JSON into Transaction object
            Transaction transaction = mapper.readValue(request.getInputStream(), Transaction.class);

            // Perform the requested operation
            BigDecimal amount = transaction.getAmount();
            String accountSourceId = transaction.getSourceAccountId();
            String accountDestinationId = transaction.getDestinationAccountId();
            accountService.transfer(amount, accountSourceId, accountDestinationId);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error: " + e.getMessage());
        }
    }

    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String accountId = request.getParameter("id");
        if (accountId == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        accountService.deleteById(accountId);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
