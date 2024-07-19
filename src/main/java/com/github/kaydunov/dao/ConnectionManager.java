package com.github.kaydunov.dao;

import com.github.kaydunov.exception.DatabaseConfigurationException;
import lombok.extern.java.Log;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

@Log
public class ConnectionManager {
    private static final Properties PROPERTIES = new Properties();
    private static final String DATABASE_URL;

    private static final String YAML_FILE_PATH = "src/main/resources/application.yaml";

    static {

        // Load YAML file
        Yaml yaml = new Yaml();
        try {
            FileInputStream inputStream = new FileInputStream(YAML_FILE_PATH);
            Map<String, Object> yamlMap = yaml.load(inputStream);

            // Get JDBC driver configuration
           PROPERTIES.putAll((Map<String, String>) yamlMap.get("db"));

            String driverName = PROPERTIES.getProperty("driver");
            Class.forName(driverName);
        } catch (IOException | ClassNotFoundException e) {
            throw new DatabaseConfigurationException("Incorrect database driver", e);
        }
        DATABASE_URL = PROPERTIES.getProperty("url");

    }

    private ConnectionManager(){}


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, PROPERTIES);
    }
}
