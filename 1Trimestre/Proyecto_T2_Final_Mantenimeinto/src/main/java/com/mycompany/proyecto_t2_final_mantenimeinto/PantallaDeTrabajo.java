/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyecto_t2_final_mantenimeinto;

import com.mycompany.proyecto_t2_final_mantenimeinto.Clases.Profesor;
import java.sql.Connection;
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
public class PantallaDeTrabajo extends javax.swing.JDialog {
    
    Conectar conectar = null;
    List<Profesor> listaPelis = new ArrayList<Profesor>();
    int idProfesorGuardar;
    

    /**
     * Creates new form PantallaDeTrabajo
     */
    public PantallaDeTrabajo(java.awt.Frame parent, boolean modal, int rol, int idProfesor) throws SQLException {
       
        super(parent, modal);
        initComponents();
        System.out.println("dvsfdv)");
        RefrescarIncidencias();
        idProfesorGuardar = idProfesor;
        System.out.println("fdsd");
        // Aqui comrpobaremos el rol que tinen el usuario
        comprobarRol(rol);
        
        
    }
    
    
    // Hacemos un SELECT con todas las INCIDENCIAS
    public void RefrescarIncidencias() throws SQLException{
        DefaultTableModel dtm = new DefaultTableModel();
        // Modelo de la tabla
        dtm.setColumnIdentifiers(new String[]{"Id Incidencia","Creada por","Descripcion","Descripción Técnica","Horas","Estado","Lanzamiento Incidencia",
                                              "Inicio Reparacion","Fin Reparación","Nivel","Clase"}); //,"Edificio","Observaciones"});
        
        
        
        //for (Clientes cliente: listaclientes){
        //    dtm.addRow(cliente.toArrayString());
        //}
        
       
        // Conexión
        conectar = new Conectar();
        Connection conexion = conectar.getConnection();
        
        if (conexion != null) {
            
            
            Statement s = conexion.createStatement();
            // Le pasamos la consulta
            ResultSet rs = s.executeQuery("SELECT * FROM man_incidencias;");
            /*ResultSet rs = s.executeQuery("select i.id_incidencia, p.nombre_completo, i.descripcion, i.desc_tecnica,\n" +
                                          " i.horas, est.estado, i.fecha,i.fecha_ini_rep, i.fecha_fin_rep, urg.urgencia, \n" +
                                          " ubi.ubicacion, edi.edificio, i.observaciones\n" +
                                          "from man_incidencias i\n" +
                                          "inner join man_reparacion r on r.id_incidencia = i.id_incidencia\n" +
                                          "inner join fp_profesor p on p.id_profesor = r.id_profesor\n" +
                                          "inner join man_estado est on est.id_estado = i.id_estado\n" +
                                          "inner join man_urgencia urg on urg.id_urgencia = i.nivel_urgencia\n" +
                                          "inner join man_ubicacion ubi on ubi.id_ubicacion = i.id_ubicacion\n" +
                                          "inner join man_edificio edi on edi.id_edificio = ubi.id_edificio ");*/
                   
            // Generamos un array para recoger lo que pedimos en la consulta
            //String incide[] = new String[13];
            String incide[] = new String[11];
                       
           
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
                //incide[11] = rs.getString(12);
                //incide[12] = rs.getString(13);
                
              
                // Añadimos una fila a la tabla
                dtm.addRow(incide);
            }
            // Le ponemos modelo a la tabla
            tablaIncidencias.setModel(dtm);
                                    
        }
        else{
            JOptionPane.showMessageDialog(this, "conexion fallida");
        }
    }

    
    // COMPROBACION DEL ROL
    private void comprobarRol(int rol) {
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
		rolProfesor();	
		break;
	
            default:
		break;
	}
    }
    
    // Rol ROOT
    private void rolRoot() {

    }
    // Rol TECNICO
    private void rolTécnico() {
        menuAdministrador.setVisible(false);
    }
    
    // Rol PROFESOR
    private void rolProfesor() {
        menuTecnico.setVisible(false);
        menuAdministrador.setVisible(false);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tablaIncidencias = new javax.swing.JTable();
        btnCrearIncidencia = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuIncidencias = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        menuProfesorado = new javax.swing.JMenu();
        menuTecnico = new javax.swing.JMenu();
        menuAdministrador = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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
        jScrollPane2.setViewportView(tablaIncidencias);

        btnCrearIncidencia.setText("Crear Incidencia");
        btnCrearIncidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearIncidenciaActionPerformed(evt);
            }
        });

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
        jMenuBar1.add(menuAdministrador);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1077, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCrearIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(112, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCrearIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearIncidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearIncidenciaActionPerformed
        
        try {
            NuevaIncidencia newIncidencia = new NuevaIncidencia(null, true, idProfesorGuardar);
            newIncidencia.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(PantallaDeTrabajo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCrearIncidenciaActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCrearIncidencia;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenu menuAdministrador;
    private javax.swing.JMenu menuIncidencias;
    private javax.swing.JMenu menuProfesorado;
    private javax.swing.JMenu menuTecnico;
    private javax.swing.JTable tablaIncidencias;
    // End of variables declaration//GEN-END:variables

    

    
}
