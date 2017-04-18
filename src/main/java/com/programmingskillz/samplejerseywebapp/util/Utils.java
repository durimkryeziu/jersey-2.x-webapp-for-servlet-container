package com.programmingskillz.samplejerseywebapp.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * @author Durim Kryeziu
 */
public class Utils {

    public static Instant toInstant(Date date) {
        return date.toLocalDate().atStartOfDay(ZoneOffset.UTC).toInstant();
    }

    public static Date toSQLDate(Instant instant) {
        return Date.valueOf(instant.atZone(ZoneId.ofOffset("", ZoneOffset.UTC)).toLocalDate());
    }

    public static void deleteDir(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File temp : files) {
                deleteDir(temp);
            }
        }
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}