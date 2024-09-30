package com.github.kaydunov.percentage_processor;

import com.github.kaydunov.service.AccountService;
import com.github.kaydunov.spring.Autowired;
import com.github.kaydunov.spring.Component;

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

    public void processAccounts() {
        // Асинхронный вызов проверки и начисления процентов
        //checkAndApplyInterestAsync();
    }

    public void chargePercentage() {
        accountService.chargePercents(getPercentageFromYaml());
    }




}
