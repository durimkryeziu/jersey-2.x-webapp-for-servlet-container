package com.programmingskillz.samplejerseywebapp.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author Durim Kryeziu
 */
public class DataSource extends HikariDataSource {

    private static DataSource instance;

    private DataSource() {
        super(new HikariConfig("/hikari.properties"));
    }

    public static DataSource getInstance() {
        if (instance == null || instance.isClosed()) {
            synchronized (DataSource.class) {
                if (instance == null || instance.isClosed()) {
                    instance = new DataSource();
                }
            }
        }
        return instance;
    }
}