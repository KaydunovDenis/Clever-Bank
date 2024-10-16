package com.github.kaydunov.util;


import org.junit.Test;

import java.util.Map;

import static com.github.kaydunov.percentage_processor.PercentageProcessor.CONFIG_FILE_PATH;
import static com.github.kaydunov.percentage_processor.PercentageProcessor.PERCENTAGE_PROPERTY;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class YamlReaderTest {

    @Test
    public void readYamlTest() {
        // Act Statement(s)
        Map<String, String> result = YamlReader.readYaml(CONFIG_FILE_PATH);

        // Assert statement(s)
        assertTrue(result.containsKey(PERCENTAGE_PROPERTY));

        Object percentage = result.get(PERCENTAGE_PROPERTY);
        assertInstanceOf(Number.class, percentage, "Percentage should be a number");
    }

}
