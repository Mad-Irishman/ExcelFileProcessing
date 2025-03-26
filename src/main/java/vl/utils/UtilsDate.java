/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vl.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 *
 * @author vlitenko
 */
public class UtilsDate {

    public final static Locale RUSSIAN_LOCALE = Locale.forLanguageTag("ru-RU");
    
    public static Date addDay(int days) {
        Calendar xCal = Calendar.getInstance();
        xCal.add(Calendar.DATE, days);
        return xCal.getTime();
    }

    public static Date subtractDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -days);
        return calendar.getTime();
    }

    public static Date endOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }

    public static LocalDateTime getMonthBegin(LocalDateTime dateLocal) {
        return LocalDateTime.of(dateLocal.getYear(), dateLocal.getMonthValue(), 1, 0, 0);
    }

    public static LocalDateTime getMonthEnd(LocalDateTime dateLocal) {
        LocalDateTime xMonthBegin = getMonthBegin(dateLocal);
        return xMonthBegin.plusMonths(1).minusNanos(1);
    }

    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
            .appendOptional(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.forLanguageTag("ru")))
            .appendOptional(DateTimeFormatter.ofPattern("dd-MMMM-yyyy", Locale.forLanguageTag("ru")))
            .appendOptional(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.forLanguageTag("en")))
            .appendOptional(DateTimeFormatter.ofPattern("dd-MMMM-yyyy", Locale.forLanguageTag("en")))
            .appendOptional(DateTimeFormatter.ofPattern("dd.MM.yy"))
            .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yy"))
            .appendOptional(DateTimeFormatter.ofPattern("dd-MM-yy"))
            .appendOptional(DateTimeFormatter.ofPattern("d.M.yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("d/M/yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("d-M-yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
            
            .parseDefaulting(ChronoField.YEAR_OF_ERA, LocalDate.now().getYear())
            .toFormatter(RUSSIAN_LOCALE);
    
    public static LocalDate convertDate(String inputDate) {
        try {
            LocalDate date = LocalDate.parse(inputDate, FORMATTER);
            return date;
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
