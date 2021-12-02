/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carlos_gascon_lopez_t1;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.util.Locale.filter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author damA
 */
public class PantallaSegunda extends javax.swing.JFrame {
    
    conxionBD_Examen conectar = null;
    DefaultTableModel dtm = new DefaultTableModel();
    TableRowSorter<TableModel> tr;
    

    
    public PantallaSegunda(PantallaLogin jj, boolean b) throws SQLException {
        initComponents();
        RefrescarTabla();
        
        // Añadimos por cada lookandfeel que tenemos por defecto un jmnuitem en el menuAspectos
        for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()){
            var name = laf.getName();
            JMenuItem item = new JMenuItem(name);
            
            // Añadimos accion a cada uno de los menus
            item.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    
                    try {
                        // Llamamos al metodo lookandfeel
                        lookandfeel(evt,laf.getClassName());
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(PantallaSegunda.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InstantiationException ex) {
                        Logger.getLogger(PantallaSegunda.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(PantallaSegunda.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedLookAndFeelException ex) {
                        Logger.getLogger(PantallaSegunda.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }       
            });
            
            menuAspectos.add(item);
            
        }
    }

    
    // Metodo donde le pasame¡os el lookandfeel a la pantalla
    private void lookandfeel(ActionEvent evt,String name) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {  
        // Recogemos el nombre de la clase
        UIManager.setLookAndFeel(name);
        SwingUtilities.updateComponentTreeUI(this);
   }

  
    
    public void RefrescarTabla() throws SQLException{
        
        // Genero el modelo de la tabla
        
        dtm.setColumnIdentifiers(new String[]{"Id","Nombre","Apellido","Profesion"});
        
        // Creo la conxion a la bd
        conectar = new conxionBD_Examen();
        Connection conexion = conectar.getConnection();
        // Si la conexion no es nula...
        if (conexion != null) {
            
            
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM examen_di.examen;");
                       
            String peli[] = new String[8];
                       
           // Mientras existan filas por recorrer...
            while (rs.next()) {
                // Extraemos los datos de la bd
                peli[0] = rs.getString(1);
                peli[1] = rs.getString(2);
                peli[2] = rs.getString(3);
                peli[3] = rs.getString(4);
              
                // Añadimos a la fila de la tabla los datos recien extraidos
                dtm.addRow(peli);
            }
            //Le pasamos el modelo con los datos a la tabla
            tablaDatos.setModel(dtm);
            tr = new TableRowSorter<TableModel>(dtm);
            tablaDatos.setRowSorter(tr); 
                                    
        }
        else{
            
            JOptionPane.showMessageDialog(this, "conexion fallida");
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable();
        txtSeleccionados = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuFiltro = new javax.swing.JMenu();
        itemMenuProfesion = new javax.swing.JMenuItem();
        menuAspectos = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tablaDatos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaDatos);

        menuFiltro.setText("Filtrar");

        itemMenuProfesion.setText("Filtrar Por Profesion");
        itemMenuProfesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuProfesionActionPerformed(evt);
            }
        });
        menuFiltro.add(itemMenuProfesion);

        jMenuBar1.add(menuFiltro);

        menuAspectos.setText("Aspectos");
        jMenuBar1.add(menuAspectos);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 879, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSeleccionados, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtSeleccionados, javax.swing.GroupLayout.DEFAULT_SIZE, 12, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemMenuProfesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuProfesionActionPerformed

        // Pido el filtro
        String filtro = JOptionPane.showInputDialog("Filtrar en profesion por: ");
        
        // Realizo la filtracion por la palabra sin que me de errores con mayusculas ni minsuculas
        tr.setRowFilter(RowFilter.regexFilter("(?i)"+filtro, 3));

        // Mostraremos el titulo de la pantalla
        this.setTitle("Filtrado de profesión por: " + filtro);
        
        // Contamos las filas que tenemos mostrando
        int filas = tablaDatos.getRowCount();
        
        // Mandamos un texto con las fils que hay
        txtSeleccionados.setText("Se han seleccionado " + filas + " elementos" );
     
        
        
        
               
       
        
      
        
        
        
        
    }//GEN-LAST:event_itemMenuProfesionActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem itemMenuProfesion;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenu menuAspectos;
    private javax.swing.JMenu menuFiltro;
    private javax.swing.JTable tablaDatos;
    private javax.swing.JLabel txtSeleccionados;
    // End of variables declaration//GEN-END:variables
}
