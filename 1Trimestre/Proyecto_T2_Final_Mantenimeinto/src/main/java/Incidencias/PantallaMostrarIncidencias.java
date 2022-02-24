/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Incidencias;

import Ajustes.PantallaAjustes;
import Incidencias.NuevaIncidencia;
import MenuProfesores.MostrarProfesores;
import Incidencias.ModificarIncidenciaProfesor;
import com.mycompany.proyecto_t2_final_mantenimeinto.Conectar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class PantallaMostrarIncidencias extends javax.swing.JDialog {

    Conectar conectar = null;
    int idProfesorGuardar;
    int rol;
    TableRowSorter<TableModel> rowSorter;
    DefaultTableModel dtm = new DefaultTableModel();
    boolean formatoFecha = true;
    SimpleDateFormat sdtDateLarge = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat sdtDateShort = new SimpleDateFormat("yy/MM/dd");

   
    public PantallaMostrarIncidencias(java.awt.Frame parent, boolean modal, int rol, int idProfesor) throws SQLException {

        super(parent, modal);
        initComponents();
        comprobarRol(rol, idProfesor);
        idProfesorGuardar = idProfesor;
        crearpopupmenu();
        
        // Aqui comrpobaremos el rol que tinen el usuario

    }

    //

    public void setFormatoFecha(boolean formatoFecha) {
        this.formatoFecha = formatoFecha;
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
                //si es tru formtato largo
                if (formatoFecha) {      
                    Date fecha = rs.getDate(7); 
                    incide[6] = (String) sdtDateShort.format(fecha);
                    //incide[6] = rs.getString(7);
                  //  Date fecha2 = rs.getDate(8); 
                  //  incide[7] = (String) sdtDateLarge.format(fecha2);
                 //   Date fecha3 = rs.getDate(9); 
                 //   incide[8] = (String) sdtDateLarge.format(fecha3);
                }else{
                    
                    Date fecha = rs.getDate(7); 
                    incide[6] = (String) sdtDateLarge.format(fecha);
                    //incide[6] = rs.getString(7);
                 //   Date fecha2 = rs.getDate(8); 
                 //   incide[7] = (String) sdtDateShort.format(fecha2);
                 //   Date fecha3 = rs.getDate(9); 
                 //   incide[8] = (String) sdtDateShort.format(fecha3);
                
                }
                
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
        jMenu3 = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(0, 153, 153));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCrearIncidencia.setBackground(new java.awt.Color(0, 153, 153));
        btnCrearIncidencia.setText("Crear Incidencia");
        btnCrearIncidencia.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        btnCrearIncidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearIncidenciaActionPerformed(evt);
            }
        });
        getContentPane().add(btnCrearIncidencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 588, 236, 28));

        tablaIncidencias.setBackground(new java.awt.Color(0, 153, 153));
        tablaIncidencias.setForeground(new java.awt.Color(255, 255, 255));
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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1247, 580));

        jMenuBar1.setBackground(new java.awt.Color(0, 102, 102));
        jMenuBar1.setBorder(new javax.swing.border.MatteBorder(null));
        jMenuBar1.setForeground(new java.awt.Color(0, 102, 102));

        menuIncidencia.setBackground(new java.awt.Color(0, 102, 102));
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

        menuProfesorado.setBackground(new java.awt.Color(0, 102, 102));
        menuProfesorado.setText("Profesorado");

        menuProfesores.setText("Lista Profesores");
        menuProfesores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuProfesoresActionPerformed(evt);
            }
        });
        menuProfesorado.add(menuProfesores);

        jMenuBar1.add(menuProfesorado);

        menuTecnico.setBackground(new java.awt.Color(0, 102, 102));
        menuTecnico.setText("Técnico");
        jMenuBar1.add(menuTecnico);
        jMenuBar1.add(jMenu3);

        jMenu1.setText("jMenu1");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

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
            Logger.getLogger(PantallaMostrarIncidencias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCrearIncidenciaActionPerformed

    private void menuProfesoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuProfesoresActionPerformed
        MostrarProfesores mostrarProfesores;
        try {
            mostrarProfesores = new MostrarProfesores(null, true, rol);
            mostrarProfesores.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(PantallaMostrarIncidencias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuProfesoresActionPerformed

    private void menuMisIncidenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMisIncidenciasActionPerformed
        try {
            CargarProfesorIncidencias(idProfesorGuardar);
        } catch (SQLException ex) {
            Logger.getLogger(PantallaMostrarIncidencias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuMisIncidenciasActionPerformed

    // Eliminar Filtro
    private void menuTodasIncidenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTodasIncidenciasActionPerformed
        try {
            CargarTodasIncidencias();
        } catch (SQLException ex) {
            Logger.getLogger(PantallaMostrarIncidencias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuTodasIncidenciasActionPerformed

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        // Abrimos la pantalla de modificar incidencia pasandole el id
        PantallaAjustes ajustes = new PantallaAjustes(this, true);
        ajustes.setVisible(true);
        try {
            CargarTodasIncidencias();
        } catch (SQLException ex) {
            Logger.getLogger(PantallaMostrarIncidencias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenu1MouseClicked

  
    
    
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
                        Logger.getLogger(PantallaMostrarIncidencias.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PantallaMostrarIncidencias.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private void PanelModifyIncidencia() throws SQLException, ParseException {
                
               int cuentaFilasSeleccionadas = tablaIncidencias.getSelectedRowCount();

                if (cuentaFilasSeleccionadas == 0) {
                    JOptionPane.showMessageDialog(PantallaMostrarIncidencias.this , "No hay filas seleccionadas", "Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    int resultado = JOptionPane.showConfirmDialog(PantallaMostrarIncidencias.this, "¿Esta seguro que desea modificar la incidencia?", "¿Seguro?", JOptionPane.YES_NO_CANCEL_OPTION);
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
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
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
