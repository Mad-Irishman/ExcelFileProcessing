/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vl.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author vlitenko
 */
public class UtilsDbPg {

    public static final SimpleDateFormat DATE = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat DATE_TIME = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.S");
    public static final DateTimeFormatter LOCALDATE = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final DateTimeFormatter DATE_TIME_TIME = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.S");

    public static String sql2Date(LocalDate p_dDate) {
        return "to_date('" + LOCALDATE.format(p_dDate) + "', 'dd.MM.yyyy')";
    }

    public static String sql2DateTime(LocalDate p_dDate) {
        return "to_date('" + DATE_TIME_TIME.format(p_dDate) + "', 'dd.MM.yyyy hh24:mi:ss')";
    }

    public static String sql2DateTime(LocalDateTime p_dDate) {
        return "to_date('" + DATE_TIME_TIME.format(p_dDate) + "', 'dd.MM.yyyy hh24:mi:ss')";
    }

    public static String sql2DateTime(Date p_dDate) {
        return "to_date('" + DATE_TIME.format(p_dDate) + "', 'dd.MM.yyyy hh24:mi:ss')";
    }
}
