/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mitrais.bootcamp.helper;

import javax.servlet.http.Cookie;

/**
 *
 * @author Aditia_RS458
 */
public class LongLivedCookie extends Cookie{
    public static final int SECONDS_PER_YEAR = 60* 60 * 24 * 365;

    public LongLivedCookie(String name, String value) {
        super(name, value);
        setMaxAge(SECONDS_PER_YEAR);
    }
    
}
