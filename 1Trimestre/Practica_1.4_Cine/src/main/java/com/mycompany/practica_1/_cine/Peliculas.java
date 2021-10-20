/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica_1._cine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author damA
 */
public class Peliculas extends javax.swing.JDialog {
    
    List<Peliculas> listaPelis = new ArrayList<Peliculas>();
    Conexion_BD_Cine conectar = null;
    PreparedStatement ps = null;

    /**
     * Creates new form Peliculas
     */
    public Peliculas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        try {
            RefrescarPeliculas();
        } catch (SQLException ex) {
            Logger.getLogger(Peliculas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void RefrescarPeliculas() throws SQLException{
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(new String[]{"Id Pelicula","Titulo","Director","Fecha Proyeccion","Año Estreno","Tematica","Precio Entrada","Sala"});
        
        
        
        //for (Clientes cliente: listaclientes){
        //    dtm.addRow(cliente.toArrayString());
        //}
        
       
        
        conectar = new Conexion_BD_Cine();
        Connection conexion = conectar.getConnection();
        if (conexion != null) {
            
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery("select p.idPelicula, p.titulo,d.nombre,p.fechaProyeccion,p.añoEstreno,t.nombre,p.precioEntrada,s.nombre\n" +
                                          "from pelicula p\n" +
                                          "inner join director d on p.director = d.idDirector\n" +
                                          "inner join tematica t on p.tematica = t.idTematica\n" +
                                          "inner join sala s on p.sala = s.idSala");
                       
            String peli[] = new String[8];
                       
           
            while (rs.next()) {
                peli[0] = rs.getString(1);
                peli[1] = rs.getString(2);
                peli[2] = rs.getString(3);
                peli[3] = rs.getString(4);
                peli[4] = rs.getString(5);
                peli[5] = rs.getString(6);
                peli[6] = rs.getString(7);
                peli[7] = rs.getString(8);
              
                
                dtm.addRow(peli);
            }
            jTablePelis.setModel(dtm);
                                    
        }
        else{
            JOptionPane.showMessageDialog(this, "conexion fallida");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePelis = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("PELICULAS");

        jTablePelis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTablePelis);

        jButton1.setText("Añadir Película");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 843, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablePelis;
    // End of variables declaration//GEN-END:variables
}