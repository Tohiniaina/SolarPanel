/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Utilitaire.Util;
import back.dao.GenericDAO;
import back.frame.AnnotMap;
import back.frame.Attribut;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

/**
 *
 * @author Tohy
 */
@AnnotMap (nomTable = "v_details_source_energie")
public class DetailsSource_energie {
    @Attribut(attr = "idsource_energie", primary_key = true)
    String idsource_energie;
    @Attribut(attr = "nom")
    String nom;
    @Attribut(attr = "idcentre")
    String idcentre;
    @Attribut(attr = "nomcentre")
    String nomcentre;
    @Attribut(attr = "idsource_solaire")
    String idsource_solaire;
    @Attribut(attr = "idbatterie")
    String idbatterie;
    @Attribut(attr = "puissance")
    double puissance;
    @Attribut(attr = "capacite")
    double capacite;

    public DetailsSource_energie(String idsource_energie, String nom, String idcentre, String nomcentre, String idsource_solaire, String idbatterie, double puissance, double capacite) {
        this.idsource_energie = idsource_energie;
        this.nom = nom;
        this.idcentre = idcentre;
        this.nomcentre = nomcentre;
        this.idsource_solaire = idsource_solaire;
        this.idbatterie = idbatterie;
        this.puissance = puissance;
        this.capacite = capacite;
    }

    public DetailsSource_energie() {
    }

    public String getIdsource_energie() {
        return idsource_energie;
    }

    public void setIdsource_energie(String idsource_energie) {
        this.idsource_energie = idsource_energie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIdcentre() {
        return idcentre;
    }

    public void setIdcentre(String idcentre) {
        this.idcentre = idcentre;
    }

    public String getNomcentre() {
        return nomcentre;
    }

    public void setNomcentre(String nomcentre) {
        this.nomcentre = nomcentre;
    }

    public String getIdsource_solaire() {
        return idsource_solaire;
    }

    public void setIdsource_solaire(String idsource_solaire) {
        this.idsource_solaire = idsource_solaire;
    }

    public String getIdbatterie() {
        return idbatterie;
    }

    public void setIdbatterie(String idbatterie) {
        this.idbatterie = idbatterie;
    }

    public double getPuissance() {
        return puissance;
    }

    public void setPuissance(double puissance) {
        this.puissance = puissance;
    }

    public double getCapacite() {
        return capacite;
    }

    public void setCapacite(double capacite) {
        this.capacite = capacite;
    }
    
    
    public static DetailsSource_energie getSourceById(Connection connection, String id) throws Exception {
        DetailsSource_energie res =  new DetailsSource_energie();
        
        String query = "select * from v_details_source_energie where idsource_energie = '"+id+"'";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String idsource_energie = resultSet.getString("idsource_energie");
                String nom = resultSet.getString("nom");
                String idcentre = resultSet.getString("idcentre");
                String nomcentre = resultSet.getString("nomcentre");
                String idsource_solaire = resultSet.getString("idsource_solaire");
                String idbatterie = resultSet.getString("idbatterie");
                double puissance = resultSet.getDouble("puissance");
                double capacite = resultSet.getDouble("capacite");

                res = new DetailsSource_energie(idsource_energie, nom, idcentre, nomcentre, idsource_solaire, idbatterie, puissance, capacite);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            throw e;
        }
        return res;
    }
    
    /*public double getMoyennePuissanceUtiliser(Connection connection, Date date) throws Exception {
        double puissance_moyenne = 0;
        
        String query = "select avg(puissance_etudiant) puissance_moyenne from coupure where idsource_energie = '"+this.idsource_energie+"' AND date < '"+date+"'";
        System.out.println(query);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                puissance_moyenne = resultSet.getDouble("puissance_moyenne");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            throw e;
        }
        return puissance_moyenne;
    }*/
    
    public double getMoyennePuissanceUtiliser(Connection connection, Date date) throws Exception {
        double puissance_moyenne = 0;
        
        double conso = 0;
        int divisor = 0;
        
        List<Coupure> allCoup = Coupure.getAllCoupureDate(connection, date);
        
        for (Coupure coupure : allCoup) {
            DetailsSource_energie src = getSourceById(connection, coupure.getIdsource_energie());
            
            Util.checkMeteo(connection, coupure.getDate());
            double sommeEtuMaraina = Presence_salle.getPresencePartie(connection, coupure.getDate(), coupure.getIdsource_energie(), 0);
            double sommeEtuAriva = Presence_salle.getPresencePartie(connection, coupure.getDate(), coupure.getIdsource_energie(), 1);
            
            
            double tc = Util.calculeConsommation(connection, sommeEtuMaraina, sommeEtuAriva, src, coupure.getDate(), coupure.getHeure());
            System.out.println(coupure.getDate()+" : "+tc);
            conso+=tc;
            divisor++;
        }
        
        puissance_moyenne=conso/divisor;
        return puissance_moyenne;
    }
}
