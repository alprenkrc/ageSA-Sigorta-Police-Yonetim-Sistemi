package com.example.user_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;

@Configuration
public class DatabaseConnectionTest {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    CommandLineRunner testDatabaseConnection() {
        return args -> {
            try {
                System.out.println("Testing database connection...");
                System.out.println("URL: " + url);
                System.out.println("Username: " + username);
                Connection connection = DriverManager.getConnection(url, username, password);
                System.out.println("Database connection successful!");
                connection.close();
            } catch (Exception e) {
                System.out.println("Database connection failed!");
                System.out.println("Error: " + e.getMessage());
                // Don't rethrow the exception so the application can continue starting up
            }
        };
    }
}
