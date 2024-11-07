package com.github.kaydunov.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class DateConverterSapientGeneratedTest {

    @Test
    void convertYearToTimestamp() {
        //Act Statement(s)
        Timestamp result = DateConverter.toTimestamp(1);
        LocalDateTime localDateTime = LocalDateTime.of(1, 1, 1, 0, 0);
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        //Assert statement(s)
        assertAll("result", () -> assertThat(result, equalTo(timestamp)));
    }

    //Sapient generated method id: ${toTimestampTest}, hash: 4DD0D0EF47EC24E1A2013D2743CED3A2
    @Test()
    void toTimestampTest() {
        //Act Statement(s)
        Timestamp result = DateConverter.toTimestamp(1);
        LocalDate localDate = LocalDate.of(1, 1, 1);
        LocalDateTime localDateTime = localDate.atStartOfDay();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        //Assert statement(s)
        assertAll("result", () -> assertThat(result, equalTo(timestamp)));
    }

    //Sapient generated method id: ${toTimestamp1Test}, hash: FC1D87C5DE824B3DCCC37B55E378AAB2
    @Test()
    void toTimestamp1Test() {
        //Act Statement(s)
        Timestamp result = DateConverter.toTimestamp(1, 1);
        LocalDate localDate = LocalDate.of(1, 1, 1);
        LocalDateTime localDateTime = localDate.atStartOfDay();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        //Assert statement(s)
        assertAll("result", () -> assertThat(result, equalTo(timestamp)));
    }

    @Test()
    void getCurrentTimestampTest() {
        Instant result = DateConverter.getCurrentTimestamp().toInstant();
        Instant now = Instant.now();
        assertTrue(now.isAfter(result));
    }
}
