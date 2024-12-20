package com.github.kaydunov.servlet.impl;

import com.github.kaydunov.entity.Account;
import com.github.kaydunov.entity.Transaction;
import com.github.kaydunov.service.AccountService;
import com.github.kaydunov.servlet.CommonHttpServlet;
import com.github.kaydunov.servlet.ObjectMapperWrapper;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javassist.NotFoundException;
import lombok.SneakyThrows;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Component
@WebServlet("/account")
public class AccountServlet extends CommonHttpServlet {
    private static final String ACCOUNT_NOT_FOUND = "Account not found";
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
            if (idParam == null) {
                json = processAllAccounts();
            } else {
                json = processAccountById(idParam);
            }
            status = HttpServletResponse.SC_OK;
        } catch (NotFoundException e) {
            json = ACCOUNT_NOT_FOUND;
            status = HttpServletResponse.SC_NOT_FOUND;
        }
        prepareResponse(response, status, json);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        //TODO  implementation
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
}
