package com.github.kaydunov.entity;

import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class TransactionTypeSapientGeneratedTest {

    @Test()
    void fromIdTest() {
        //Act Statement(s)
        TransactionType result = TransactionType.getByOrdinal(1);
        //Assert statement(s)
        assertAll("result", () -> assertThat(result, equalTo(TransactionType.WITHDRAW)));
    }
}
