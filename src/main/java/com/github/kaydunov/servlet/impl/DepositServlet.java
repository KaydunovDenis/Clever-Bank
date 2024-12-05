package com.github.kaydunov.servlet.impl;

import com.github.kaydunov.service.AccountService;
import com.github.kaydunov.servlet.CommonHttpServlet;
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
public class DepositServlet extends CommonHttpServlet {

    private static final String INVALID_INPUT = "Invalid input";
    private static final String DATABASE_ERROR = "Database error";
    private static final String DEPOSIT_WAS_SUCCESSFUL = "Deposit was successful";

    @Autowired
    private AccountService accountService;

    //TODO handle IOException
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String accountId = request.getParameter(ACCOUNT_ID_URL_PARAMETER);
            BigDecimal amount = new BigDecimal(request.getParameter(AMOUNT_URL_PARAMETER));
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





}
