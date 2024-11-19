package com.github.kaydunov.entity;

import com.github.kaydunov.dto.Check;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
public class CheckTest {

    @Test()
    void toStringTest() {
        //Arrange Statement(s)
        Check target = getCheck();

        //Act Statement(s)
        String result = target.toString();

        //Assert statement(s)
        String expectedCheck = """
                ----------------------------------
                |          Банковский Чек        |
                | Чек:                        -1 |
                | 01-01-1970            03:00:05 |
                | Тип транзакции:       INTEREST |
                | Банк отправителя:   SenderBank |
                | Банк получателя: RecipientBank |
                | Счет отправителя:           1L |
                | Счет получателя:            2L |
                | Сумма:              100.00 BYN |
                ----------------------------------
                """;
        assertEquals(expectedCheck, result);
    }

    public static Check getCheck() {
        Check object = new Check();
        int notRealId = -1;
        object.setNumber(notRealId);
        object.setDate(new Timestamp(5000));
        object.setAccountSourceId("1L");
        object.setAccountDestinationId("2L");
        object.setAmount(new BigDecimal("100.0"));
        object.setSenderBankName("SenderBank");
        object.setRecipientBankName("RecipientBank");
        object.setType(TransactionType.INTEREST);
        object.setCurrency("BYN");
        return object;
    }
}
