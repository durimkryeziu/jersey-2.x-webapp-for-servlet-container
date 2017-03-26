package com.programmingskillz.util;

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
}