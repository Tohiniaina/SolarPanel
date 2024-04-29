package Model;

import back.dao.GenericDAO;
import back.frame.AnnotMap;
import back.frame.Attribut;
import java.sql.Time;
import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@AnnotMap (nomTable = "puissance_solaire")
public class Puissance_solaire {
    @Attribut(attr = "id", primary_key = true)
    Integer id;
    @Attribut(attr = "date")
    Date date;
    @Attribut(attr = "h_debut")
    Time h_debut;
    @Attribut(attr = "h_fin")
    Time h_fin;
    @Attribut(attr = "niveau")
    double niveau;

    public Puissance_solaire() {
    }

    public Puissance_solaire(Integer id, Date date, Time h_debut, Time h_fin, double niveau) {
        this.id = id;
        this.date = date;
        this.h_debut = h_debut;
        this.h_fin = h_fin;
        this.niveau = niveau;
    }

    public Integer getId(){
        return this.id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Date getDate(){
        return this.date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public Time getH_debut(){
        return this.h_debut;
    }

    public void setH_debut(Time h_debut){
        this.h_debut = h_debut;
    }

    public Time getH_fin(){
        return this.h_fin;
    }

    public void setH_fin(Time h_fin){
        this.h_fin = h_fin;
    }

    public double getNiveau(){
        return this.niveau;
    }

    public void setNiveau(Double niveau){
        this.niveau = niveau;
    }
    
    public static List<Puissance_solaire> getMeteoHeure(Connection connection, Date date, Time timeDeb, Time timeFin) throws Exception {
        List<Puissance_solaire> ans = new ArrayList<>();
        String query = "select * from puissance_solaire where date = '"+date+"' AND h_debut = '"+timeDeb+"' AND h_fin = '"+timeFin+"'";
        //System.out.println(query);        

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
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
        return ans;
    }
    
    public static List<Puissance_solaire> getMeteo(Connection connection, Date date) throws Exception {
        List<Puissance_solaire> ans = new ArrayList<>();
        String query = "select * from puissance_solaire where date = '"+date+"'";
        //System.out.println(query);        

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
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
        return ans;
    }
}
