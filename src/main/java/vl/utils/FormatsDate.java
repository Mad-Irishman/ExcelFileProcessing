/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vl.utils;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Alexey S.
 */
public class FormatsDate {

    public static final String STR_DOT_DATE_TIME = "dd.MM.yyyy HH:mm:ss";
    public static final String STR_SLASH_DATE = "dd/MM/yyyy";
    public static final String STR_DASH_DATE_REVERSED = "yyyy-MM-dd";
    public static final String STR_DASH_DATE_TIME_REVERSED = "yyyy-MM-dd HH:mm:ss";
    public static final String STR_DASH_DATE_TIME_MILLIS_REVERSED = "yyyy-MM-dd HH:mm:ss.S";
    public static final String STR_DOT_DATE = "dd.MM.yyyy";
    public static final String STR_DOT_DATE_TIME_MILLIS = "dd.MM.yyyy HH:mm:ss.S";
    public static final String STR_UNDERSCORE_YEAR_MONTH_REVERSED = "yyyy_MM";
    public static final String STR_DASH_DATE_T_HOUR_MINUTE_REVERSED = "yyyy-MM-dd'T'HH:mm";

    public static final DateTimeFormatter DTF_DOT_DATE = DateTimeFormatter.ofPattern(STR_DOT_DATE);
    public static final DateTimeFormatter DTF_DASH_DATE_TIME_MILLIS_REVERSED = DateTimeFormatter.ofPattern(STR_DASH_DATE_TIME_MILLIS_REVERSED);
    public static final DateTimeFormatter DTF_DASH_DATE_TIME_REVERSED = DateTimeFormatter.ofPattern(STR_DASH_DATE_TIME_REVERSED);
    public static final DateTimeFormatter DTF_DASH_DATE_REVERSED = DateTimeFormatter.ofPattern(STR_DASH_DATE_REVERSED);
    public static final DateTimeFormatter DTF_UNDERSCORE_YEAR_MONTH_REVERSED = DateTimeFormatter.ofPattern(STR_UNDERSCORE_YEAR_MONTH_REVERSED);
    public static final DateTimeFormatter DTF_DASH_DATE_T_HOUR_MINUTE_REVERSED = DateTimeFormatter.ofPattern(STR_DASH_DATE_T_HOUR_MINUTE_REVERSED);
    public static final DateTimeFormatter DTF_DOT_DATE_TIME = DateTimeFormatter.ofPattern(STR_DOT_DATE_TIME);
    public static final SimpleDateFormat SDF_DOT_DATE_TIME_MILLIS = new SimpleDateFormat(STR_DOT_DATE_TIME_MILLIS);
    public static final SimpleDateFormat SDF_DOT_DATE_TIME = new SimpleDateFormat(STR_DOT_DATE_TIME);
    public static final SimpleDateFormat SDF_DOT_DATE = new SimpleDateFormat(STR_DOT_DATE);
    public static final SimpleDateFormat SDF_UNDERSCORE_YEAR_MONTH_REVERSED = new SimpleDateFormat(STR_UNDERSCORE_YEAR_MONTH_REVERSED);

}
