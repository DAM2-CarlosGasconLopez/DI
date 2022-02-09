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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author damA
 */
public class PantallaDeTrabajo extends javax.swing.JDialog {

    Conectar conectar = null;
    int idProfesorGuardar;
    int rol;

    /**
     * Creates new form PantallaDeTrabajo
     */
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
    private void rolTécnico() {
        menuAdministrador.setVisible(false);
    }

    // Rol PROFESOR
    private void rolProfesor(int idProfesor) throws SQLException {

        // Cargamos las incidencias del propio profesor
        CargarProfesorIncidencias(idProfesor);

        menuTecnico.setVisible(false);
        menuAdministrador.setVisible(false);

    }
    // Hacemos un SELECT con todas las INCIDENCIAS de un PROFESOR
    private void CargarProfesorIncidencias(int idProfesor) throws SQLException {
        DefaultTableModel dtm = new DefaultTableModel();
        // Modelo de la tabla
        dtm.setColumnIdentifiers(new String[]{"Id Incidencia", "Creada por", "Descripcion", "Descripción Técnica", "Horas", "Estado", "Lanzamiento Incidencia",
            "Inicio Reparacion", "Fin Reparación", "Nivel", "Clase", "Edificio", "Observaciones"});

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
    }

    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCrearIncidencia = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaIncidencias = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuIncidencias = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        menuProfesorado = new javax.swing.JMenu();
        menuTecnico = new javax.swing.JMenu();
        menuAdministrador = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

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

        menuIncidencias.setText("Incidencias");

        jMenuItem1.setText("Abiertas");
        menuIncidencias.add(jMenuItem1);

        jMenuItem2.setText("Cerradas");
        menuIncidencias.add(jMenuItem2);

        jMenuBar1.add(menuIncidencias);

        menuProfesorado.setText("Profesorado");
        jMenuBar1.add(menuProfesorado);

        menuTecnico.setText("Técnico");
        jMenuBar1.add(menuTecnico);

        menuAdministrador.setText("Administrador");

        jMenuItem3.setText("Profesores");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        menuAdministrador.add(jMenuItem3);

        jMenuItem4.setText("Configuracion");
        menuAdministrador.add(jMenuItem4);

        jMenuBar1.add(menuAdministrador);

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
   
    // Mostrar Los Profesores (SOLO ROOT)
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        try {
            MostrarProfesores nuevoProfesor = new MostrarProfesores(null, true);
            nuevoProfesor.setVisible(true);
            if(rol == 3){
                // Si es profesor cargamos sus incidencias
                CargarProfesorIncidencias(idProfesorGuardar);
            }else{
                CargarTodasIncidencias();                
            }
        } catch (SQLException ex) {
            Logger.getLogger(PantallaDeTrabajo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    
    
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
                    PanelModifyIncidencia();
                } catch (SQLException ex) {
                    Logger.getLogger(PantallaDeTrabajo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private void PanelModifyIncidencia() throws SQLException {
                
               int cuentaFilasSeleccionadas = tablaIncidencias.getSelectedRowCount();

                if (cuentaFilasSeleccionadas == 0) {
                    JOptionPane.showMessageDialog(PantallaDeTrabajo.this , "No hay filas seleccionadas", "Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    int resultado = JOptionPane.showConfirmDialog(PantallaDeTrabajo.this, "¿Esta seguro que desea modificar la incidencia?", "¿Seguro?", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (resultado == JOptionPane.YES_OPTION) {
                        int fila = tablaIncidencias.getSelectedRow();
                        var idIncidencia = tablaIncidencias.getModel().getValueAt(fila, 0).toString();
                        
                        // Abrimos la pantalla de modificar incidencia pasandole el id
                        ModificarIncidenciaProfesor modificar = new ModificarIncidenciaProfesor(null, true, idIncidencia);
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
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenu menuAdministrador;
    private javax.swing.JMenu menuIncidencias;
    private javax.swing.JMenu menuProfesorado;
    private javax.swing.JMenu menuTecnico;
    private javax.swing.JTable tablaIncidencias;
    // End of variables declaration//GEN-END:variables

    

}
