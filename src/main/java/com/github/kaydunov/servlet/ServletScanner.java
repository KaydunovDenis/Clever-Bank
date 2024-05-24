package com.github.kaydunov.servlet;

import jakarta.servlet.annotation.WebServlet;
import org.reflections.Reflections;

import java.util.Set;

public class ServletScanner {

    public static Set<Class<?>> getServletClasses(String packageName) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getTypesAnnotatedWith(WebServlet.class);
    }
}
