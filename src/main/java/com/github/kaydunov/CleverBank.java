package com.github.kaydunov;

import com.github.kaydunov.percentage_processor.PercentageProcessor;
import com.github.kaydunov.servlet.TomcatManager;
import com.github.kaydunov.spring.ApplicationContext;

public class CleverBank {

    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContext(CleverBank.class);
        PercentageProcessor percentageProcessor = context.getBean(PercentageProcessor.class);
        percentageProcessor.startProcessing();
        TomcatManager.start(context);
    }
}