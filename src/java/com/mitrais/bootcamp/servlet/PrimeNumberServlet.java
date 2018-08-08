/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mitrais.bootcamp.servlet;

import com.mitrais.bootcamp.helper.PrimeList;
import com.mitrais.bootcamp.helper.ServletUtilities;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Aditia_RS458
 */
public class PrimeNumberServlet extends HttpServlet {

    private final List<PrimeList> primeListCollection = new ArrayList<>();
    private final int MAX_PRIME_LIST = 30;

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
        int numPrimes = ServletUtilities.getIntParameter(request, "numPrimes", 50);
        int numDigits = ServletUtilities.getIntParameter(request, "numDigits", 120);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        PrimeList primeList = findPrimeList(primeListCollection, numPrimes, numDigits);
        if (primeList == null) {
            primeList = new PrimeList(numPrimes, numDigits, true);
            // Multiple servlet request threads share the instance
            // variables (fields) of PrimeNumbers. So
            // synchronize all access to servlet fields.
            synchronized (primeListCollection) {
                if (primeListCollection.size() >= MAX_PRIME_LIST) {
                    primeListCollection.remove(0);
                }
                primeListCollection.add(primeList);
            }
        }
        List<BigInteger> currentPrimes = primeList.getPrimes();
        int numCurrentPrimes = currentPrimes.size();
        int numPrimesRemaining = (numPrimes - numCurrentPrimes);
        boolean isLastResult = (numPrimesRemaining == 0);
        if (!isLastResult) {
            response.setIntHeader("Refresh", 5);
        }

        String title = "Some " + numDigits + "-Digit Prime Numbers";
        out.println(ServletUtilities.headWithTitle(title)
                + "<BODY BGCOLOR=\"#FDF5E6\">\n"
                + "<H2 ALIGN=CENTER>" + title + "</H2>\n"
                + "<H3>Primes found with " + numDigits
                + " or more digits: " + numCurrentPrimes
                + ".</H3>");
        if (isLastResult) {
            out.println("<B>Done searching.</B>");
        } else {
            out.println("<B>Still looking for " + numPrimesRemaining + " more<BLINK>...</BLINK></B>");
        }
        out.println("<OL>");
        for (int i = 0; i < numCurrentPrimes; i++) {
            out.println("  <LI>" + currentPrimes.get(i));
        }
        out.println("</OL>");
        out.println("</BODY></HTML>");
    }

    private PrimeList findPrimeList(List<PrimeList> primeListCollection, int numPrimes, int numDigits) {
        synchronized (primeListCollection) {
            for (PrimeList primes : primeListCollection) {
                if ((numPrimes == primes.numPrimes())
                        && (numDigits == primes.numDigits())) {
                    return (primes);
                }
            }
            return (null);
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
