package com.github.kaydunov.percentage_processor;

import com.github.kaydunov.service.AccountService;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;
import com.github.kaydunov.util.YamlReader;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Регулярно, по расписанию (раз в полминуты), проверяет, нужно ли начислять проценты
 * (1% - значение подставляется из конфигурационного файла)
 * на остаток счета в конце месяца
 * <p>
 * Проверку и начисление процентов нужно реализовать асинхронно
 */

@Component
public class PercentageProcessor {
    private static final int CHECK_PERIOD = 30;
    public static final String CONFIG_FILE_PATH = "src/main/resources/config.yaml";
    public static final String PERCENTAGE_PROPERTY = "percentage";

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

    public double getPercentageFromYaml() {
        Map<String, Object> yamlMap = YamlReader.readYaml(CONFIG_FILE_PATH);
        Object percentage = yamlMap.get(PERCENTAGE_PROPERTY);
        return Double.parseDouble(percentage.toString());
    }

}
