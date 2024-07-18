package com.github.kaydunov.servlet;

import jakarta.servlet.annotation.WebServlet;
import org.reflections.Reflections;

import java.util.Set;

public class ServletScanner {

    private ServletScanner() {

    }

    public static Set<Class<?>> getServletClasses(String packageName) {
        if (packageName == null || packageName.isEmpty()) {
            throw new IllegalArgumentException("Package name must not be null or empty");
        }

        Reflections reflections = new Reflections(packageName);
        return reflections.getTypesAnnotatedWith(WebServlet.class);
    }
}
