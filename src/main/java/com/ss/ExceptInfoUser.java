/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ss;

//import com.ss.topt.Msg;

import java.util.Map;

/**
 * not error in system, no logging, just info for user
 *
 * @author user
 */
public class ExceptInfoUser extends Exception {

    private String message4support;

    private Map<String, String> errors;


    public ExceptInfoUser(Message4User_I message) {
        super(message.toString());
    }

    public ExceptInfoUser(Message4User_I message, String message4support) {
        super(message.toString());
        this.message4support = message4support;
    }

    //    public ExceptInfoUser(String message4support) {
//        this.message4support = message4support;
//    }
    public ExceptInfoUser(Map<String, String> errors) {
        this.errors = errors;
    }

    public ExceptInfoUser(Map<String, String> errors, Throwable cause) {
        super(cause);
        this.errors = errors;
    }

    public String getErrorMessage() {
        if (errors != null && !errors.isEmpty()) {
            return errors.get("error");
        }
        return "Unknown error";
    }


    public Map<String, String> getErrors() {
        return errors;
    }
}

