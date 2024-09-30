package com.github.kaydunov.util;

import com.github.kaydunov.spring.Component;
import lombok.SneakyThrows;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.util.Map;

@Component
public class YamlConfigReader {
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.yaml";

    private YamlConfigReader() {
    }

    @SneakyThrows
    public static Map<String, String> readConfigYaml() {
        Yaml yaml = new Yaml();
        FileInputStream inputStream = new FileInputStream(CONFIG_FILE_PATH);
        return yaml.load(inputStream);
    }
}
