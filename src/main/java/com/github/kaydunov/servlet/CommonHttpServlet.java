package com.github.kaydunov.servlet;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class CommonHttpServlet extends HttpServlet {

    public static final String ACCOUNT_ID_URL_PARAMETER = "accountId";
    public static final String AMOUNT_URL_PARAMETER = "amount";
    private static final String FORMAT_URL_PARAMETER = "format";

    private static final String DEFAULT_FORMAT = "txt";

    protected void sendResponse(HttpServletResponse response, int status, Map<String, Object> message) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        String jsonResponse = new Gson().toJson(message);
        response.getWriter().write(jsonResponse);
    }

    protected void handleError(HttpServletResponse response, int status, String errorMessage) throws IOException {
        sendResponse(response, status, Map.of("error", errorMessage));
    }

    public String getFormat(HttpServletRequest request) {
        String format = request.getParameter(FORMAT_URL_PARAMETER);
        if (format == null || format.isBlank()) {
            format = DEFAULT_FORMAT;
        }
        return format;
    }
}
