/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mitrais.bootcamp.helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Aditia_RS458
 */
public class GZipUtilities {

    public static boolean isGzipSupported(HttpServletRequest request) {
        String encodings = request.getHeader("Accept-Encoding");
        return ((encodings != null) && (encodings.contains("gzip")));
    }

    public static boolean isGzipDisabled(HttpServletRequest request) {
        String flag = request.getParameter("disableGzip");
        return ((flag != null) && (!flag.equalsIgnoreCase("false")));
    }

    public static PrintWriter getGzipWriter(HttpServletResponse response) throws IOException {
        return (new PrintWriter(new GZIPOutputStream(response.getOutputStream())));
    }
}
