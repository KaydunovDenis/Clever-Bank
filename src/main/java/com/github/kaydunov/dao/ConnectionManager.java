package com.github.kaydunov.dao;

import com.github.kaydunov.exception.DatabaseConfigurationException;
import com.github.kaydunov.util.YamlReader;
import lombok.extern.java.Log;

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

    //todo сделать пул connection
    static {
        try {
            Map<String, Object> yamlMap = YamlReader.readYaml(YAML_FILE_PATH);
            // Get JDBC driver configuration
            PROPERTIES.putAll((Map<String, String>) yamlMap.get("db"));

            String driverName = PROPERTIES.getProperty("driver");
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            throw new DatabaseConfigurationException("Incorrect database driver", e);
        }
        DATABASE_URL = PROPERTIES.getProperty("url");

    }

    private ConnectionManager() {
    }


    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DATABASE_URL, PROPERTIES);
        } catch (SQLException e) {
            throw new DatabaseConfigurationException(e);
        }
    }
}
