/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ss.config.js;

import java.util.HashMap;

/**
 *
 * @author user
 */
public interface ConfJsAppFactory_I 
{
    public ConfJsApp newObj(HashMap<String, ConfJsDbFactory_I> factoriesDb);
}
