package com.github.kaydunov.util;

import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class TimeStampConverterSapientGeneratedTest {

    //Sapient generated method id: ${convertYearToTimestampTest}, hash: 47057C041DC41E7E5EE796B5664091E8
    @Test()
    void convertYearToTimestampTest() {
        
        //Act Statement(s)
        Timestamp result = TimeStampConverter.convertYearToTimestamp(1);
        LocalDateTime localDateTime = LocalDateTime.of(1, 1, 1, 0, 0);
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        
        //Assert statement(s)
        assertAll("result", () -> assertThat(result, equalTo(timestamp)));
    }
}
