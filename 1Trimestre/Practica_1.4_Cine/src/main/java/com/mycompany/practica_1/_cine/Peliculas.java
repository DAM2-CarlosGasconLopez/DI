/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica_1._cine;

import Añadir.AddPeliculas;
import Añadir.AddPeliculasAndElementos;
import Añadir.AddSala;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;


public class Peliculas extends javax.swing.JDialog {
    
    List<Peliculas> listaPelis = new ArrayList<Peliculas>();
    Conexion_BD_Cine conectar = null;
    PreparedStatement ps = null;
    static int instancia = 0;
    static int maxNum = 0;
    

    public Peliculas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        try {
            RefrescarPeliculas();
            crearpopupmenu();
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
                                          "inner join sala s on p.sala = s.idSala\n" +
                                          "order by p.idPelicula\n" +
                                          "limit " + instancia + ",3");
                       
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

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePelis = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        btnSala = new javax.swing.JButton();
        btntematica = new javax.swing.JButton();
        btnDirector = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

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

        btnSala.setText("Por Sala");
        btnSala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalaActionPerformed(evt);
            }
        });

        btntematica.setText("Por Tematica");
        btntematica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntematicaActionPerformed(evt);
            }
        });

        btnDirector.setText("Por Director");
        btnDirector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDirectorActionPerformed(evt);
            }
        });

        btnRefresh.setText("Refrescar");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnSiguiente.setText(">");
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });

        btnAnterior.setText("<");
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });

        jMenu1.setText("Peliculas");

        jMenuItem1.setText("Añadir Pelicula ");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Añadir Pelicula y Elementos");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(83, 83, 83))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(316, 316, 316)
                        .addComponent(btnAnterior)
                        .addGap(96, 96, 96)
                        .addComponent(btnSiguiente)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDirector)
                        .addGap(18, 18, 18)
                        .addComponent(btntematica)
                        .addGap(18, 18, 18)
                        .addComponent(btnSala)
                        .addGap(18, 18, 18)
                        .addComponent(btnRefresh)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSala)
                    .addComponent(btntematica)
                    .addComponent(btnDirector)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(67, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // BOTONES DEL MENU BAR
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        AddPeliculas nuevaPelicula = null;
        try {
            nuevaPelicula = new AddPeliculas(null, true);
        } catch (SQLException ex) {
            Logger.getLogger(Peliculas.class.getName()).log(Level.SEVERE, null, ex);
        }
        nuevaPelicula.setVisible(true);
        
        try {
            RefrescarPeliculas();
        } catch (SQLException ex) {
            
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        AddPeliculasAndElementos nuevaPelicula = null;
        try {
            nuevaPelicula = new AddPeliculasAndElementos(null, true);
        } catch (SQLException ex) {
            Logger.getLogger(Peliculas.class.getName()).log(Level.SEVERE, null, ex);
        }
        nuevaPelicula.setVisible(true);
        
        try {
            RefrescarPeliculas();
        } catch (SQLException ex) {
            
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    
    
    
    
    // BOTONES DE BUSQUEDA DE PELICULAS Y  DE REFRESCAR
    private void btnDirectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDirectorActionPerformed
                
        String buscar = jTextField1.getText().toString();
        if (buscar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Campo vacio");
        }else{
        

           DefaultTableModel dtm = new DefaultTableModel();

           dtm.setColumnIdentifiers(new String[]{"Id Pelicula","Titulo","Director","Fecha Proyeccion","Año Estreno","Tematica","Precio Entrada","Sala"});
           jTablePelis.setModel(dtm);
            try {
                BuscarPorDirector(buscar, dtm);
            } catch (SQLException ex) {
                Logger.getLogger(Peliculas.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }//GEN-LAST:event_btnDirectorActionPerformed

    private void btntematicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntematicaActionPerformed
        String buscar = jTextField1.getText().toString();
        if (buscar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Campo vacio");
        }else{
        

           DefaultTableModel dtm = new DefaultTableModel();

           dtm.setColumnIdentifiers(new String[]{"Id Pelicula","Titulo","Director","Fecha Proyeccion","Año Estreno","Tematica","Precio Entrada","Sala"});
           jTablePelis.setModel(dtm);
            try {
                buscarPorTematica(buscar, dtm);
            } catch (SQLException ex) {
                Logger.getLogger(Peliculas.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }//GEN-LAST:event_btntematicaActionPerformed

    private void btnSalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalaActionPerformed
        String buscar = jTextField1.getText().toString();
        if (buscar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Campo vacio");
        }else{
        

           DefaultTableModel dtm = new DefaultTableModel();

           dtm.setColumnIdentifiers(new String[]{"Id Pelicula","Titulo","Director","Fecha Proyeccion","Año Estreno","Tematica","Precio Entrada","Sala"});
           jTablePelis.setModel(dtm);
            try {
                buscarPorSala(buscar, dtm);
            } catch (SQLException ex) {
                Logger.getLogger(Peliculas.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }//GEN-LAST:event_btnSalaActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        try {
            RefrescarPeliculas();
        } catch (SQLException ex) {
            Logger.getLogger(Peliculas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRefreshActionPerformed

    
    // BOTONES DE PAGINADO
    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        
        SacarNumdirectores();
        
        
        if (instancia!=maxNum) {
            instancia = instancia + 3;    
            
            try {
                RefrescarPeliculas();
            } catch (SQLException ex) {
                Logger.getLogger(Peliculas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
             
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        
       
        if (instancia>=3) {
            instancia = instancia - 3;    
            
            try {
                RefrescarPeliculas();
            } catch (SQLException ex) {
                Logger.getLogger(Peliculas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }//GEN-LAST:event_btnAnteriorActionPerformed

    
    // POPUP MENU
    private void crearpopupmenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem directorItem = new JMenuItem("Buscar por el director de la pelicula seleccionada");
        JMenuItem tematicaItem = new JMenuItem("Buscar por la tematica de la pelicula seleccionada");

       
               
        popupMenu.add(directorItem);
        popupMenu.add(tematicaItem);
       
        jTablePelis.setComponentPopupMenu(popupMenu);
       
        directorItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    buscarDirectorPOP();
                } catch (SQLException ex) {
                    Logger.getLogger(Peliculas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        tematicaItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    buscarTematicaPOP();
                } catch (SQLException ex) {
                    Logger.getLogger(Peliculas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });  
    }
    
    public void buscarDirectorPOP() throws SQLException{
        int cuentaFilasSeleccionadas = jTablePelis.getSelectedRowCount();

        if (cuentaFilasSeleccionadas == 0) {
            JOptionPane.showMessageDialog(this, "No hay filas seleccionadas","Error",JOptionPane.WARNING_MESSAGE);  
        } else {
            int resultado=JOptionPane.showConfirmDialog(this, "¿Esta seguro que desea buscar por director?","¿Seguro?", JOptionPane.YES_NO_CANCEL_OPTION);
            if (resultado==JOptionPane.YES_OPTION) {
                int fila = jTablePelis.getSelectedRow();
                var director =jTablePelis.getModel().getValueAt(fila, 2).toString();
               
                DefaultTableModel dtm = new DefaultTableModel();
                dtm.setColumnIdentifiers(new String[]{"idPelicula","Titulo","Director","Fecha proyeccion","Año de estreno", "Tematica","Precio de entrada","Sala"});      
                BuscarPorDirector(director, dtm);
               
               
            }else if (resultado==JOptionPane.NO_OPTION) {
                JOptionPane.showConfirmDialog(this, "Fila no borrada","",JOptionPane.ERROR_MESSAGE);
            }
            else if (resultado==JOptionPane.CANCEL_OPTION) {
                JOptionPane.showConfirmDialog(this, "Operacion cancelada","",JOptionPane.WARNING_MESSAGE);
            }
        }
    }
   
    public void buscarTematicaPOP() throws SQLException{
        int cuentaFilasSeleccionadas = jTablePelis.getSelectedRowCount();

        if (cuentaFilasSeleccionadas == 0) {
            JOptionPane.showMessageDialog(this, "No hay filas seleccionadas","Error",JOptionPane.WARNING_MESSAGE);  
        } else {
            int resultado=JOptionPane.showConfirmDialog(this, "¿Esta seguro que desea buscar por temática?","¿Seguro?", JOptionPane.YES_NO_CANCEL_OPTION);
            if (resultado==JOptionPane.YES_OPTION) {
                int fila = jTablePelis.getSelectedRow();
                var tematica =jTablePelis.getModel().getValueAt(fila, 5).toString();
               
                DefaultTableModel dtm = new DefaultTableModel();
                dtm.setColumnIdentifiers(new String[]{"idPelicula","Titulo","Director","Fecha proyeccion","Año de estreno", "Tematica","Precio de entrada","Sala"});
       
                buscarPorTematica(tematica, dtm);
               
               
            }else if (resultado==JOptionPane.NO_OPTION) {
                JOptionPane.showConfirmDialog(this, "Fila no borrada","",JOptionPane.ERROR_MESSAGE);
            }
            else if (resultado==JOptionPane.CANCEL_OPTION) {
                JOptionPane.showConfirmDialog(this, "Operacion cancelada","",JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    
    // BUSQUEDA DE PELICULAS SEGUN LO QUE LE PIDAMOS
    private void BuscarPorDirector(String buscar,DefaultTableModel dtm) throws SQLException {
        

        conectar = new Conexion_BD_Cine();
        Connection conexion = conectar.getConnection();
        if (conexion != null) {
            
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery("select p.idPelicula, p.titulo,d.nombre,p.fechaProyeccion,p.añoEstreno,t.nombre,p.precioEntrada,s.nombre\n" +
                                          "from pelicula p\n" +
                                          "inner join director d on p.director = d.idDirector\n" +
                                          "inner join tematica t on p.tematica = t.idTematica\n" +
                                          "inner join sala s on p.sala = s.idSala\n " +
                                          "where d.nombre = '" + buscar + "'");
                       
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

    private void buscarPorSala(String buscar, DefaultTableModel dtm) throws SQLException {
        conectar = new Conexion_BD_Cine();
        Connection conexion = conectar.getConnection();
        if (conexion != null) {
            
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery("select p.idPelicula, p.titulo,d.nombre,p.fechaProyeccion,p.añoEstreno,t.nombre,p.precioEntrada,s.nombre\n" +
                                          "from pelicula p\n" +
                                          "inner join director d on p.director = d.idDirector\n" +
                                          "inner join tematica t on p.tematica = t.idTematica\n" +
                                          "inner join sala s on p.sala = s.idSala\n " +
                                          "where s.nombre = '" + buscar +"'");
                       
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

    private void buscarPorTematica(String buscar, DefaultTableModel dtm) throws SQLException {
        conectar = new Conexion_BD_Cine();
        Connection conexion = conectar.getConnection();
        if (conexion != null) {
            
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery("select p.idPelicula, p.titulo,d.nombre,p.fechaProyeccion,p.añoEstreno,t.nombre,p.precioEntrada,s.nombre\n" +
                                          "from pelicula p\n" +
                                          "inner join director d on p.director = d.idDirector\n" +
                                          "inner join tematica t on p.tematica = t.idTematica\n" +
                                          "inner join sala s on p.sala = s.idSala\n " +
                                          "where t.nombre = '" + buscar +"'");
                       
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
    
    
    // SACAR CUANTAS PELICULAS TENEMOS EN LA BASE DE DATOS

    private void SacarNumdirectores() {
        conectar = new Conexion_BD_Cine();
        Connection conexion = conectar.getConnection();
        if (conexion != null) {
            
            Statement s;
            try {
                s = conexion.createStatement();
                ResultSet rs = s.executeQuery("select count(idPelicula)"
                                            + "from pelicula");
                
                while (rs.next()) {
                    
                    maxNum =  rs.getInt(1);
                    
                    
                }
                
                
            } catch (SQLException ex) {
                Logger.getLogger(Peliculas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnDirector;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSala;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JButton btntematica;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablePelis;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    
}