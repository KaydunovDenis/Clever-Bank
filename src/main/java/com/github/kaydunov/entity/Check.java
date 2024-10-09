package com.github.kaydunov.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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
public class Check {
    int number;
    Timestamp date;
    TransactionType type;
    String recipientBankName;
    String senderBankName;
    Long accountSourceId;
    Long accountDestinationId;
    BigDecimal amount;
    //TODO: добавить валюту


    @Override
    public String toString() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        final StringBuilder sb = new StringBuilder();

        sb.append("----------------------------------\n");
        sb.append("|          Банковский Чек        |\n");
        sb.append(String.format("| Чек: %25d |\n", number));
        sb.append(String.format("| %s %19s |\n", dateFormat.format(date), timeFormat.format(date)));
        sb.append(String.format("| Тип транзакции: %14s |\n", type));
        sb.append(String.format("| Банк отправителя: %12s |\n", senderBankName));
        sb.append(String.format("| Банк получателя: %10s |\n", recipientBankName));
        sb.append(String.format("| Счет отправителя: %12d |\n", accountSourceId));
        sb.append(String.format("| Счет получателя:%14d |\n", accountDestinationId));
        sb.append(String.format("| Сумма:        %12.2f BYN |\n", amount));
        sb.append("----------------------------------\n");

        return sb.toString();
    }
}