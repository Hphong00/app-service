package com.app.productservice.core.utils;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static String ORCL_QUERY_DATE_TIME_FORMAT = "DD-MM-YYYY HH24:MI:SS";
    public static String DD_MM_YYYY = "dd/MM/yyyy";
    public static String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
    public static String TIME_ZONE = "Asia/Ho_Chi_Minh";
    public static DateTimeFormatter FORMATTER;

    public DateUtils() {
    }

    public static String asString(Instant instant) {
        if (instant == null) {
            return null;
        }
        return FORMATTER.format(instant);
    }

    public static String asString(Instant instant, String format) {
        if (instant == null) {
            return null;
        }
        return DateTimeFormatter.ofPattern(format)
                .withZone(ZoneId.of(TIME_ZONE))
                .format(instant);
    }

    public static String asString(ZonedDateTime zdt) {
        if (zdt == null) {
            return null;
        }
        return FORMATTER.format(zdt);
    }

    public static Instant asInstant(String str) {
        if (!StringUtils.hasText(str)) {
            return null;
        }
        return Instant.from(FORMATTER.parse(str));
    }

    public static ZonedDateTime asZonedDateTime(String str) {
        if (!StringUtils.hasText(str)) {
            return null;
        }
        return ZonedDateTime.from(FORMATTER.parse(str));
    }

    public static ZonedDateTime getStartOfDay(ZonedDateTime zdt) {
        ZoneId zoneId = ZoneId.of("Asia/Bangkok");
        return zdt.toLocalDate().atStartOfDay(zoneId);
    }

    public static ZonedDateTime getEndOfDay(ZonedDateTime zdt) {
        ZoneId zoneId = ZoneId.of("Asia/Bangkok");
        return zdt.toLocalDate().atStartOfDay(zoneId).plusDays(1L);
    }

    public static Instant asInstant(String str, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format).withZone(ZoneId.of("Asia/Bangkok"));
        return Instant.from(formatter.parse(str));
    }

    public static String convertTimeFormat(String time, String inputFormat, String outputFormat) {
        try {
            String outTime = "";
            if (inputFormat.isEmpty()) {
                inputFormat = DATE_TIME_FORMAT;
            }

            if (outputFormat.isEmpty()) {
                outputFormat = DATE_TIME_FORMAT;
            }

            SimpleDateFormat simpleDateFormatInput = new SimpleDateFormat(inputFormat);
            SimpleDateFormat simpleDateFormatOutput = new SimpleDateFormat(outputFormat);
            if (!time.isEmpty()) {
                outTime = simpleDateFormatOutput.format(simpleDateFormatInput.parse(time));
            }

            return outTime;
        } catch (ParseException var6) {
            var6.printStackTrace();
            return "";
        }
    }

    static {
        FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).withZone(ZoneId.of(TIME_ZONE));
    }
}
