package com.github.kaydunov.util;

import com.github.kaydunov.spring.Component;
import lombok.SneakyThrows;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.util.Map;

@Component
public class YamlReader {

    private YamlReader() {
    }

    @SneakyThrows
    public static Map<String, String> readYaml(String filePath) {
        Yaml yaml = new Yaml();
        FileInputStream inputStream = new FileInputStream(filePath);
        return yaml.load(inputStream);
    }

}
