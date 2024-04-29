/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package DataConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Tohy
 */
public class Dbconnect {
    public Dbconnect() {
    }
    
    public Connection ConnectPostgres() throws Exception {
        Connection c = null;
        String url = "jdbc:postgresql://localhost:5432/solaire2";
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, "postgres", "postgres");
            System.out.println(url);
        } catch (Exception e) {
            throw e;
        }
        return c;
    }
}
