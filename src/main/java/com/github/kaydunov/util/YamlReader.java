package com.github.kaydunov.util;

import lombok.SneakyThrows;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.util.Map;

public class YamlReader {

    private YamlReader() {
    }

    @SneakyThrows
    public static Map<String, Object> readYaml(String filePath) {
        Yaml yaml = new Yaml();
        FileInputStream inputStream = new FileInputStream(filePath);
        return yaml.load(inputStream);
    }

}
