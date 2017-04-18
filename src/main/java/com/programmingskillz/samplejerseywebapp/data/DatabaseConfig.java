package com.programmingskillz.samplejerseywebapp.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Durim Kryeziu
 */
public class DatabaseConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfig.class);

    private static HikariDataSource hikariDataSource;

    public static void init() {
        LOGGER.info("Initializing HikariCP...");

        Properties prop = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = loader.getResourceAsStream("hikari.properties");
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("IOException:", e);
        }

        HikariConfig config = new HikariConfig(prop);

        hikariDataSource = new HikariDataSource(config);
    }

    public static DataSource getDataSource() {
        return hikariDataSource;
    }

    public static void close() {
        if (hikariDataSource != null) {
            hikariDataSource.close();
        }
    }
}