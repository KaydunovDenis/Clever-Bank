package com.github.kaydunov.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kaydunov.dao.AccountDao;
import com.github.kaydunov.entity.Account;
import com.github.kaydunov.entity.Operation;
import com.github.kaydunov.service.AccountService;
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
    private final AccountService accountService;

    public AccountServlet(AccountService accountService) {
        this.accountService = accountService;
    }

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
            Operation operation = mapper.readValue(sb.toString(), Operation.class);

            // Perform the requested operation
            accountService.performRequestOperation(operation);
            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("Error: " + e.getMessage());
        }
    }
}
