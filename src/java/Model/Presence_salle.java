package Model;

import back.frame.AnnotMap;
import back.frame.Attribut;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import Utilitaire.Util;

@AnnotMap (nomTable = "presence_salle")
public class Presence_salle {
    @Attribut(attr = "idpresence", primary_key = true)
    String idpresence;
    @Attribut(attr = "date")
    Date date;
    @Attribut(attr = "idsalle", foreign_key = true)
    String idsalle;
    @Attribut(attr = "nbr_personne")
    int nbr_personne;
    @Attribut(attr = "partie")
    Integer partie;

    public Presence_salle() {
    }

    public Presence_salle(String idpresence, Date date, String idsalle, int nbr_personne, Integer partie) {
        this.idpresence = idpresence;
        this.date = date;
        this.idsalle = idsalle;
        this.nbr_personne = nbr_personne;
        this.partie = partie;
    }

    public String getIdpresence(){
        return this.idpresence;
    }

    public void setIdpresence(String idpresence){
        this.idpresence = idpresence;
    }

    public Date getDate(){
        return this.date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public String getIdsalle(){
        return this.idsalle;
    }

    public void setIdsalle(String idsalle){
        this.idsalle = idsalle;
    }

    public int getNbr_personne(){
        return this.nbr_personne;
    }

    public void setNbr_personne(Integer nbr_personne){
        this.nbr_personne = nbr_personne;
    }

    public Integer getPartie(){
        return this.partie;
    }

    public void setPartie(Integer partie){
        this.partie = partie;
    }
    
    public static List<Presence_salle> getPresenceSalleDayOfWeek(Connection connection, Date date, int dayofweek, List<Salle> salle) throws Exception {
        List<Presence_salle> ans = new ArrayList<>();
        String cond = Util.generateCondOrSalle(salle);
        String query = "select * from presence_salle where date < '"+date+"' AND extract(dow from date) = "+dayofweek+" AND ("+cond+")";
        System.out.println(query);        

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String idpresence = resultSet.getString("idpresence");
                Date datee = resultSet.getDate("date");
                String idsalle = resultSet.getString("idsalle");
                int nbr_personne = resultSet.getInt("nbr_personne");
                int partie = resultSet.getInt("partie");    

                Presence_salle prs = new Presence_salle(idpresence, datee, idsalle, nbr_personne, partie);
                ans.add(prs);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            throw e;
        } 
        return ans;
    }
    
    public static double getPresencePartie(Connection c, Date date, String idsource, int partie) throws Exception {
        List<Salle> salleSrc = Salle.getSalleBySource(c, idsource);

        List<Presence_salle> presence = new ArrayList<>();

        for (Salle sl : salleSrc) {
            presence.add((Presence_salle) sl.getPresencePartie(c, date, partie));
        }

        double somme = 0;
        for (int i = 0; i < presence.size(); i++) {
            somme+=presence.get(i).getNbr_personne();
        }
        
        return somme;
    }
}
