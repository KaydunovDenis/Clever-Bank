package com.github.kaydunov.servlet;

import com.github.kaydunov.convertor.HTMLFileToStringConverter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.PrintWriter;

@WebServlet("")
public class GreetingPageServlet extends ServletMarker {
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
        PrintWriter writer = resp.getWriter();
        String response = HTMLFileToStringConverter.convertHTMLFileToString("src/main/webapp/Greeting.html");
        writer.println(response);
    }
}
