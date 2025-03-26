/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ss.config.js;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * using jackson-databind 2.14.1
 * @author user
 */
public class ConfJs extends ConfJsObj
{
    protected static final String ATTR_NAME = "name";    
    protected static final String OBJECT_TYPE_APP = "app";
    
    public static final String STATE_NEW = "new";
    public static final String STATE_OK = "ok";
    public static final String STATE_ERROR = "error";
    
    protected String state = STATE_NEW;
    protected ArrayList<ConfJsApp> apps = new ArrayList<>();
    protected HashMap<String, ConfJsApp> appsMap = new HashMap<>();
    protected HashMap<String, ConfJsAppFactory_I> factoriesApp;
    protected HashMap<String, ConfJsDbFactory_I> factoriesDb;

    public ConfJs(String appName, ConfJsAppFactory_I factoryApp, String dbName, ConfJsDbFactory_I factoryDb) {
        this.factoriesApp = new HashMap<>();
        this.factoriesApp.put(appName, factoryApp);
        this.factoriesDb = new HashMap<>();
        this.factoriesDb.put(dbName, factoryDb);
    }

    public ConfJs(String appName, ConfJsAppFactory_I factoryApp) {
        this.factoriesApp = new HashMap<>();
        this.factoriesApp.put(appName, factoryApp);
        this.factoriesDb = ConfJsDb.knownDb;
    }

    public ConfJs(HashMap<String, ConfJsAppFactory_I> factoriesApp, HashMap<String, ConfJsDbFactory_I> factoriesDb) {
        this.factoriesApp = factoriesApp;
        this.factoriesDb = factoriesDb;
    }
    
    public void load(String ... p_asPathes) throws FileNotFoundException, ExceptCJsUnsupported
    {
        for(String sPath : p_asPathes)
        {
            try
            {
                load(sPath);
                return;
            }
            catch(ExceptCJsWrongFormat ex)
            {
                Logger.getLogger(this.getClass().getName()).info("Not JSON conf file " + sPath);
            }
            catch(IOException ex)
            {
                Logger.getLogger(this.getClass().getName()).info("Cant' read conf file " + sPath);
            }
        }
        // todo replace FileNotFoundException
        throw new FileNotFoundException("Can't find any file: " + Arrays.toString(p_asPathes) + " path is " + Paths.get(".").toAbsolutePath().normalize().toString());
    }
    
    public void load(String p_sPath) throws IOException, ExceptCJsUnsupported, ExceptCJsWrongFormat
    {
        state = STATE_ERROR;
        apps.clear();
        appsMap.clear();

        Reader xReader = new BufferedReader(new FileReader(p_sPath, Charset.forName("UTF-8")));

        ObjectMapper xMapper = new ObjectMapper();

        JsonNode xParser;
        try
        {
            xParser = xMapper.readTree(xReader);
        }
        catch(IOException ex)
        {
            throw new ExceptCJsWrongFormat("json conf file", ex);
        }
        initObj(xParser);
        xReader.close();
        state = STATE_OK;
    }
    
    @Override
    protected void initSelf(JsonNode xParser) throws ExceptCJsUnsupported
    {
        for (JsonNode xApp : xParser.path("apps"))
        {
            String sName = getStringRequired(xApp, ATTR_NAME);
            ConfJsAppFactory_I kFactory = factoriesApp.get(sName);
            if(kFactory == null)
                throw new ExceptCJsNoSuchFactory("App", sName);
            ConfJsApp kApp = kFactory.newObj(factoriesDb);
            kApp.initObj(xApp);
            apps.add(kApp);
            appsMap.put(kApp.getName(), kApp);
        }      
    }
    
    public ConfJsApp getApp(String p_sName) throws ExceptCJsNoObject
    {
        ConfJsApp kRes = appsMap.get(p_sName);
        if(kRes != null)
            return kRes;
        throw new ExceptCJsNoObject(OBJECT_TYPE_APP, p_sName);
    }
    
}

/*using format v 1.0

{
	"version": "1.0",
	"version_min": "1.0",
	"comments": "conf file for pg_speed prod",
	"apps":
	[
		{
			"version": "1.0",
			"version_min": "1.0",
			"comments": "prod",			
			"name": "pg_speed",
			"dbs":
			[
				{
					"version": "1.0",
					"version_min": "1.0",
					"comments": "PROD postgresql connection",
					"type": "postgresql",
					"name_ref": "postgresql",
					"host": "localhost",
					"port": "5432",
					"db" : "postgres",
					"schema" : "speed",
					"user" : "postgres",
					"password" : "1"
				}
			]
		}
	]
}
*/