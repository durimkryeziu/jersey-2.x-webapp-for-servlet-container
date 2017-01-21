package com.programmingskillz.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Durim Kryeziu
 */
public class DataSource {

    private static HikariDataSource hikariDataSource;

    public static void init() {
        System.out.println("Initializing HikariCP...");

        Properties prop = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = loader.getResourceAsStream("hikari.properties");
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HikariConfig config = new HikariConfig(prop);
        hikariDataSource = new HikariDataSource(config);
    }

    static synchronized Connection getConnection() throws SQLException {
        return hikariDataSource != null ? hikariDataSource.getConnection() : null;
    }

    public static void close() {
        if (hikariDataSource != null) {
            hikariDataSource.close();
        }
    }
}