package com.github.kaydunov.servlet;

import com.github.kaydunov.dao.AccountDao;
import com.github.kaydunov.dao.BankDao;
import com.github.kaydunov.dao.UserDao;
import com.github.kaydunov.exception.TomCatException;
import com.github.kaydunov.service.AccountService;
import lombok.extern.java.Log;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

@Log
public class TomcatManager {
    private static Tomcat tomcat;

    private TomcatManager() {}


    public static void start(){
        tomcat = new Tomcat();
        tomcat.setBaseDir("temp");
        tomcat.setPort(8080);
        tomcat.getConnector();

        addServletsToContainer();

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            throw new TomCatException(e);
        }
        tomcat.getServer().await();
        log.info("Tomcat was started and is waiting requests");
    }

    private static void addServletsToContainer() {
        String contextPath = "";
        String docBase = new File(".").getAbsolutePath();
        Context context = tomcat.addContext(contextPath, docBase);

        String servletName = AccountServlet.class.getName();
        String urlPattern = "/clever.bank";

        tomcat.addServlet(contextPath, servletName, new AccountServlet(new AccountService(new AccountDao(new BankDao(), new UserDao()))));
        context.addServletMappingDecoded(urlPattern, servletName);
    }
}
