/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica_1._cine;

import Añadir.AddDirectores;
import Añadir.AddTemática;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author damA
 */
public class Directores extends javax.swing.JDialog {

    List<Directores> lstDirectores = new ArrayList<Directores>();
    Conexion_BD_Cine conectar = null;
    PreparedStatement ps = null;

    public Directores(java.awt.Frame parent, boolean modal) throws SQLException {
        super(parent, modal);
        initComponents();
        RefrescarDirectores();
    }

    public void RefrescarDirectores() throws SQLException {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(new String[]{"Id Director", "Nombre", "Apellidos", "Fecha Nacimiento"});

        //for (Clientes cliente: listaclientes){
        //    dtm.addRow(cliente.toArrayString());
        //}
        conectar = new Conexion_BD_Cine();
        Connection conexion = conectar.getConnection();
        if (conexion != null) {

            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery("select * from cine_di.director");

            String peli[] = new String[8];

            while (rs.next()) {
                peli[0] = rs.getString(1);
                peli[1] = rs.getString(2);
                peli[2] = rs.getString(3);
                peli[3] = rs.getString(4);

                dtm.addRow(peli);
            }
            jTablePelis.setModel(dtm);

        } else {
            JOptionPane.showMessageDialog(this, "conexion fallida");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePelis = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DIRECTORES");

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

        jMenu1.setText("Directores");

        jMenuItem1.setText("Añadir Director");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        AddDirectores tema = new AddDirectores(null, true);
        tema.setVisible(true);
        
        try {
            RefrescarDirectores();
        } catch (SQLException ex) {
            
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablePelis;
    // End of variables declaration//GEN-END:variables

}
