package com.github.kaydunov.entity;

import com.github.kaydunov.exception.InsufficientFundsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
public class AccountTest {

    //Sapient generated method id: ${withdrawBalanceWhenNewSourceBalanceCompareToBigDecimalZEROLessThan0ThrowsInsufficientFundsException}, hash: 7FD69B128B985A9942119E94EA39AFCE
    @Test()
    void withdrawBalanceWhenNewSourceBalanceCompareToBigDecimalZEROLessThan0ThrowsInsufficientFundsException() {
        /* Branches:
         * (newSourceBalance.compareTo(BigDecimal.ZERO) < 0) : true
         */
        //Arrange Statement(s)
        Account target = createAccount();
        final BigDecimal amount = new BigDecimal("2.0");
        //Act Statement(s)
        final InsufficientFundsException result = assertThrows(InsufficientFundsException.class, () -> target.withdrawBalance(amount));

        //Assert statement(s)
        assertAll("result", () -> assertThat(result, is(notNullValue())));
    }

    //Sapient generated method id: ${withdrawBalanceWhenNewSourceBalanceCompareToBigDecimalZERONotLessThan0}, hash: DE4721492436C7286425DBCD663BC25D
    @Test()
    void withdrawBalanceWhenNewSourceBalanceCompareToBigDecimalZERONotLessThan0() {
        /* Branches:
         * (newSourceBalance.compareTo(BigDecimal.ZERO) < 0) : false
         */
        //Arrange Statement(s)
        Account target = createAccount();
        //Act Statement(s)
        String money = "0.4";
        target.withdrawBalance(new BigDecimal(money));
        //Assert statement(s)
        Assertions.assertEquals(new BigDecimal("0.6"), target.getBalance());
    }

    //Sapient generated method id: ${depositBalanceTest}, hash: D691429363B3DB6A3D6CC8BF465485D7
    @Test()
    void depositBalanceTest() {
        //Arrange Statement(s)
        Account target = createAccount();

        //Act Statement(s)
        String money = "1.0";
        target.depositBalance(new BigDecimal(money));
        //Assert statement(s)
        Assertions.assertEquals(new BigDecimal("3.0"), target.getBalance());

    }

    public static Account createAccount() {
        Account account = new Account();
        account.setId("0L");
        account.setBalance(new BigDecimal("1.0"));
        account.setCurrency("BYN");
        account.setUserId(0L);
        account.setBankId(0L);
        account.setSavingAccount(true);
        List<Long> transactionIds = List.of(1L, 2L);
        account.setTransactionsIds(transactionIds);
        return account;
    }
}
