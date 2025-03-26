/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ss.config.js;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashMap;

/**
 *
 * @author user
 */
public class ConfJsAppEmpty extends ConfJsApp
{

    public ConfJsAppEmpty(String dbName, ConfJsDbFactory_I factoryDb) {
        super(dbName, factoryDb);
    }

    public ConfJsAppEmpty(HashMap<String, ConfJsDbFactory_I> factoriesDb) {
        super(factoriesDb);
    }
    

    @Override
    protected void initApp(JsonNode p_xParser) throws ExceptCJsUnsupported {}
    
    
}
