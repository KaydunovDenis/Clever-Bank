package com.github.kaydunov.service;

import com.github.kaydunov.entity.Check;
import com.github.kaydunov.entity.CheckTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

import static org.junit.Assert.assertTrue;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class CheckServiceTest {

    private final CheckService target = new CheckService();

    @Test
    void processCheckTest() {
        File file = new File("check/check_-1.txt");
        if (file.exists()) {
            file.delete();
        }
        //Arrange Statement(s)
        Check check = CheckTest.getCheck();
        //Act Statement(s)
        target.processCheck(check);
        assertTrue(file.exists());
    }


}
