package com.github.kaydunov.util;


import org.junit.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class YamlConfigReaderTest {

    @Test
    public void readConfigYamlTest() {
        // Act Statement(s)
        Map<String, String> result = YamlConfigReader.readConfigYaml();

        // Assert statement(s)
        assertTrue(result.containsKey("percentage"));

        Object percentage = result.get("percentage");
        assertTrue(percentage instanceof Number, "Percentage should be a number");
    }

}
