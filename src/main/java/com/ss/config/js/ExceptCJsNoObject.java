/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ss.config.js;

/**
 *
 * @author user
 */
public class ExceptCJsNoObject extends Exception
{
    private String objectType;
    private String name;

    public ExceptCJsNoObject(String objectType, String name) {
        super("Object type " + objectType + " not found by name " + name);
        this.objectType = objectType;
        this.name = objectType;
    }
 
    
}
