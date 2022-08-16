package com.example.restaurant_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class DatabaseConnectionProperties {

    public static String url;
    private static String userName;
    private static String password;

    @Autowired
    public DatabaseConnectionProperties(@Value("${spring.datasource.url}") String url,
                                        @Value("${spring.datasource.username}") String userName,
                                        @Value("${spring.datasource.password}") String password) {
        DatabaseConnectionProperties.url = url;
        DatabaseConnectionProperties.userName = userName;
        DatabaseConnectionProperties.password = password;
    }

    public static Properties getConnectionProps() {
        Properties properties = new Properties();
        properties.setProperty("user", userName);
        properties.setProperty("password", password);
        return properties;
    }

}
