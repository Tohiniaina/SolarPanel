package Model;

import back.frame.AnnotMap;
import back.frame.Attribut;
import java.sql.Time;
import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@AnnotMap (nomTable = "coupure")
public class Coupure {
    @Attribut(attr = "idcoupure", primary_key = true)
    String idcoupure;
    @Attribut(attr = "idsource_energie")
    String idsource_energie;
    @Attribut(attr = "date")
    Date date;
    @Attribut(attr = "heure")
    Time heure;
    @Attribut(attr = "puissance_etudiant")
    double puissance_etudiant;
    Time heureResultat;

    public Coupure(String idcoupure, String idsource_energie, Date date, Time heure, double puissance_etudiant) {
        this.idcoupure = idcoupure;
        this.idsource_energie = idsource_energie;
        this.date = date;
        this.heure = heure;
        this.puissance_etudiant = puissance_etudiant;
    }

    public Coupure(String idsource_energie, Date date, Time heure, double puissance_etudiant, Time heureResultat) {
        this.idsource_energie = idsource_energie;
        this.date = date;
        this.heure = heure;
        this.puissance_etudiant = puissance_etudiant;
        this.heureResultat = heureResultat;
    }

    public Time getHeureResultat() {
        return heureResultat;
    }

    public void setHeureResultat(Time heureResultat) {
        this.heureResultat = heureResultat;
    }

    public Coupure() {
    }

    public Coupure(String idsource_energie, Date date, Time heure, double puissance_etudiant) {
        this.idsource_energie = idsource_energie;
        this.date = date;
        this.heure = heure;
        this.puissance_etudiant = puissance_etudiant;
    }

    public double getPuissance_etudiant() {
        return puissance_etudiant;
    }

    public void setPuissance_etudiant(double puissance_etudiant) {
        this.puissance_etudiant = puissance_etudiant;
    }

    public String getIdcoupure(){
        return this.idcoupure;
    }

    public void setIdcoupure(String idcoupure){
        this.idcoupure = idcoupure;
    }

    public String getIdsource_energie(){
        return this.idsource_energie;
    }

    public void setIdsource_energie(String idsource_energie){
        this.idsource_energie = idsource_energie;
    }

    public Date getDate(){
        return this.date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public Time getHeure(){
        return this.heure;
    }

    public void setHeure(Time heure){
        this.heure = heure;
    }
    
    public void insert(Connection connection) throws Exception {
        
        String query = "INSERT INTO coupure(idsource_energie,date,heure,puissance_etudiant) VALUES('"+this.idsource_energie+"','"+this.date+"','"+this.heure+"',"+this.puissance_etudiant+")";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int row = preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (Exception e) {
            throw e;
        } 
    }
    
    public static List<Coupure> getAllCoupureDate(Connection connection, Date date) throws Exception {
        List<Coupure> res =  new ArrayList<>();
        String sql2 = "select * from coupure where date < '"+date+"'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql2);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String idcoupure = resultSet.getString("idcoupure");
                String idsource_energie = resultSet.getString("idsource_energie");
                Date datee = resultSet.getDate("date");
                Time heure = resultSet.getTime("heure");
                double puissance_etudiant = resultSet.getDouble("puissance_etudiant");

                Coupure cop = new Coupure(idcoupure, idsource_energie, datee, heure, puissance_etudiant);
                res.add(cop);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            throw e;
        } 
        return res;
    }
}
