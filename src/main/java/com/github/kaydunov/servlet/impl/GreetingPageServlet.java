package com.github.kaydunov.servlet.impl;

import com.github.kaydunov.util.HTMLFileToStringConverter;
import com.github.kaydunov.spring.Component;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.PrintWriter;

@Component
@WebServlet("")
public class GreetingPageServlet extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
        PrintWriter writer = resp.getWriter();
        String response = HTMLFileToStringConverter.convertHTMLFileToString("src/main/webapp/Greeting.html");
        writer.println(response);
    }
}
