package com.github.kaydunov.dto;

import com.github.kaydunov.entity.TransactionType;
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
        Check target = createCheck();

        //Act Statement(s)
        String result = target.toString();

        //Assert statement(s)
        String expectedCheck = """
                --------------------------------------------------
                |                 Банковский Чек                 |
                | Чек:                                        -1 |
                | 01-01-1970                            03:00:05 |
                | Тип транзакции:                       INTEREST |
                | Банк отправителя:                   SenderBank |
                | Банк получателя:                 RecipientBank |
                | Счет отправителя:                           1L |
                | Счет получателя:                            2L |
                | Сумма:                              100.00 BYN |
                --------------------------------------------------
                """;
        assertEquals(expectedCheck, result);
    }

    public static Check createCheck() {
        Check check = new Check();
        int notRealId = -1;
        check.setNumber(notRealId);
        check.setDate(new Timestamp(5000));
        check.setAccountSourceId("1L");
        check.setAccountDestinationId("2L");
        check.setAmount(new BigDecimal("100.0"));
        check.setSenderBankName("SenderBank");
        check.setRecipientBankName("RecipientBank");
        check.setType(TransactionType.INTEREST);
        check.setCurrency("BYN");
        return check;
    }
}
