package com.github.kaydunov.percentage_processor;

import com.github.kaydunov.service.AccountService;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

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
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.yaml";
    private static final String PERCENTAGE_PROPERTY = "percentage";

    @Autowired
    private AccountService accountService;

    public void processAccounts() {
        // Асинхронный вызов проверки и начисления процентов
        //checkAndApplyInterestAsync();
    }

    public void chargePercentage() {
        accountService.chargePercents(getPercentageFromYaml());
    }



    public static double getPercentageFromYaml() {
        // Load YAML file
        Yaml yaml = new Yaml();
        try {
            FileInputStream inputStream = new FileInputStream(CONFIG_FILE_PATH);
            Map<String, String> yamlMap = yaml.load(inputStream);
            Object percentage = yamlMap.get(PERCENTAGE_PROPERTY);
            return Double.parseDouble(percentage.toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
