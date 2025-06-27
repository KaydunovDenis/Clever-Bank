package com.github.kaydunov.dto;

import com.github.kaydunov.entity.Transaction;
import com.github.kaydunov.entity.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
public class StatementTest {

    @Test()
    void toStringTest() {
        //Arrange Statement(s)
        Statement target = createStatement();

        //Act Statement(s)
        String result = target.toString();
        System.out.println("RESULT:\n" + result);

        //Assert statement(s)
        String expectedCheck = """
                                             Выписка                            \s
                                           Clever-Bank                          \s
                 Клиент                    | Сотников Кирилл Артёмович               \s
                 Счет                      | AS12 ASDG 1200 2132 ASDA 353A 2132      \s
                 Валюта                    | BYN                                     \s
                 Дата открытия             | 01.01.1970                              \s
                 Период                    | 01.01.1970 - 08.10.2024                 \s
                 Дата и время формирования | 01.11.2024, 14:47                       \s
                 Остаток                   | 10,000.00 BYN                           \s
                    Дата    | Примечание           | Сумма     \s
                -----------------------------------------------------------------
                 08.10.2024 | WITHDRAW             | 1000.00    BYN\s
                 09.10.2024 | DEPOSIT              | 2512.72    BYN\s
                 11.10.2023 | DEPOSIT              | 512.72     BYN\s
                """;
        assertEquals(expectedCheck, result);
    }

    public static Statement createStatement() {
        Statement statement = new Statement();
        statement.setClientName("Сотников Кирилл Артёмович ");
        statement.setAccountNumber("AS12 ASDG 1200 2132 ASDA 353A 2132");
        statement.setCurrency("BYN");
        statement.setAccountOpeningDate(LocalDate.of(1970, 1, 1));
        statement.setStartOfPeriod(LocalDate.of(1970, 1, 1));
        statement.setEndOfPeriod(LocalDate.of(2024, 10, 8));
        statement.setGenerationDate(LocalDateTime.of(2024, 11, 1, 14, 47));
        statement.setBalance(BigDecimal.valueOf(10000));
        statement.setTransactions(getTransactions());
        return statement;
    }

    private static List<Transaction> getTransactions() {
        Transaction transaction1 = new Transaction(
                BigDecimal.valueOf(1000),
                new Timestamp(Date.valueOf("2024-10-08").getTime()),
                "1L",
                "2L",
                TransactionType.WITHDRAW,
                "BYN");
        Transaction transaction2 = new Transaction(
                BigDecimal.valueOf(2512.72),
                new Timestamp(Date.valueOf("2024-10-09").getTime()),
                "3L",
                "1L",
                TransactionType.DEPOSIT,
                "BYN");
        Transaction transaction3 = new Transaction(
                BigDecimal.valueOf(512.72),
                new Timestamp(Date.valueOf("2023-10-11").getTime()),
                "3L",
                "1L",
                TransactionType.DEPOSIT,
                "BYN");
        return List.of(transaction1, transaction2, transaction3);
    }
}
