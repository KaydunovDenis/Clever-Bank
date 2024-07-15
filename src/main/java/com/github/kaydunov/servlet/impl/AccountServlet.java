package com.github.kaydunov.servlet.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kaydunov.entity.Account;
import com.github.kaydunov.entity.Transaction;
import com.github.kaydunov.service.AccountService;
import com.github.kaydunov.servlet.ServletMarker;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.util.List;

@Component
@WebServlet(value = "/account")
public class AccountServlet extends ServletMarker {
    private static final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private AccountService accountService;


    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
        List<Account> accounts = accountService.getAll();
        String json = convertToJson(accounts);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    private String convertToJson(Object object) throws JsonProcessingException {
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
            Long accountSourceId = transaction.getAccountSourceId();
            Long accountDestinationId = transaction.getAccountDestinationId();
            accountService.transfer(amount, accountSourceId, accountDestinationId);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
