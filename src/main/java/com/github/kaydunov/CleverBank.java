package com.github.kaydunov;

import com.github.kaydunov.servlet.TomcatManager;
import com.github.kaydunov.spring.ApplicationContext;

public class CleverBank {

    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContext(CleverBank.class);
        TomcatManager.start(context);//todo сорать код в jar
    }
}