package Model;

import back.dao.GenericDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.Connect;

public class Batterie {
    String idbatterie;
    double capacite;

    public String getIdbatterie(){
        return this.idbatterie;
    }

    public void setIdbatterie(String idbatterie){
        this.idbatterie = idbatterie;
    }

    public double getCapacite(){
        return this.capacite;
    }

    public void setCapacite(double capacite){
        this.capacite = capacite;
    }
}
