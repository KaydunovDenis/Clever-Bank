package com.github.kaydunov.servlet;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class CommonHttpServlet extends HttpServlet {

    public static final String ACCOUNT_ID_URL_PARAMETER = "accountId";
    public static final String AMOUNT_URL_PARAMETER = "amount";

    protected void sendResponse(HttpServletResponse response, int status, Map<String, String> message) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        String jsonResponse = new Gson().toJson(message);
        response.getWriter().write(jsonResponse);
    }

    protected void handleError(HttpServletResponse response, int status, String errorMessage) throws IOException {
        sendResponse(response, status, Map.of("error", errorMessage));
    }
}
