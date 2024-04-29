/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 *
 * @author Tohy
 */
public class Prevision {
    Date date;
    Time coupure;
    double nbrEtuAM;
    double nbrEtuPM;
    double conso_estimer;

    public Prevision(Date date, Time coupure, double nbrEtuAM, double nbrEtuPM, double conso_estimer) {
        this.date = date;
        this.coupure = coupure;
        this.nbrEtuAM = nbrEtuAM;
        this.nbrEtuPM = nbrEtuPM;
        this.conso_estimer = conso_estimer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getCoupure() {
        return coupure;
    }

    public void setCoupure(Time coupure) {
        this.coupure = coupure;
    }

    public double getNbrEtuAM() {
        return nbrEtuAM;
    }

    public void setNbrEtuAM(double nbrEtuAM) {
        this.nbrEtuAM = nbrEtuAM;
    }

    public double getNbrEtuPM() {
        return nbrEtuPM;
    }

    public void setNbrEtuPM(double nbrEtuPM) {
        this.nbrEtuPM = nbrEtuPM;
    }

    public double getConso_estimer() {
        return conso_estimer;
    }

    public void setConso_estimer(double conso_estimer) {
        this.conso_estimer = conso_estimer;
    }
    
    /*public static Prevision getHeureCoupure(Connection c, double nbrMatin, double nbrPM, DetailsSource_energie src, double conso, Date datecoupure) throws Exception {
        double nbrEtu = nbrMatin;
        double newBat = src.getCapacite();
        double bat = src.getCapacite();
        double min = 0;
        int hr = 0;
        System.out.println("Puissance Energie: "+src.getPuissance());
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
                    if (newBat <= (src.getCapacite()/2)) {
                        reste = (src.getCapacite()/2)-newBat;
                        System.out.println("Reste : "+reste);
                        double newTotalConso = totalConso-reste;
                        System.out.println("new Conso : "+newTotalConso);
                        min = (newTotalConso*60)/totalConso;
                        hr = i;
                    }
                    bat =  newBat;
                }
            }
        }
        System.out.println("Min : "+min);
        if (hr == 0 && min == 0) {
            return new Prevision(datecoupure, null, nbrMatin, nbrPM, conso);
        }
        
        return new Prevision(datecoupure, Time.valueOf(hr+":"+(int)min+":00"), nbrMatin, nbrPM, conso);
    }*/
    
    public static Prevision getHeureCoupure(Connection c, double nbrMatin, double nbrPM, DetailsSource_energie src, double conso, Date datecoupure) throws Exception {
        double nbrEtu = nbrMatin;
        double newBat = src.getCapacite()/2;
        double bat = src.getCapacite()/2;
        double min = 0;
        int hr = 0;
        List<Puissance_solaire> AllMeteo = Puissance_solaire.getMeteo(c, datecoupure);
        //System.out.println("Consommation donner : "+conso);
        //System.out.println("Puissance Energie: "+src.getPuissance());
        for (Puissance_solaire met : AllMeteo) {
            //System.out.println("Heure Mlay : "+met.getH_debut().getHours());
            //System.out.println("Batterie : "+newBat);
            if (newBat > 0) {
                if (met.getH_debut().getHours()>=14) {
                    nbrEtu = nbrPM;
                }
                Time deb = met.getH_debut();
                Time fin = met.getH_fin();
                double nivLum = Puissance_solaire.getMeteoHeure(c, datecoupure, deb, fin).get(0).getNiveau();

                double percent = (nivLum*100)/10;
                double puissanceSolaire = (src.getPuissance()*percent)/100;
                //System.out.println("Puissance Solaire : "+puissanceSolaire);
                
                double totalConso = conso*nbrEtu;
                
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
        }
        //System.out.println("Min : "+min);
        if (hr == 0 && min == 0) {
            return new Prevision(datecoupure, null, nbrMatin, nbrPM, conso);
        }
        
        return new Prevision(datecoupure, Time.valueOf(hr+":"+(int)min+":00"), nbrMatin, nbrPM, conso);
    }
    
    public static Prevision getDetailsPrevision(Connection c, double nbrMatin, double nbrPM, DetailsSource_energie src, double conso, Date datecoupure) throws Exception {
        double nbrEtu = nbrMatin;
        double newBat = src.getCapacite()/2;
        double bat = src.getCapacite()/2;
        double min = 0;
        int hr = 0;
        List<Puissance_solaire> AllMeteo = Puissance_solaire.getMeteo(c, datecoupure);
        //System.out.println("Consommation donner : "+conso);
        //System.out.println("Puissance Energie: "+src.getPuissance());
        for (Puissance_solaire met : AllMeteo) {
            //System.out.println("Batterie : "+newBat);
            if (newBat > 0) {
                if (met.getH_debut().getHours()>=14) {
                    nbrEtu = nbrPM;
                }
                Time deb = met.getH_debut();
                Time fin = met.getH_fin();
                double nivLum = Puissance_solaire.getMeteoHeure(c, datecoupure, deb, fin).get(0).getNiveau();

                double percent = (nivLum*100)/10;
                double puissanceSolaire = (src.getPuissance()*percent)/100;
                //System.out.println("Puissance Solaire "+met.getH_debut().getHours()+" : "+puissanceSolaire);
                
                double totalConso = conso*nbrEtu;
                
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
                    if (newBat < src.getCapacite()/2) {
                        double surplus = totalConso-puissanceSolaire;
                        if ((newBat+surplus) > (src.getCapacite()/2)) {
                            newBat = src.getCapacite()/2;
                        } else {
                            newBat+=surplus;
                        }
                    }
                }
            }
        }
        //System.out.println("Min : "+min);
        if (hr == 0 && min == 0) {
            return new Prevision(datecoupure, null, nbrMatin, nbrPM, conso);
        }
        
        return new Prevision(datecoupure, Time.valueOf(hr+":"+(int)min+":00"), nbrMatin, nbrPM, conso);
    }
}
