/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilitaire;

import Model.DetailsSource_energie;
import Model.Presence_salle;
import Model.Prevision;
import Model.Puissance_solaire;
import Model.Salle;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tohy
 */
public class Util {
    public static void checkMeteo(Connection connection, Date date) throws Exception {
        List<Puissance_solaire> ans = new ArrayList<>();
        String sql = "select * from puissance_solaire where date = '"+date+"'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Date datee = resultSet.getDate("date");
                Time h_debut = resultSet.getTime("h_debut");
                Time h_fin = resultSet.getTime("h_fin");
                double niveau = resultSet.getDouble("niveau");

                Puissance_solaire ps = new Puissance_solaire(id, datee, h_debut, h_fin, niveau);
                ans.add(ps);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            throw e;
        }
        if (ans.isEmpty()) {
            throw new Exception("Meteo du date non definie");
        }
    }
    
    /*public static Time getHeureCoupure(Connection c, double nbrMatin, double nbrPM, DetailsSource_energie src, double conso, Date datecoupure) throws Exception {
        double nbrEtu = nbrMatin;
        double newBat = src.getCapacite();
        double bat = src.getCapacite();
        int min = 0;
        int hr = 0;
        for (int i = 8; i < 17; i++) {
            System.out.println("Batterie : "+newBat);
            if (newBat > (src.getCapacite()/2)) {
                if (i>=12) {
                    nbrEtu = nbrPM;
                }
                Time deb = Time.valueOf("00:"+i+":00");
                Time fin = Time.valueOf("00:+"+(i+1)+":00");
                double nivLum = Puissance_solaire.getMeteoHeure(c, datecoupure, deb, fin).get(0).getNiveau();

                double percent = (nivLum*100)/10;
                double puissanceSolaire = (src.getPuissance()*percent)/100;
                System.out.println("Puissance Solaire "+i+" : "+puissanceSolaire);
                
                double totalConso = conso*nbrEtu;
                
                System.out.println("Consommation :"+totalConso);

                if (puissanceSolaire < totalConso) {
                    double reste = totalConso-puissanceSolaire;
                    newBat = bat - reste;
                    if (newBat < (src.getCapacite()/2)) {
                        reste = (src.getCapacite()/2)-newBat;
                        double newTotalConso = totalConso-reste;
                        min = (int) ((newTotalConso*60)/totalConso);
                        hr = i;
                    }
                    bat =  newBat;
                }
            }
        }
        
        if (hr == 0 && min == 0) {
            return null;
        }
        
        return Time.valueOf(hr+":"+min+":00");
    }*/
    
    public static double generateNumberWithZeros(int nbr_zero) {
        double result;
        double divisor = 1.0;
        
        for (int i = 0; i < nbr_zero; i++) {
            divisor *= 10.0;
        }
        
        result = 1.0 / divisor;
        
        return result;
    }
    
    public static double calculeConsommation(Connection c, double nbrMatin, double nbrPM, DetailsSource_energie src, Date datecoupure, Time coupure_vraie) throws Exception {
        double min = 0, discretion = 1, max = min+discretion;
        Time coupure_temp = Prevision.getHeureCoupure(c, nbrMatin, nbrPM, src, max, datecoupure).getCoupure();
        double last_max;
        int nbr_zero = 1;
        if (coupure_vraie != null) {
            while (true) {                
                last_max = max;
                if (coupure_temp == null) {
                    min = max;
                } else {
                    if (coupure_temp.getTime() > coupure_vraie.getTime()) {
                        min = max;
                    } else {
                        discretion = generateNumberWithZeros(nbr_zero);
                        nbr_zero++;
                    }
                    if (coupure_temp.getTime() == coupure_vraie.getTime()) {
                        return max;
                    }
                }
                max = min+discretion;
                coupure_temp = Prevision.getHeureCoupure(c, nbrMatin, nbrPM, src, max, datecoupure).getCoupure();
                if (last_max == max) {
                    return max;
                }
                //System.out.println("Coupure Temp : "+coupure_temp);
            }
        } else {
            while (true) {         
                //System.out.println("Coupure Temp Null");       
                coupure_temp = Prevision.getHeureCoupure(c, nbrMatin, nbrPM, src, max, datecoupure).getCoupure();
                if (coupure_temp == null) {
                    min = max;
                } else {
                    return min;
                }
                max = max + discretion;
            }
        }
    }
    
    public static String generateCondOrSalle(List<Salle> salle) throws Exception {
        if (salle.size() == 0) {
            throw new Exception("Liste Salle Null");
        }
        String ans = "idsalle = '"+salle.get(0).getIdsalle()+"'";
        for (int i = 1; i < salle.size(); i++) {
            ans += " OR idsalle = '"+salle.get(i).getIdsalle()+"'";
        }
        return ans;
    }
    
    public static double getMoyenne(List<Presence_salle> presence, int partie) {
        double nbr = 0;
        int divisor = 0;

        for (Presence_salle presence_salle : presence) {
            if(presence_salle.getPartie() == partie) {
                nbr+=presence_salle.getNbr_personne();
                divisor++;
            }
        }
        double moyenne = nbr/divisor;
        return moyenne;
    }
    
    public static void dispatcher(HttpServletRequest request, HttpServletResponse response, String body) throws ServletException, IOException {
        RequestDispatcher re1 = request.getRequestDispatcher("/EcranPrevision.jsp");
        re1.include(request, response);
        RequestDispatcher re2 = request.getRequestDispatcher("/EcranPrevision.jsp");
        re2.include(request, response);
        RequestDispatcher re3 = request.getRequestDispatcher("/EcranPrevision.jsp");
        re3.include(request, response);
        
    }
}
