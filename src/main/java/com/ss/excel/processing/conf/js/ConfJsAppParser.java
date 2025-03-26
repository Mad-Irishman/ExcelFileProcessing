/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ss.excel.processing.conf.js;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ss.config.js.ConfJsApp;
import com.ss.config.js.ConfJsDb;
import com.ss.config.js.ExceptConf;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ConfJsAppParser extends ConfJsApp {

    private String nameServer;
    private String serverType;
    private int executorPoolSize;
    private String downloadPathUpload;
    private String downloadPathProcessing;

    public static final String SERVER_TYPE_DEV = "dev";
    public static final String SERVER_TYPE_TEST = "test";

    public ConfJsAppParser() {
        super(ConfJsDb.knownDb);
    }

    @Override
    protected void initApp(JsonNode p_xParser) throws ExceptConf {
        try {
            nameServer = getStringRequired(p_xParser, "name");
            serverType = getStringRequired(p_xParser, "server_type");
            executorPoolSize = getIntRequired(p_xParser, "executor_pool_size");
            downloadPathUpload = getStringRequired(p_xParser, "download_path_upload");
            downloadPathProcessing = getStringRequired(p_xParser, "download_path_processing");
        } catch (RuntimeException ex) {
            throw new ExceptConf("ErrConfA1", "Can't process project configuration",
                    ex.getMessage(), ex);
        }
    }

    public String getDownloadPathProcessing() {
        return downloadPathProcessing;
    }

    public String getDownloadPathUpload() {
        return downloadPathUpload;
    }

    public int getExecutorPoolSize() {
        return executorPoolSize;
    }

    public String getNameServer() {
        return nameServer;
    }

    public String getServerType() {
        return serverType;
    }

    @Override
    public String toString() {
        return "serverType" + serverType + "\n";
    }
}
