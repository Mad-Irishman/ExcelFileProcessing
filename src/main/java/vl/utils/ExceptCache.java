/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vl.utils;

/**
 *  Exception when read or parse config file occurs
 * @author maxim
 */
public class ExceptCache extends Exception{

    public ExceptCache(String message) {
        super(message);
    }

    public ExceptCache(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptCache(Throwable cause) {
        super(cause);
    }

}