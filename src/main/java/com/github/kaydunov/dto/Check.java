package com.github.kaydunov.dto;

import com.github.kaydunov.entity.TransactionType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static com.github.kaydunov.util.StringUtils.alignByCentre;

/**
 * This class responsible for a check view.
 * <p>
 * String checkExample = """
 * ----------------------------------
 * |          Банковский Чек        |
 * | Чек:                        -1 |
 * | 01-01-1970            03:00:05 |
 * | Тип транзакции:        PERCENT |
 * | Банк отправителя:   SenderBank |
 * | Банк получателя: RecipientBank |
 * | Счет отправителя:            1 |
 * | Счет получателя:             2 |
 * | Сумма:              100.00 BYN |
 * ----------------------------------
 * """;
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Check extends Report {

    private static final int LINE_WIDTH = 50;
    int number;
    Timestamp date;
    TransactionType type;
    String recipientBankName;
    String senderBankName;
    String accountSourceId;
    String accountDestinationId;
    BigDecimal amount;
    String currency;


    public String toString() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        final StringBuilder sb = new StringBuilder();

        sb.append("-".repeat(LINE_WIDTH)).append("\n");


        String title = "Банковский Чек";

        sb.append(String.format("|%s|%n", alignByCentre(title, LINE_WIDTH - 2)));
        sb.append(String.format("| Чек: %41d |%n", number));
        sb.append(String.format("| %-16s %29s |%n", dateFormat.format(date), timeFormat.format(date)));
        sb.append(String.format("| Тип транзакции: %30s |%n", type));
        sb.append(String.format("| Банк отправителя: %28s |%n", senderBankName != null ? senderBankName : "-"));
        sb.append(String.format("| Банк получателя: %29s |%n", recipientBankName != null ? recipientBankName : "-"));
        sb.append(String.format("| Счет отправителя: %28s |%n", accountSourceId != null ? accountSourceId : "-"));
        sb.append(String.format("| Счет получателя: %29s |%n", accountDestinationId != null ? accountDestinationId : "-"));
        sb.append(String.format("| Сумма: %35.2f %-3s |%n", amount, currency));
        sb.append("-".repeat(LINE_WIDTH)).append("\n");

        return sb.toString();
    }
}