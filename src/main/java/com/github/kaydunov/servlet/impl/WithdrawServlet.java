package com.github.kaydunov.servlet.impl;

import com.github.kaydunov.service.AccountService;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javassist.NotFoundException;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@Component
@WebServlet("/account/withdraw")
public class WithdrawServlet extends HttpServlet {

    @Autowired
    private AccountService accountService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonResponse;
        int status = HttpServletResponse.SC_OK;

        try {
            String accountId = request.getParameter("accountId");
            BigDecimal amount = new BigDecimal(request.getParameter("amount"));

            accountService.withdraw(amount, accountId);
            jsonResponse = "{\"message\":\"Withdrawal successful\"}";

        } catch (NumberFormatException e) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            jsonResponse = "{\"error\":\"Invalid input\"}";
        } catch (NotFoundException e) {
            status = HttpServletResponse.SC_NOT_FOUND;
            jsonResponse = "{\"error\":\"Account not found\"}";
        } catch (IllegalArgumentException e) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            jsonResponse = "{\"error\":\"" + e.getMessage() + "\"}";
        } catch (SQLException e) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            jsonResponse = "{\"error\":\"Database error\"}";
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.getWriter().write(jsonResponse);
    }
}

