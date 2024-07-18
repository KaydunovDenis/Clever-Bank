package com.github.kaydunov;

import com.github.kaydunov.servlet.TomcatManager;
import com.github.kaydunov.spring.ApplicationContext;

public class CleverBank {
    public static final String BASE_PACKAGE = "com.github.kaydunov";

    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContext(BASE_PACKAGE);
        TomcatManager.start(context);
    }
}