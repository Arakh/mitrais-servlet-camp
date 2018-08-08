/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mitrais.bootcamp.servlet;

import com.mitrais.bootcamp.helper.MessageImage;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Aditia_RS458
 */
public class CreateImageServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String wantsList = request.getParameter("showList");
        if (wantsList != null) {
            showFontList(response);
        } else {
            String message = request.getParameter("message");
            if ((message == null) || (message.length() == 0)) {
                message = "Missing 'message' parameter \nSo what is that?";
            }
            String fontName = request.getParameter("fontName");
            if ((fontName == null) || (fontName.length() == 0)) {
                fontName = "Serif";
            }
            String fontSizeString = request.getParameter("fontSize");
            int fontSize;
            try {
                fontSize = Integer.parseInt(fontSizeString);
            } catch (NumberFormatException nfe) {
                fontSize = 90;
            }
            response.setContentType("image/jpeg");
            MessageImage.writeJPEG(MessageImage.makeMessageImage(message,
                    fontName,
                    fontSize),
                    response.getOutputStream());
        }
    }

    private void showFontList(HttpServletResponse response)
            throws IOException {
        PrintWriter out = response.getWriter();
        String docType
                = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 "
                + "Transitional//EN\">\n";
        String title = "Fonts Available on Server";
        out.println(docType
                + "<HTML>\n"
                + "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n"
                + "<BODY BGCOLOR=\"#FDF5E6\">\n"
                + "<H1 ALIGN=CENTER>" + title + "</H1>\n"
                + "<UL>");
        String[] fontNames = MessageImage.getFontNames();
        for (int i = 0; i < fontNames.length; i++) {
            out.println("  <LI>" + fontNames[i]);
        }
        out.println("</UL>\n"
                + "</BODY></HTML>");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
