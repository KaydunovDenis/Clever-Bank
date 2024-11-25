package com.github.kaydunov.servlet.impl;

import com.github.kaydunov.dto.Statement;
import com.github.kaydunov.service.StatementService;
import com.github.kaydunov.servlet.CommonHttpServlet;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javassist.NotFoundException;

import java.io.IOException;
import java.util.Map;

@Component
@WebServlet("/account/statement")
public class StatementServlet extends CommonHttpServlet {

    @Autowired
    private StatementService statementService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Извлечение параметров
            String userId = request.getParameter(ACCOUNT_ID_URL_PARAMETER);
            int year = getYear(request);
            int month = getMonth(request);

            // Определение типа отчета
            Statement statement = statementService.getStatement(userId, year, month);

            // Отправка результата
            sendResponse(response, HttpServletResponse.SC_OK, Map.of("statement", statement));
        } catch (IllegalArgumentException e) {
            handleError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (NotFoundException e) {
            handleError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            handleError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
        }
    }
    
    private int getYear(HttpServletRequest request) {
        String year = request.getParameter("year");
        try {
            return Integer.parseInt(year);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private int getMonth(HttpServletRequest request) {
        String month = request.getParameter("month");
        try {
            return Integer.parseInt(month);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
