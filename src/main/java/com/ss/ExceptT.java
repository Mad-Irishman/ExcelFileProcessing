/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ss;

/**
 * @author Vl
 */
public class ExceptT extends Exception implements Except_I {
    protected ExceptDto data;

    public ExceptT(String errorCode, String message, String extendedMessage, Throwable cause) {
        super(message, cause);
        data = new ExceptDto(errorCode, message, extendedMessage);
    }

    public ExceptT(String errorCode, String message, String extendedMessage) {
        this(errorCode, message, extendedMessage, null);
    }

    @Override
    public String toString() {
        return getMessage4Support();
    }

    @Override
    public String getMessage4Support() {
        return data.getMessage4Support(this);
    }

    @Override
    public String getMessage4User() {
        return data.getMessage4User(this);
    }

    @Override
    public long getId() {
        return data.getId();
    }

    public String getCodeId() {
        return data.getCodeId();
    }

    public String getMessage4Monitor() {
        return data.getMessage4Monitor(this);
    }
}
