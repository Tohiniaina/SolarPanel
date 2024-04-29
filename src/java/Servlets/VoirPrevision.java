/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DataConnect.Dbconnect;
import Model.DetailsPrevision;
import Model.DetailsSource_energie;
import Model.Presence_salle;
import Model.Salle;
import Model.Prevision;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Tohy
 */
public class VoirPrevision extends HttpServlet {

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
        LocalDate date =  LocalDate.parse(request.getParameter("datecoupure"));
        Date datesql =  Date.valueOf(request.getParameter("datecoupure"));
        
        out.print(idsource);
        
        int dayofweek = date.getDayOfWeek().getValue();
        
        Dbconnect db = new Dbconnect();
        try {
            Connection c = db.ConnectPostgres();
            
            DetailsSource_energie src = DetailsSource_energie.getSourceById(c, idsource);
            double puissance_moyenne = src.getMoyennePuissanceUtiliser(c, datesql);
            
            List<Salle> salleSrc = Salle.getSalleBySource(c, idsource);
            double nbrMaraina = 300;
            double nbrAriva = 280;
            //double nbrMaraina = Presence_salle.getPresencePartie(c, datesql, idsource, 0);
            //double nbrAriva = Presence_salle.getPresencePartie(c, datesql, idsource, 1);
            
            Prevision previ = Prevision.getHeureCoupure(c, nbrMaraina, nbrAriva, src, puissance_moyenne, datesql);
            request.setAttribute("prevision", previ);
            
            List<DetailsPrevision> dtp = DetailsPrevision.getDetailsPrevision(c, nbrMaraina, nbrAriva, src, puissance_moyenne, datesql);
            request.setAttribute("detailsPrevision", dtp);
            
            RequestDispatcher dp = getServletContext().getRequestDispatcher("/EcranPrevision.jsp");
            dp.forward(request, response);
        } catch (Exception ex) {
            out.print(ex.getMessage());
            ex.printStackTrace();
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
