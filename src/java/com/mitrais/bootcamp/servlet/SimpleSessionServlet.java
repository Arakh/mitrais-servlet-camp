/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mitrais.bootcamp.servlet;

import com.mitrais.bootcamp.helper.ServletUtilities;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Aditia_RS458
 */
public class SimpleSessionServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        synchronized (session) {
            String heading;
            Integer accessCount = (Integer) session.getAttribute("accessCount");
            if (accessCount == null) {
                accessCount = 1;
                heading = "Welcome abroad";
            } else {
                heading = "Weclome back";
                accessCount++;
            }
            session.setAttribute("accessCount", accessCount);

            try (PrintWriter out = response.getWriter()) {
                String title = "Simple Storing Session";
                out.println(ServletUtilities.DOCTYPE
                        + "<HTML>\n"
                        + "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n"
                        + "<BODY BGCOLOR=\"#FDF5E6\">\n"
                        + "<CENTER>\n"
                        + "<H1>" + heading + "</H1>\n"
                        + "<H2>Information on Your Session:</H2>\n"
                        + "<TABLE BORDER=1>\n"
                        + "<TR BGCOLOR=\"#FFAD00\">\n"
                        + "  <TH>Info Type<TH>Value\n"
                        + "<TR>\n"
                        + "  <TD>ID\n"
                        + "  <TD>" + session.getId() + "\n"
                        + "<TR>\n"
                        + "  <TD>Creation Time\n"
                        + "  <TD>"
                        + new Date(session.getCreationTime()) + "\n"
                        + "<TR>\n"
                        + "  <TD>Time of Last Access\n"
                        + "  <TD>"
                        + new Date(session.getLastAccessedTime()) + "\n"
                        + "<TR>\n"
                        + "  <TD>Number of Previous Accesses\n"
                        + "  <TD>" + accessCount + "\n"
                        + "</TABLE>\n"
                        + "</CENTER></BODY></HTML>"
                );
            }
        }

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
