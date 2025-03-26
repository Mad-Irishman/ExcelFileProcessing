/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ss.config.js;
import com.fasterxml.jackson.databind.JsonNode;

/**
 *
 * @author user
 */
public class ConfJsDbPostgre extends ConfJsDb
{
    public static final String DB_NAME = "postgresql";
    
    protected String host;
    protected int port;
    protected String db;
    protected String schema;

    public ConfJsDbPostgre() {
    }

    public ConfJsDbPostgre(ConfJsDb p_kCopy) 
    {
        super(p_kCopy);
    }
    
    @Override
    protected void initDb(JsonNode p_xParser) throws ExceptCJsUnsupported 
    {
        host = p_xParser.path("host").asText();
        port = p_xParser.path("port").asInt();
        db = p_xParser.path("db").asText();
        schema = p_xParser.path("schema").asText();
    }  
    
    @Override
    public String getUrl()
    {
        return "jdbc:postgresql://" + host + ":" + port +"/" + db;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getDb() {
        return db;
    }

    public String getSchema() {
        return schema;
    }
    
    
}
