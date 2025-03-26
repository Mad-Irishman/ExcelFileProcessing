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

public class ConfJsAppFactoryExcel extends ConfJsAppFactory {

    private static final ConfJsAppFactoryExcel instance = new ConfJsAppFactoryExcel();

    public static ConfJsAppFactoryExcel getInstance() {
        return instance;
    }

    @Override
    public ConfJsApp newObj(HashMap<String, ConfJsDbFactory_I> factoriesDb) {
        return new ConfJsAppExcel();
    }
}