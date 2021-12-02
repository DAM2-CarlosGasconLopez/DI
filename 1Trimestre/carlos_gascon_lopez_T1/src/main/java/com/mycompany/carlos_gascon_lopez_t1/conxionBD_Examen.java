/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carlos_gascon_lopez_t1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class conxionBD_Examen {
    public static final String URL = "jdbc:mysql://servidorifc.iesch.org:3306/examen_di";
    public static String USER = "";
    public static String PASS = "";
 
       
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

    public PreparedStatement prepareStatement(String sql) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}