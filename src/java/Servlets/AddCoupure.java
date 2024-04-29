/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DataConnect.Dbconnect;
import Model.DetailsSource_energie;
import Model.Presence_salle;
import Model.Salle;
import Model.Coupure;
import Model.Prevision;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.sql.Connection;
import Utilitaire.Util;
import jakarta.servlet.RequestDispatcher;
import java.util.ArrayList;

/**
 *
 * @author Tohy
 */
public class AddCoupure extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        String idsource = request.getParameter("source");
        Date datecoupure =  Date.valueOf(request.getParameter("datecoupure"));
        String heure = request.getParameter("heurecoupure");
        LocalTime localTime = LocalTime.parse(heure, DateTimeFormatter.ofPattern("HH:mm"));
        Time heureCoupure = Time.valueOf(localTime);
        out.print("Heure Coupure Entrer : "+heureCoupure+"<br>");
        
        Dbconnect db = new Dbconnect();
        try {
            if (heureCoupure.getHours() < 8 || heureCoupure.getHours() >= 18) {
                out.print("Time error");
                throw new Exception("Heure Coupure invalide");
            } 
            Connection c = db.ConnectPostgres();
            
            DetailsSource_energie src = DetailsSource_energie.getSourceById(c, idsource);
                        
            Util.checkMeteo(c, datecoupure);
            double sommeEtuMaraina = Presence_salle.getPresencePartie(c, datecoupure, idsource, 0);
            double sommeEtuAriva = Presence_salle.getPresencePartie(c, datecoupure, idsource, 1);

            double tc = Util.calculeConsommation(c, sommeEtuMaraina, sommeEtuAriva, src, datecoupure, heureCoupure);
            
            Time heureResultat = Prevision.getHeureCoupure(c, sommeEtuMaraina, sommeEtuAriva, src, tc, datecoupure).getCoupure();
            
            Coupure cop = new Coupure(idsource, datecoupure, heureCoupure, tc, heureResultat);
            out.print(cop.getHeureResultat());
            //cop.insert(c);
            
            c.close();
            
            request.setAttribute("coupure", cop);
            RequestDispatcher dp = getServletContext().getRequestDispatcher("/ResultatCoupure.jsp");
            dp.forward(request, response);
        } catch (Exception e) {
            out.print(e.getMessage());
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
