package com.github.kaydunov.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kaydunov.dao.AccountDao;
import com.github.kaydunov.entity.Operation;
import com.github.kaydunov.service.AccountService;
import com.github.kaydunov.spring.Autowired;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import javax.swing.text.html.HTML;
import java.io.BufferedReader;
import java.io.PrintWriter;

@WebServlet(value = "/account")
public class AccountServlet extends ServletMarker {
    private static final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private AccountService accountService;


    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {

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
