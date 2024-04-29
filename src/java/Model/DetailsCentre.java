/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import back.dao.GenericDAO;
import back.frame.AnnotMap;
import back.frame.Attribut;
import java.util.List;
import utils.Connect;

/**
 *
 * @author Tohy
 */
@AnnotMap (nomTable = "v_source_energie_salle")
public class DetailsCentre {
    @Attribut(attr = "idsalle")
    String idsalle;
    @Attribut(attr = "nom")
    String nom;
    @Attribut(attr = "idsource_energie")
    String idsource_energie;
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

    public String getIdsalle() {
        return idsalle;
    }

    public void setIdsalle(String idsalle) {
        this.idsalle = idsalle;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIdsource_energie() {
        return idsource_energie;
    }

    public void setIdsource_energie(String idsource_energie) {
        this.idsource_energie = idsource_energie;
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
    
    public static DetailsCentre getDetailsByIdSalle(Connect c, String idsalle) throws Exception {
        DetailsCentre dc = new DetailsCentre();
        try {
            Puissance_solaire ps = new Puissance_solaire();
            String sql = "select * from v_source_energie_salle where idsalle = '"+idsalle+"'";
            List<Object> obs = GenericDAO.executeQuery(sql, c, ps);
            for (Object ob : obs) {
                dc = (DetailsCentre) ob;
            }
        } catch (Exception e) {
            throw e;
        }
        return dc;
    }
}
