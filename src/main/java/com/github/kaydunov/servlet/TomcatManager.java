package com.github.kaydunov.servlet;

import com.github.kaydunov.exception.TomCatException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import lombok.extern.java.Log;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.reflections.Reflections;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

@Log
public class TomcatManager {
    private static final String CONTEXT_PATH = "/clever-bank";
    private static Tomcat tomcat;
    private static Context context;

    private TomcatManager() {}


    public static void start(){
        tomcat = new Tomcat();
        tomcat.setBaseDir("temp");
        tomcat.setPort(8080);
        tomcat.getConnector();

        String docBase = new File(".").getAbsolutePath();
        context = tomcat.addContext(CONTEXT_PATH, docBase);

        try {
            registerServlets();
            tomcat.start();
        } catch (Exception e) {
            throw new TomCatException(e.getMessage(), e);
        }
        tomcat.getServer().await();
        log.info("Tomcat was started and is waiting requests");
    }

    private static void registerServlets() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Set<Class<?>> servletClasses = ServletScanner.getServletClasses("");
        for (Class<?> implementation : servletClasses) {
            registerServlet((HttpServlet) implementation.getDeclaredConstructor().newInstance());
        }
    }

    private static void registerServlet(HttpServlet servletInstance) {
        Class<? extends HttpServlet> servletClass = servletInstance.getClass();
        WebServlet annotation = servletClass.getAnnotation(WebServlet.class);
        if (annotation != null) {
            String[] urls = annotation.value();
            String urlPattern = urls[0];
            String servletName = servletClass.getName();


            Tomcat.addServlet(context, servletName, servletInstance);

            //Set context
            context.addServletMappingDecoded(urlPattern, servletName);
        } else {
            throw new IllegalArgumentException("Servlet class " + servletClass.getName() + " is missing @WebServlet annotation");
        }
    }
}
