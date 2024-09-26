package com.github.kaydunov.util;

import com.github.kaydunov.exception.YamlConfigReaderException;
import com.github.kaydunov.spring.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

@Component
public class YamlConfigReader {
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.yaml";

    public static Map<String, String> readConfigYaml() {
        Yaml yaml = new Yaml();
        FileInputStream inputStream ;

        try {
            inputStream = new FileInputStream(CONFIG_FILE_PATH);
        } catch (FileNotFoundException e) {
            throw new YamlConfigReaderException(e);
        }
        Map<String, String> yamlMap = yaml.load(inputStream);
        return yamlMap;
    }
}
