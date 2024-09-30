package com.github.kaydunov.util;

import com.github.kaydunov.spring.Component;
import lombok.SneakyThrows;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.util.Map;

@Component
public class YamlConfigReader {
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.yaml";
    public static final String PERCENTAGE_PROPERTY = "percentage";

    private YamlConfigReader() {
    }

    @SneakyThrows
    public static Map<String, String> readConfigYaml() {
        Yaml yaml = new Yaml();
        FileInputStream inputStream = new FileInputStream(CONFIG_FILE_PATH);
        return yaml.load(inputStream);
    }

    public static double getPercentageFromYaml() {
        Map<String, String> yamlMap = YamlConfigReader.readConfigYaml();
        Object percentage = yamlMap.get(PERCENTAGE_PROPERTY);
        return Double.parseDouble(percentage.toString());
    }

}
