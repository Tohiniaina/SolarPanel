package Model;

import back.dao.GenericDAO;
import back.frame.AnnotMap;
import back.frame.Attribut;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;

@AnnotMap (nomTable = "salle")
public class Salle {
    @Attribut(attr = "idsalle", primary_key = true)
    String idsalle;
    @Attribut(attr = "nom")
    String nom;
    @Attribut(attr = "idsource_energie")
    String idsource_energie;

    public Salle() {
    }

    public Salle(String idsalle, String nom, String idsource_energie) {
        this.idsalle = idsalle;
        this.nom = nom;
        this.idsource_energie = idsource_energie;
    }

    public String getIdsalle(){
        return this.idsalle;
    }

    public void setIdsalle(String idsalle){
        this.idsalle = idsalle;
    }

    public String getNom(){
        return this.nom;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public String getIdsource_energie(){
        return this.idsource_energie;
    }

    public void setIdsource_energie(String idsource_energie){
        this.idsource_energie = idsource_energie;
    }
    
    public static List<Salle> getSalleBySource(Connection connection, String source) throws Exception {
        List<Salle> rep = new ArrayList<>();

        String query = "SELECT * FROM salle WHERE idsource_energie = '"+source+"'";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String idsalle = resultSet.getString("idsalle");
                String nom = resultSet.getString("nom");
                String idsource_energie = resultSet.getString("idsource_energie");

                Salle salle = new Salle(idsalle, nom, idsource_energie);
                rep.add(salle);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            throw e;
        } 
        return rep;
    }
    
    public List<Presence_salle> getPresence(Connection connection, Date date) throws Exception {
        List<Presence_salle> res =  new ArrayList<>();
        String sql2 = "select * from presence_salle where idsalle = '"+idsalle+"' and date = '"+date+"'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql2);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String idpresence = resultSet.getString("idpresence");
                Date datee = resultSet.getDate("date");
                String idsalle = resultSet.getString("idsalle");
                int nbr_personne = resultSet.getInt("nbr_personne");
                int partie = resultSet.getInt("partie");    

                Presence_salle prs = new Presence_salle(idpresence, datee, idsalle, nbr_personne, partie);
                res.add(prs);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            throw e;
        } 
        return res;
    }
    
    public Presence_salle getPresencePartie(Connection connection, Date date, int partie) throws Exception {
        Presence_salle psl = new Presence_salle();
        String sql2 = "select * from presence_salle where idsalle = '"+idsalle+"' and date = '"+date+"' and partie = "+partie;
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql2);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String idpresence = resultSet.getString("idpresence");
                Date datee = resultSet.getDate("date");
                String idsalle = resultSet.getString("idsalle");
                int nbr_personne = resultSet.getInt("nbr_personne");
                int part = resultSet.getInt("partie");    

                psl = new Presence_salle(idpresence, datee, idsalle, nbr_personne, part);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            throw e;
        } 
        return psl;
    }
    
    public double getMoyennePresence(Connection connection, Date date, int partie, int dayWeek) throws Exception {
        Presence_salle psl = new Presence_salle();
        String sql2 = "select * from presence_salle where idsalle = '"+idsalle+"' and date <= '"+date+"' and partie = "+partie+" AND extract(dow from date) = "+dayWeek;
        double ans = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql2);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            int i = 0;
            int nbr = 0;

            while (resultSet.next()) {
                nbr += resultSet.getInt("nbr_personne");
                i++;
            }
            
            ans = nbr/i;
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            throw e;
        } 
        return ans;
    }
}
