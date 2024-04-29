package Model;

import back.dao.GenericDAO;
import back.frame.AnnotMap;
import back.frame.Attribut;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import utils.Connect;

@AnnotMap (nomTable = "source_energie")
public class Source_energie {
    @Attribut(attr = "idsource_energie", primary_key = true)
    String idsource_energie;
    @Attribut(attr = "nom")
    String nom;
    @Attribut(attr = "idsource_solaire")
    String idsource_solaire;
    @Attribut(attr = "idbatterie")
    String idbatterie;

    public String getIdsource_energie(){
        return this.idsource_energie;
    }

    public void setIdsource_energie(String idsource_energie){
        this.idsource_energie = idsource_energie;
    }

    public String getNom(){
        return this.nom;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public String getIdsource_solaire(){
        return this.idsource_solaire;
    }

    public void setIdsource_solaire(String idsource_solaire){
        this.idsource_solaire = idsource_solaire;
    }

    public String getIdbatterie(){
        return this.idbatterie;
    }

    public void setIdbatterie(String idbatterie){
        this.idbatterie = idbatterie;
    }
    
    public static Source_energie getSourceById(Connect c, String id) throws Exception {
        Source_energie res =  new Source_energie();
        String sql2 = "select * from source_energie where idsource_energie = '"+id+"'";
        List<Object> obPre = GenericDAO.executeQuery(sql2, c, res);
        if (obPre.isEmpty()) {
            throw new Exception("Source invalide");
        }
        for (Object object : obPre) {
            res = (Source_energie) object;
        }
        return res;
    }
}
