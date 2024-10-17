package com.github.kaydunov.service;

import com.github.kaydunov.dto.Check;
import com.github.kaydunov.entity.CheckTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

import static org.junit.Assert.assertTrue;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class CheckServiceTest {

    private final CheckService target = new CheckService();

    @Test
    void process() {
        Check check = CheckTest.getCheck();
        String filename = "check/check_" + check.getNumber() + ".txt";
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
        //Arrange Statement(s)
        //Act Statement(s)
        target.process(check);
        assertTrue(file.exists());
    }


}
