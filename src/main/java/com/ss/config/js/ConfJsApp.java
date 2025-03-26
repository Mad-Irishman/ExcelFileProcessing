/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ss.config.js;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author user
 */
public abstract class ConfJsApp extends ConfJsObj
{
    protected static final String ATTR_NAME = "name";    
    protected static final String OBJECT_TYPE_DB = "db";    
    
    protected String name;
    //protected String conf;
    protected ArrayList<ConfJsDb> dbs = new ArrayList<>();
    protected HashMap<String, ConfJsDb> dbsMapRefName = new HashMap<>();
    protected HashMap<String, ConfJsDbFactory_I> factoriesDb;

    public ConfJsApp(String dbName, ConfJsDbFactory_I factoryDb) {
        this.factoriesDb = new HashMap<>();
        this.factoriesDb.put(dbName, factoryDb);
    }

    public ConfJsApp(HashMap<String, ConfJsDbFactory_I> factoriesDb) {
        this.factoriesDb = factoriesDb;
    }

    public ConfJsApp(ConfJsApp p_kCopy) 
    {
        name = p_kCopy.name;
        //conf = p_kCopy.conf;
        dbs = (ArrayList<ConfJsDb>) p_kCopy.dbs.clone();
        dbsMapRefName = (HashMap<String, ConfJsDb> ) p_kCopy.dbsMapRefName.clone();
        factoriesDb = p_kCopy.factoriesDb;
    }
    
    @Override
    protected String getObjName(){return "app name '" + name + "'";}
    
    @Override
    protected void initSelf(JsonNode p_xParser) throws ExceptCJsUnsupported 
    {
        name = getStringRequired(p_xParser, "name");
        JsonNode aDbs = p_xParser.path("dbs");
        if(aDbs == null)
            return;
        for (JsonNode xDb : p_xParser.path("dbs"))
        {
            String sName = getStringRequired(xDb, ATTR_NAME);
            ConfJsDbFactory_I kFactory = factoriesDb.get(sName);
            if(kFactory == null)
                throw new ExceptCJsNoSuchFactory("App", sName);
            ConfJsDb kDb = kFactory.newObj();            
            kDb.initObj(xDb);
            dbs.add(kDb);
            dbsMapRefName.put(kDb.getNameRef(), kDb);
        }  
        initApp(p_xParser);
    }
    
    protected abstract void initApp(JsonNode p_xParser) throws ExceptCJsUnsupported;

    public String getName() {
        return name;
    }

//    public String getConf() {
//        return conf;
//    }

    public ConfJsDb getDb(String p_sNameRef) throws ExceptCJsNoObject
    {
        ConfJsDb kRes = dbsMapRefName.get(p_sNameRef);
        if(kRes != null)
            return kRes;
        throw new ExceptCJsNoObject(OBJECT_TYPE_DB, p_sNameRef);
    }
    
    
}
