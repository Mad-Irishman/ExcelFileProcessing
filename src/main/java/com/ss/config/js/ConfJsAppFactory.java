/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ss.config.js;

import java.util.HashMap;

/**
 *
 * @author user
 */
public class ConfJsAppFactory implements ConfJsAppFactory_I
{
    private static ConfJsAppFactory instance = new ConfJsAppFactory();

    public static ConfJsAppFactory getInstance() {
        return instance;
    }
    
    @Override
    public ConfJsApp newObj(HashMap<String, ConfJsDbFactory_I> factoriesDb) {
        return new ConfJsAppEmpty(factoriesDb);
    }
    
}
