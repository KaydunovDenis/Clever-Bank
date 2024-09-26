package com.github.kaydunov.util;

import com.github.kaydunov.exception.YamlConfigReaderException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(PowerMockExtension.class)
@PrepareForTest({YamlConfigReader.class})
class YamlConfigReaderSapientGeneratedTest {


    @Disabled()
    @Test()
    void readConfigYamlWhenThrowsException() throws Exception {
        //Act Statement(s)
        PowerMockito.whenNew(FileInputStream.class)
                .withAnyArguments()
                .thenThrow(FileNotFoundException.class);

        assertThrows(YamlConfigReaderException.class, () ->
            YamlConfigReader.readConfigYaml());

    }

    @Test()
    void readConfigYamlTest() {
        //Act Statement(s)
        Map<String, String> result = YamlConfigReader.readConfigYaml();
        //Assert statement(s)
        assertAll("result", () -> assertThat(result, is(notNullValue())));
    }
}
