package Model;

import back.dao.GenericDAO;
import back.frame.AnnotMap;
import back.frame.Attribut;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.Connect;

@AnnotMap (nomTable = "centre")
public class Centre {
    @Attribut(attr = "idcentre", primary_key = true)
    String idcentre;
    @Attribut(attr = "nomcentre")
    String nomcentre;

    public String getIdcentre(){
        return this.idcentre;
    }

    public void setIdcentre(String idcentre){
        this.idcentre = idcentre;
    }

    public String getNomcentre(){
        return this.nomcentre;
    }

    public void setNomcentre(String nomcentre){
        this.nomcentre = nomcentre;
    }
    
    public static List<Centre> getAllCentre(Connect c) throws SQLException {
        List<Object> obj = new ArrayList<>();
        List<Centre> allCenter = new ArrayList<>();
        Centre center = new Centre();
        try {
            obj = GenericDAO.selectAll(center, c);
        } catch (SQLException ex) {
            throw ex;
        }
        for (Object o : obj) {
            allCenter.add((Centre)o);
        }
        
        return allCenter;
    }
    
    public static Centre getCentreById(Connect c, String id) throws  Exception {
        Centre centre = new Centre();
        centre.setIdcentre(id);
        try {
            centre = (Centre) GenericDAO.getByIds(centre, c)[0];
        } catch (Exception ex) {
            throw ex;
        }
        
        return centre;
    }
}
