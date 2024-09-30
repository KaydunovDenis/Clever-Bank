package com.github.kaydunov.percentage_processor;

import com.github.kaydunov.service.AccountService;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.github.kaydunov.util.YamlConfigReader.getPercentageFromYaml;

/**
 * Регулярно, по расписанию (раз в полминуты),проверяет, нужно ли начислять проценты
 * (1% - значение подставляется из конфигурационного файла)
 * на остаток счета в конце месяца
 * <p>
 * Проверку и начисление процентов нужно реализовать асинхронно
 */

@Component
public class PercentageProcessor {
    private static final int CHECK_PERIOD = 30;

    @Autowired
    private AccountService accountService;
    private ScheduledExecutorService executorService;

    public void startProcessing() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::chargePercentageAsync, 0, CHECK_PERIOD, TimeUnit.SECONDS);
    }


    private void chargePercentageAsync() {
        CompletableFuture.runAsync(() -> accountService.chargePercents(getPercentageFromYaml()));
    }

    public void stopProcessing() {
        if (executorService != null) {
            executorService.shutdown();
        }
    }


}
