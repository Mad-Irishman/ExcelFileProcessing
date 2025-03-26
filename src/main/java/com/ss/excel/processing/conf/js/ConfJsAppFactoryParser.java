/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ss.excel.processing.conf.js;

import com.ss.config.js.ConfJsApp;
import com.ss.config.js.ConfJsAppFactory;
import com.ss.config.js.ConfJsDbFactory_I;
import java.util.HashMap;

/**
 *
 * @author vlitenko
 */
import java.util.HashMap;

public class ConfJsAppFactoryParser extends ConfJsAppFactory {

    private static final ConfJsAppFactoryParser instance = new ConfJsAppFactoryParser();

    public static ConfJsAppFactoryParser getInstance() {
        return instance;
    }

    @Override
    public ConfJsApp newObj(HashMap<String, ConfJsDbFactory_I> factoriesDb) {
        return new ConfJsAppParser();
    }
}