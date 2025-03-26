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
public abstract class ConfJsDb extends ConfJsObj
{
    public static final HashMap<String, ConfJsDbFactory_I> knownDb = new HashMap<>();
    static
    {
        knownDb.put(ConfJsDbFactoryPosgres.DB_NAME, ConfJsDbFactoryPosgres.getInstance());
    }
    
    protected String type;
    protected String nameRef;
    protected String user;
    protected String password;
    
    public ConfJsDb() {
    }

    
    public ConfJsDb(ConfJsDb p_kCopy) 
    {
        type = p_kCopy.getType();
        nameRef = p_kCopy.getNameRef();
        user = p_kCopy.getUser();
        password = p_kCopy.getPassword();
    }

    @Override
    protected void initSelf(JsonNode p_xParser)  throws ExceptCJsUnsupported
    {
        type = p_xParser.path("type").asText();
        nameRef = p_xParser.path("name_ref").asText();
        user = p_xParser.path("user").asText();
        password = p_xParser.path("password").asText();        
        initDb(p_xParser);
    }
    
    protected abstract void initDb(JsonNode p_xParser) throws ExceptCJsUnsupported;

    public String getType() {
        return type;
    }

    public String getNameRef() {
        return nameRef;
    }

    
    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
    
    public String getUrl()
    {
        return "";
    }
    
    
}
