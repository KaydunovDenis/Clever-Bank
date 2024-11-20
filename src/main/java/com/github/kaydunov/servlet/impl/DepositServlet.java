package com.github.kaydunov.servlet.impl;

import com.github.kaydunov.service.AccountService;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javassist.NotFoundException;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

@Component
@WebServlet("/account/deposit")
public class DepositServlet extends HttpServlet {

    private static final String INVALID_INPUT = "Invalid input";
    private static final String DATABASE_ERROR = "Database error";
    private static final String DEPOSIT_WAS_SUCCESSFUL = "Deposit was successful";
    @Autowired
    private AccountService accountService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String accountId = request.getParameter("accountId");
            BigDecimal amount = new BigDecimal(request.getParameter("amount"));
            String format = getFormat(request);

            accountService.deposit(amount, accountId, format);
            sendResponse(response, HttpServletResponse.SC_OK, Map.of("message", DEPOSIT_WAS_SUCCESSFUL));
        } catch (NumberFormatException e) {
            handleError(response, HttpServletResponse.SC_BAD_REQUEST, INVALID_INPUT);
        } catch (NotFoundException e) {
            handleError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            handleError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (SQLException e) {
            handleError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, DATABASE_ERROR);
        }
    }

    private String getFormat(HttpServletRequest request) {
        String format = request.getParameter("format");
        if (format == null || format.isBlank()) {
            format = "txt"; // Дефолтный формат
        }
        return format;
    }

    private void sendResponse(HttpServletResponse response, int status, Map<String, String> message) throws IOException {
        response.setStatus(status);
        String jsonResponse = new Gson().toJson(message);
        response.getWriter().write(jsonResponse);
    }

    private void handleError(HttpServletResponse response, int status, String errorMessage) throws IOException {
        sendResponse(response, status, Map.of("error", errorMessage));
    }

}
