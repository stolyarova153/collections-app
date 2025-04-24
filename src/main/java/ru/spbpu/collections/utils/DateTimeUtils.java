package ru.spbpu.collections.utils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;

public final class DateTimeUtils {

    public static final String DATE_TIME_FORMAT_SHORT = "dd.MM.yyyy HH:mm";

    public static Date now() {
        return new GregorianCalendar().getTime();
    }

    public static Timestamp toTimestamp(final Date date) {
        return date == null ? null : new Timestamp(date.getTime());
    }
}
