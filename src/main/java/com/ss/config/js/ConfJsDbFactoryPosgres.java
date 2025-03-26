/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ss.config.js;

/**
 *
 * @author user
 */
public class ConfJsDbFactoryPosgres implements ConfJsDbFactory_I
{
    public static final String DB_NAME = ConfJsDbPostgre.DB_NAME;

    private static ConfJsDbFactoryPosgres instance = new ConfJsDbFactoryPosgres();
    public static ConfJsDbFactoryPosgres getInstance() {
        return instance;
    }

    @Override
    public ConfJsDb newObj()
    {
        return new ConfJsDbPostgre();
    }

    
}
