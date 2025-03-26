/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ss.config.js;

/**
 *
 * @author user
 */
public class ExceptCJsNoSuchFactory extends ExceptCJsUnsupported
{
    private String factoryType;
    private String name;

    public ExceptCJsNoSuchFactory(String factoryType, String name) {
        super("No factory type " + factoryType + " for name " + name);
        this.factoryType = factoryType;
        this.name = name;
    }
    
}
