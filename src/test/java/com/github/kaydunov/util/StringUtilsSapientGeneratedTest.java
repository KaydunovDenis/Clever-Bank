package com.github.kaydunov.util;

import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class StringUtilsSapientGeneratedTest {

    //Sapient generated method id: ${centerStringTest}, hash: AA1E2EE5B220A4CAD172D30FCC36FC3D
    @Test()
    void centerStringTest() {
        
        //Act Statement(s)
        String result = StringUtils.centerString("B", 4);
        
        //Assert statement(s)
        assertAll("result", () -> assertThat(result, equalTo(" B  ")));
    }

    @Test()
    void centerStringTest2() {
        //Act Statement(s)
        String result = StringUtils.centerString("ABC", 7);
        //Assert statement(s)
        assertAll("result", () -> assertThat(result, equalTo("  ABC  ")));
    }

    //Sapient generated method id: ${centerStringLnTest}, hash: 4E129CCE90C0979F8DEEA17C8E4FBB5D
    @Test()
    void centerStringLnTest() {
        
        //Act Statement(s)
        String result = StringUtils.centerStringLn("1", 3);
        
        //Assert statement(s)
        assertAll("result", () -> assertThat(result, equalTo(" 1 \n")));
    }
}
