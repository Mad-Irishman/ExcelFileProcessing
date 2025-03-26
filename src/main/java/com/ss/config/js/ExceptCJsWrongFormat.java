/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ss.config.js;

/**
 *
 * @author user
 */
public class ExceptCJsWrongFormat extends Exception
{
    private String objectType;

    public ExceptCJsWrongFormat(String objectType, Throwable cause) {
        super("Wrong format of " + objectType, cause);
        this.objectType = objectType;
    }
    
    public ExceptCJsWrongFormat(String objectType) {
        super("Wrong format of " + objectType);
        this.objectType = objectType;
    }
 
    
}
