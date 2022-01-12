package Informes;


import java.sql.Connection;
import java.sql.DriverManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author damA
 */
public class Conectar {
    public static final String URL = "jdbc:mysql://servidorifc.iesch.org:3306/jardineria_di";
    public static final String USER = "pasAlumno";
    public static final String PASS = "Admin1234";
 
       
    public Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection)DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return con;
    }

    
}
