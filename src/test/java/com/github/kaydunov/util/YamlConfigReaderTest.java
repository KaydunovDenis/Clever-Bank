package com.github.kaydunov.util;


import org.junit.Test;

import java.util.Map;

import static com.github.kaydunov.util.YamlConfigReader.PERCENTAGE_PROPERTY;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class YamlConfigReaderTest {

    @Test
    public void readConfigYamlTest() {
        // Act Statement(s)
        Map<String, String> result = YamlConfigReader.readConfigYaml();

        // Assert statement(s)
        assertTrue(result.containsKey(PERCENTAGE_PROPERTY));

        Object percentage = result.get(PERCENTAGE_PROPERTY);
        assertInstanceOf(Number.class, percentage, "Percentage should be a number");
    }

}
