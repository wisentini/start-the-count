package br.dev.wisentini.startthecount.backend.util;

import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class StringUtil {

    public static Integer toInteger(@NonNull String string) {
        return Integer.parseInt(string);
    }

    public static LocalDate toLocalDate(String date, String format) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
    }

    public static LocalTime toLocalTime(String time, String format) {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern(format));
    }
}
