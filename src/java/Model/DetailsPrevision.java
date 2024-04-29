/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Tohy
 */
public class DetailsPrevision {
    Time h_debut;
    Time h_fin;
    double capacite_batterie;
    double consommation;
    double puissance_solaire;
    double niv_lum;

    public DetailsPrevision(Time h_debut, Time h_fin, double capacite_batterie, double consommation, double puissance_solaire, double niv_lum) {
        this.h_debut = h_debut;
        this.h_fin = h_fin;
        this.capacite_batterie = capacite_batterie;
        this.consommation = consommation;
        this.puissance_solaire = puissance_solaire;
        this.niv_lum = niv_lum;
    }

    public Time getH_debut() {
        return h_debut;
    }

    public void setH_debut(Time h_debut) {
        this.h_debut = h_debut;
    }

    public Time getH_fin() {
        return h_fin;
    }

    public void setH_fin(Time h_fin) {
        this.h_fin = h_fin;
    }

    public double getCapacite_batterie() {
        return capacite_batterie;
    }

    public void setCapacite_batterie(double capacite_batterie) {
        this.capacite_batterie = capacite_batterie;
    }

    public double getConsommation() {
        return consommation;
    }

    public void setConsommation(double consommation) {
        this.consommation = consommation;
    }

    public double getPuissance_solaire() {
        return puissance_solaire;
    }

    public void setPuissance_solaire(double puissance_solaire) {
        this.puissance_solaire = puissance_solaire;
    }

    public double getNiv_lum() {
        return niv_lum;
    }

    public void setNiv_lum(double niv_lum) {
        this.niv_lum = niv_lum;
    }
    
    public static List<DetailsPrevision> getDetailsPrevision(Connection c, double nbrMatin, double nbrPM, DetailsSource_energie src, double conso, Date datecoupure) throws Exception {
        List<DetailsPrevision> ans = new ArrayList<>();
        
        double nbrEtu = nbrMatin;
        double newBat = src.getCapacite()/2;
        double bat = src.getCapacite()/2;
        double min = 0;
        int hr = 0;
        List<Puissance_solaire> AllMeteo = Puissance_solaire.getMeteo(c, datecoupure);
        for (Puissance_solaire met : AllMeteo) {
            //System.out.println("Batterie : "+newBat);
            
            if (met.getH_debut().getHours()>=14) {
                nbrEtu = nbrPM;
            }
            
            Time deb = met.getH_debut();
            Time fin = met.getH_fin();
            
            double nivLum = Puissance_solaire.getMeteoHeure(c, datecoupure, deb, fin).get(0).getNiveau();

            double percent = (nivLum*100)/10;
            double puissanceSolaire = (src.getPuissance()*percent)/100;
            
            double totalConso = conso*nbrEtu;
            if (newBat > 0) {
                //System.out.println("Consommation :"+totalConso);
                
                if (puissanceSolaire < totalConso) {
                    double reste = totalConso-puissanceSolaire;
                    if ((newBat - reste)>0) {
                        newBat = newBat - reste;
                    } else {
                        min = (newBat/reste)*60;
                        hr = met.getH_debut().getHours();
                        //System.out.println("Heure Mlay : "+hr);
                        newBat = newBat - reste;
                    }
                } else {
                    if (newBat < (src.getCapacite()/2)) {
                        double surplus = puissanceSolaire-totalConso;
                        if ((newBat+surplus) > (src.getCapacite()/2)) {
                            newBat = src.getCapacite()/2;
                        } else {
                            newBat+=surplus;
                        }
                    }   
                }
            }
            double capbat = newBat;
            if (capbat < 0) {
                capbat = 0;
            }
            DetailsPrevision dp = new DetailsPrevision(deb, fin, capbat, totalConso, puissanceSolaire, nivLum);
            ans.add(dp);
        }
        
        return ans;
    }
}
