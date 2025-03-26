/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ss.config.js;

/**
 *
 * @author user
 */
public class ExceptCJsUnsupportedVersion extends ExceptCJsUnsupported
{
    private String objectType;
    private String version;

    public ExceptCJsUnsupportedVersion(String objectType, String version) {
        super("Unsupported object (" + objectType + ") verions " + version);
        this.version = version;
        this.objectType = objectType;
    }
 
    
}
