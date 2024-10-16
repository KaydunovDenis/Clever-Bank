package com.github.kaydunov.servlet;

import com.github.kaydunov.exception.TomCatException;
import com.github.kaydunov.spring.ApplicationContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.util.Set;

@Slf4j
public class TomcatManager {
    private static final String CONTEXT_PATH = "/clever-bank";
    private static final String SERVLETS_PACKAGE_NAME = "com.github.kaydunov.servlet";
    public static final int PORT = 8080;
    public static final String TOMCAT_BASE_DIR = "temp";
    private static Context context;

    private TomcatManager() {}

    public static void start(ApplicationContext applicationContext){
        Tomcat tomcat = initializeTomcat();
        context = tomcat.addContext(CONTEXT_PATH, new File(".").getAbsolutePath());

        try {
            registerServlets(applicationContext);
            tomcat.start();
            tomcat.getServer().await();
            log.info("Tomcat was started and is waiting requests");
        } catch (Exception e) {
            log.error("Failed to start Tomcat: {}", e.getMessage(), e);
            throw new TomCatException(e.getMessage(), e);
        }
    }

    protected static Tomcat initializeTomcat() {
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(TOMCAT_BASE_DIR);
        tomcat.setPort(PORT);
        tomcat.getConnector();
        return tomcat;
    }

    private static void registerServlets(ApplicationContext applicationContext) {
        Set<Class<?>> servletClasses = ServletScanner.getServletClasses(SERVLETS_PACKAGE_NAME);
        for (Class<?> implementation : servletClasses) {
            HttpServlet servlet = (HttpServlet) applicationContext.getBean(implementation);
            registerServlet(servlet);
        }
    }

    private static void registerServlet(HttpServlet servlet) {
        validateServlet(servlet);
        WebServlet annotation = servlet.getClass().getAnnotation(WebServlet.class);
        if (annotation != null) {
            addServletToContext(servlet, annotation);
        } else {
            throw new IllegalArgumentException("Servlet class " + servlet.getClass().getName() + " is missing @WebServlet annotation");
        }
    }

    private static void addServletToContext(HttpServlet servlet, WebServlet annotation) {
        String servletName = servlet.getClass().getName();
        Tomcat.addServlet(context, servletName, servlet);
        for (String urlPattern : annotation.value()) {
            context.addServletMappingDecoded(urlPattern, servletName);
        }
    }

    private static void validateServlet(HttpServlet servlet) {
        if (servlet == null) {
            throw new TomCatException("Servlet shouldn't be null.");
        }
    }
}
