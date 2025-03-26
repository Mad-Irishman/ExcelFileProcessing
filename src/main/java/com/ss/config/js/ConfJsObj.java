/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ss.config.js;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public abstract class ConfJsObj 
{
    protected String version;
    protected String versionMin;
    protected String comments;
    
    protected abstract void initSelf(JsonNode p_xParser) throws ExceptCJsUnsupported;
    protected String getObjName(){return getClass().getName();}

    protected String getStringRequired(JsonNode p_xParser, String p_sName) throws RuntimeException
    {
        JsonNode xNode = p_xParser.path(p_sName);
            // TODO: replace RuntimeException
        if((xNode == null) || (xNode.isMissingNode()))
            throw new RuntimeException("error in json configuration, no required attribute '" + p_sName + ", " +  getObjName()+ " '" + comments + "'");
        return xNode.asText();
    } 

    protected int getIntRequired(JsonNode p_xParser, String p_sName) throws RuntimeException
    {
        JsonNode xNode = p_xParser.path(p_sName);
            // TODO: replace RuntimeException
        if(xNode == null)
            throw new RuntimeException("error in json configuration, no required attribute '" + p_sName + ", " +  getObjName()+ " '" + comments + "'");
        return xNode.asInt();
    }
    
    protected double getDoubleRequired(JsonNode p_xParser, String p_sName) throws RuntimeException
    {
        JsonNode xNode = p_xParser.path(p_sName);
            // TODO: replace RuntimeException
        if(xNode == null)
            throw new RuntimeException("error in json configuration, no required attribute '" + p_sName + ", " +  getObjName()+ " '" + comments + "'");
        return xNode.asDouble();
    }

    protected List<String> getListRequired(JsonNode parser, String name) throws RuntimeException {
        JsonNode node = parser.path(name);
        if (node.isMissingNode()) {
            throw new RuntimeException("Error in json configuration, no required attribute '" + name + "'");
        }
        List<String> list = new ArrayList<>();
        if (node.isArray()) {
            for (JsonNode itemNode : node) {
                if (itemNode.isTextual()) {
                    list.add(itemNode.asText());
                } else {
                    throw new RuntimeException("Expected a text value in array '" + name + "'");
                }
            }
        } else {
            throw new RuntimeException("Expected an array for '" + name + "'");
        }

        return list;
    }
    
    protected void initObj(JsonNode p_xParser) throws ExceptCJsUnsupported
    {
        version = p_xParser.path("version").asText();
        versionMin = p_xParser.path("version_min").asText();
        JsonNode xComments = p_xParser.path("comments");
        if(xComments != null)
            comments = xComments.asText();
        initSelf(p_xParser);
    }

    public String getVersion() {
        return version;
    }

    public String getVersionMin() {
        return versionMin;
    }

    public String getComments() {
        return comments;
    }
   
    
}
