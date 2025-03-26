/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ss;

/**
 * @author VL
 */
public class Except4Support extends Except {
    private static final String MEG_INTERNAL_ERROR = "Внутренняя ошибка";
    public static final String ENG_INTERNAL_ERROR = "Internal error";

    public Except4Support(String errorCode, String message, String extendedMessage, Throwable cause) {
        super(errorCode, message, extendedMessage, cause);
    }

    public Except4Support(String errorCode, String message, String extendedMessage) {
        super(errorCode, message, extendedMessage, null);
    }

    public Except4Support(String errorCode, String extendedMessage, Throwable cause) {
        super(errorCode, MEG_INTERNAL_ERROR, extendedMessage, cause);
    }

    public Except4Support(String errorCode, String extendedMessage) {
        super(errorCode, MEG_INTERNAL_ERROR, extendedMessage, null);
    }

}