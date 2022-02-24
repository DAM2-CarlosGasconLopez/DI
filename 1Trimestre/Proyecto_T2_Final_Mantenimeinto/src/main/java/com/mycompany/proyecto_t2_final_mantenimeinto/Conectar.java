/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyecto_t2_final_mantenimeinto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 *
 * @author damA
 */
public class Conectar {
    public static final String URL = "jdbc:mysql://servidorifc.iesch.org:8882/mantenimiento_gascon_c";
    public static final String USER = "2_gascon_c";
    public static final String PASS = "gy7f8";
 
       
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