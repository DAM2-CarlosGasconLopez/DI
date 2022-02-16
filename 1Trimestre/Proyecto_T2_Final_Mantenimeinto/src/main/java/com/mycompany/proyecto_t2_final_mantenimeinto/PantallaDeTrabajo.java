/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyecto_t2_final_mantenimeinto;

import CosasEnRoot.MostrarProfesores;
import ModificarIncidencias.ModificarIncidenciaProfesor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author damA
 */
public class PantallaDeTrabajo extends javax.swing.JDialog {

    Conectar conectar = null;
    int idProfesorGuardar;
    int rol;
    TableRowSorter<TableModel> rowSorter;

   
    public PantallaDeTrabajo(java.awt.Frame parent, boolean modal, int rol, int idProfesor) throws SQLException {

        super(parent, modal);
        initComponents();
        comprobarRol(rol, idProfesor);
        idProfesorGuardar = idProfesor;
        crearpopupmenu();
        
        // Aqui comrpobaremos el rol que tinen el usuario

    }

    
    // COMPROBACION DEL ROL
    private void comprobarRol(int role, int idProfesor) throws SQLException {
        rol = role;
        switch (rol) {
            // rol de ROOT
            case 1:
                rolRoot();
                break;
            // Rol de Tecnico
            case 2:
                rolTécnico();
                break;
            // Rol de Profesor
            case 3:
                rolProfesor(idProfesor);
                break;

            default:
                break;
        }
    }

    // Rol ROOT
    private void rolRoot() throws SQLException {
        CargarTodasIncidencias();
        

    }

    // Rol TECNICO
    private void rolTécnico() throws SQLException {
        CargarTodasIncidencias();
        
    }

    // Rol PROFESOR
    private void rolProfesor(int idProfesor) throws SQLException {

        // Cargamos las incidencias del propio profesor
        CargarProfesorIncidencias(idProfesor);

        menuTecnico.setVisible(false);
        menuTodasIncidencias.setVisible(false);
        

    }
    // Hacemos un SELECT con todas las INCIDENCIAS de un PROFESOR
    private void CargarProfesorIncidencias(int idProfesor) throws SQLException {
        DefaultTableModel dtm = new DefaultTableModel();
        // Modelo de la tabla
        dtm.setColumnIdentifiers(new String[]{"Id Incidencia", "Creada por", "Descripcion", "Descripción Técnica", "Horas", "Estado", "Lanzamiento Incidencia",
            "Inicio Reparacion", "Fin Reparación", "Nivel", "Clase", "Edificio", "Observaciones"});

        //ELEMENTOS PARA ORDENAR LA TABLA INCIDENCIAS
        rowSorter = new TableRowSorter<TableModel>(dtm);
        //Ordenamos la tabla segun las columnas
        tablaIncidencias.setRowSorter(rowSorter);
        
        // Conexión
        conectar = new Conectar();
        Connection conexion = conectar.getConnection();

        if (conexion != null) {

            Statement s = conexion.createStatement();
            // Le pasamos la consulta
            ResultSet rs = s.executeQuery("select i.id_incidencia, p.nombre_completo, i.descripcion, i.desc_tecnica,\n"
                    + "i.horas, est.estado, i.fecha,i.fecha_ini_rep, i.fecha_fin_rep, urg.urgencia,\n"
                    + "ubi.ubicacion, edi.edificio, i.observaciones\n"
                    + "from man_incidencias i\n"
                    + "inner join fp_profesor p on p.id_profesor = i.id_profesor_crea\n"
                    + "inner join man_estado est on est.id_estado = i.id_estado\n"
                    + "inner join man_urgencia urg on urg.id_urgencia = i.nivel_urgencia\n"
                    + "inner join man_ubicacion ubi on ubi.id_ubicacion = i.id_ubicacion\n"
                    + "inner join man_edificio edi on edi.id_edificio = ubi.id_edificio\n"
                    + "where i.id_profesor_crea = " + idProfesor);

            // Generamos un array para recoger lo que pedimos en la consulta
            String incide[] = new String[13];
            //String incide[] = new String[11];

            while (rs.next()) {
                incide[0] = rs.getString(1);
                incide[1] = rs.getString(2);
                incide[2] = rs.getString(3);
                incide[3] = rs.getString(4);
                incide[4] = rs.getString(5);
                incide[5] = rs.getString(6);
                incide[6] = rs.getString(7);
                incide[7] = rs.getString(8);
                incide[8] = rs.getString(9);
                incide[9] = rs.getString(10);
                incide[10] = rs.getString(11);
                incide[11] = rs.getString(12);
                incide[12] = rs.getString(13);
                System.out.println(incide);

                // Añadimos una fila a la tabla
                dtm.addRow(incide);
                System.out.println(incide);
            }
            // Le ponemos modelo a la tabla
            tablaIncidencias.setModel(dtm);
            
           

        } else {
            JOptionPane.showMessageDialog(this, "conexion fallida");
        }
    }
    
    // Hacemos un SELECT con todas las INCIDENCIAS para TECNICO y ROOT
    public void CargarTodasIncidencias() throws SQLException {
        DefaultTableModel dtm = new DefaultTableModel();
        
        // Modelo de la tabla
        dtm.setColumnIdentifiers(new String[]{"Id Incidencia", "Creada por", "Descripcion", "Descripción Técnica", "Horas", "Estado", "Lanzamiento Incidencia",
            "Inicio Reparacion", "Fin Reparación", "Nivel", "Clase", "Edificio", "Observaciones"});
        
        //ELEMENTOS PARA ORDENAR LA TABLA INCIDENCIAS
        rowSorter = new TableRowSorter<TableModel>(dtm);
        //Ordenamos la tabla segun las columnas
        tablaIncidencias.setRowSorter(rowSorter);
        

        // Conexión
        conectar = new Conectar();
        Connection conexion = conectar.getConnection();

        if (conexion != null) {

            Statement s = conexion.createStatement();
            // Le pasamos la consulta
            ResultSet rs = s.executeQuery("select i.id_incidencia, p.nombre_completo, i.descripcion, i.desc_tecnica,\n"
                    + " i.horas, est.estado, i.fecha,i.fecha_ini_rep, i.fecha_fin_rep, urg.urgencia, \n"
                    + " ubi.ubicacion, edi.edificio, i.observaciones\n"
                    + "from man_incidencias i\n"
                    + "inner join fp_profesor p on p.id_profesor = i.id_profesor_crea\n"
                    + "inner join man_estado est on est.id_estado = i.id_estado\n"
                    + "inner join man_urgencia urg on urg.id_urgencia = i.nivel_urgencia\n"
                    + "inner join man_ubicacion ubi on ubi.id_ubicacion = i.id_ubicacion\n"
                    + "inner join man_edificio edi on edi.id_edificio = ubi.id_edificio ");

            // Generamos un array para recoger lo que pedimos en la consulta
         
            String incide[] = new String[13];
            //String incide[] = new String[11];

            while (rs.next()) {
                incide[0] = rs.getString(1);
                incide[1] = rs.getString(2);
                incide[2] = rs.getString(3);
                incide[3] = rs.getString(4);
                incide[4] = rs.getString(5);
                incide[5] = rs.getString(6);
                incide[6] = rs.getString(7);
                incide[7] = rs.getString(8);
                incide[8] = rs.getString(9);
                incide[9] = rs.getString(10);
                incide[10] = rs.getString(11);
                incide[11] = rs.getString(12);
                incide[12] = rs.getString(13);

                // Añadimos una fila a la tabla
                dtm.addRow(incide);
            }
            // Le ponemos modelo a la tabla
            tablaIncidencias.setModel(dtm);
        } else {
            JOptionPane.showMessageDialog(this, "conexion fallida");
        }
        
        /*dtm = new DefaultTableModel(){
            @Override
            public Class getColumnClass(int columna){
            if (columna == 1) {
                return Integer.class;
            }
                return String.class ;
        };
       
    };*/
        
  }

    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCrearIncidencia = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaIncidencias = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuIncidencia = new javax.swing.JMenu();
        menuTodasIncidencias = new javax.swing.JMenuItem();
        menuMisIncidencias = new javax.swing.JMenuItem();
        menuProfesorado = new javax.swing.JMenu();
        menuProfesores = new javax.swing.JMenuItem();
        menuTecnico = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnCrearIncidencia.setText("Crear Incidencia");
        btnCrearIncidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearIncidenciaActionPerformed(evt);
            }
        });

        tablaIncidencias.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaIncidencias);

        menuIncidencia.setText("Incidencias");

        menuTodasIncidencias.setText("Todas Incidencias");
        menuTodasIncidencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTodasIncidenciasActionPerformed(evt);
            }
        });
        menuIncidencia.add(menuTodasIncidencias);

        menuMisIncidencias.setText("Mis Incidencias");
        menuMisIncidencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMisIncidenciasActionPerformed(evt);
            }
        });
        menuIncidencia.add(menuMisIncidencias);

        jMenuBar1.add(menuIncidencia);

        menuProfesorado.setText("Profesorado");

        menuProfesores.setText("Lista Profesores");
        menuProfesores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuProfesoresActionPerformed(evt);
            }
        });
        menuProfesorado.add(menuProfesores);

        jMenuBar1.add(menuProfesorado);

        menuTecnico.setText("Técnico");
        jMenuBar1.add(menuTecnico);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCrearIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1001, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCrearIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearIncidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearIncidenciaActionPerformed

        try {
            NuevaIncidencia newIncidencia = new NuevaIncidencia(null, true, idProfesorGuardar);
            newIncidencia.setVisible(true);
            
            if(rol == 3){
                // Si es profesor cargamos sus incidencias
                CargarProfesorIncidencias(idProfesorGuardar);
            }else{
                CargarTodasIncidencias();                
            }
        } catch (SQLException ex) {
            Logger.getLogger(PantallaDeTrabajo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCrearIncidenciaActionPerformed

    private void menuProfesoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuProfesoresActionPerformed
        MostrarProfesores mostrarProfesores;
        try {
            mostrarProfesores = new MostrarProfesores(null, true, rol);
            mostrarProfesores.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(PantallaDeTrabajo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuProfesoresActionPerformed

    // Eliminar Filtro
    private void menuTodasIncidenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTodasIncidenciasActionPerformed
        try {
            CargarTodasIncidencias();
        } catch (SQLException ex) {
            Logger.getLogger(PantallaDeTrabajo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuTodasIncidenciasActionPerformed

    private void menuMisIncidenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMisIncidenciasActionPerformed
        try {
            CargarProfesorIncidencias(idProfesorGuardar);
        } catch (SQLException ex) {
            Logger.getLogger(PantallaDeTrabajo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuMisIncidenciasActionPerformed

  
    
    
    // POPUP MENU para MODIFICAR las incidencias
    private void crearpopupmenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem modificarIncidenciaItem = new JMenuItem("Modificar Incidencia");
        
        popupMenu.add(modificarIncidenciaItem);

        tablaIncidencias.setComponentPopupMenu(popupMenu);

        modificarIncidenciaItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    try {
                        PanelModifyIncidencia();
                    } catch (ParseException ex) {
                        Logger.getLogger(PantallaDeTrabajo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PantallaDeTrabajo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private void PanelModifyIncidencia() throws SQLException, ParseException {
                
               int cuentaFilasSeleccionadas = tablaIncidencias.getSelectedRowCount();

                if (cuentaFilasSeleccionadas == 0) {
                    JOptionPane.showMessageDialog(PantallaDeTrabajo.this , "No hay filas seleccionadas", "Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    int resultado = JOptionPane.showConfirmDialog(PantallaDeTrabajo.this, "¿Esta seguro que desea modificar la incidencia?", "¿Seguro?", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (resultado == JOptionPane.YES_OPTION) {
                        int fila = tablaIncidencias.getSelectedRow();
                        var idIncidencia = tablaIncidencias.getModel().getValueAt(fila, 0).toString();
                        
                        // Abrimos la pantalla de modificar incidencia pasandole el id
                        ModificarIncidenciaProfesor modificar = new ModificarIncidenciaProfesor(null, true, idIncidencia,rol);
                        modificar.setVisible(true);
                        
                        if(rol == 3){
                            // Si es profesor cargamos sus incidencias
                            CargarProfesorIncidencias(idProfesorGuardar);
                        }else{
                            CargarTodasIncidencias();                
                        }
                        

                    }
                }
            }
        });
    }
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCrearIncidencia;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenu menuIncidencia;
    private javax.swing.JMenuItem menuMisIncidencias;
    private javax.swing.JMenu menuProfesorado;
    private javax.swing.JMenuItem menuProfesores;
    private javax.swing.JMenu menuTecnico;
    private javax.swing.JMenuItem menuTodasIncidencias;
    private javax.swing.JTable tablaIncidencias;
    // End of variables declaration//GEN-END:variables

    

}
