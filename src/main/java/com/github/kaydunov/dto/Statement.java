package com.github.kaydunov.dto;

import com.github.kaydunov.entity.Transaction;
import com.github.kaydunov.util.StringUtils;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * This class responsible for a statement view.
 * <p>
 *
 *                           Выписка
 *                         Clever-Bank
 * | Клиент                    | Сотников Кирилл Артёмович                |
 * | Счет                      | AS12 ASDG 1200 2132 ASDA 353A 2132       |
 * | Валюта                    | BYN                                      |
 * | Дата открытия             | 01.01.1970                               |
 * | Период                    | 01.01.1970 - 08.10.2024                  |
 * | Дата и время формирования | 01.11.2024, 14:47                        |
 * | Остаток                   | 10,000.00 BYN                            |
 * | Дата       | Примечание           | Сумма      |
 * ----------------------------------------------
 * | 08.10.2024 | Снятие средств        |  -1000.10 BYN |
 * | 08.10.2024 | Пополнение от Иванова |   2512.72 BYN |
 * | 08.10.2024 | Пополнение от Петрова |    638.02 BYN |
 *
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Statement implements TXT{
    int number;
    String clientName;
    String accountNumber;
    String currency;
    LocalDate accountOpeningDate;
    LocalDate periodStartDate;
    LocalDate periodEndDate;
    LocalDateTime generationDate;
    BigDecimal balance;
    List<Transaction> transactions;

    static final DateTimeFormatter onlyDateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
    static final int WITH = 65;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.centerStringLn("Выписка", WITH));
        fillAccountInfo(sb);
        fillAccountTransactionsInfo(sb);
        return sb.toString();
    }

    private void fillAccountInfo(StringBuilder sb) {
        sb.append(StringUtils.centerStringLn("Clever-Bank", WITH));
        sb.append(String.format(" Клиент                    | %-40s %n", clientName));
        sb.append(String.format(" Счет                      | %-40s %n", accountNumber));
        sb.append(String.format(" Валюта                    | %-40s %n", currency));
        sb.append(String.format(" Дата открытия             | %-40s %n", accountOpeningDate.format(onlyDateFormat)));
        sb.append(String.format(" Период                    | %s - %-27s %n",
                periodStartDate.format(onlyDateFormat),
                periodEndDate.format(onlyDateFormat)));
        sb.append(String.format(" Дата и время формирования | %-40s %n",
                generationDate.format(dateTimeFormatter)));
        sb.append(String.format(" Остаток                   | %,.2f %-30s %n", balance, currency));
    }

    private void fillAccountTransactionsInfo(StringBuilder sb) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        sb.append(String.format("%s| %-20s | %-10s %n", "    Дата    ", "Примечание", "Сумма"));
        sb.append("-".repeat(WITH)).append("\n");
        for (Transaction transaction : transactions) {
            sb.append(String.format(" %-10s | %-20s | %-10.2f %s %n",
                    dateFormat.format(transaction.getCreatedAt()),
                    transaction.getTransactionType(),
                    transaction.getAmount(),
                    currency));
        }
    }

}

