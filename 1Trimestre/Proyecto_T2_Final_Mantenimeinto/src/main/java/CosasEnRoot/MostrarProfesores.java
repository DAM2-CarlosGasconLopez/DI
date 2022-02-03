/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CosasEnRoot;

import com.mycompany.proyecto_t2_final_mantenimeinto.Conectar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author damA
 */
public class MostrarProfesores extends javax.swing.JDialog {
    Conectar conectar = null;
   
    public MostrarProfesores(java.awt.Frame parent, boolean modal) throws SQLException {
        super(parent, modal);
        initComponents();
        RefrescarProfesores();
        crearpopupmenu();
    }

    
    
   
    
    
     // Hacemos un SELECT con todas las INCIDENCIAS
    public void RefrescarProfesores() throws SQLException{
        DefaultTableModel dtm = new DefaultTableModel();
        // Modelo de la tabla
        dtm.setColumnIdentifiers(new String[]{"Id","Login","Nombre","E-Mail","Activo","Rol","Departamento"});
        
        
        
 
        
       
        // Conexión
        conectar = new Conectar();
        Connection conexion = conectar.getConnection();
        
        if (conexion != null) {
            
            
            Statement s = conexion.createStatement();
            // Le pasamos la consulta
            ResultSet rs = s.executeQuery("SELECT p.id_profesor, p.login, p.nombre_completo, p.email, if(p.activo = 1,\"Si\",\"No\"),"
                    + "r.rol,d.departamento_corto\n" +
                      "FROM fp_profesor p\n" +
                      "inner join fp_rol r on r.id_rol = p.id_rol\n" +
                      "inner join fp_departamento d on d.id_departamento = p.id_departamento;");
                   
            // Generamos un array para recoger lo que pedimos en la consulta
            String incide[] = new String[7];
            //String incide[] = new String[11];
                       
           
            while (rs.next()) {
                incide[0] = rs.getString(1);
                incide[1] = rs.getString(2);
                incide[2] = rs.getString(3);
                incide[3] = rs.getString(4);
                incide[4] = rs.getString(5);
                incide[5] = rs.getString(6);
                incide[6] = rs.getString(7);
                
              
                // Añadimos una fila a la tabla
                dtm.addRow(incide);
            }
            // Le ponemos modelo a la tabla
            tableProfesores.setModel(dtm);
                                    
        }
        else{
            JOptionPane.showMessageDialog(this, "conexion fallida");
        }
    }
    
    // POPUP MENU
    private void crearpopupmenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem contraseñaItem = new JMenuItem("Restablecer Contraseña (IESCH2022)");
        JMenuItem activoitem = new JMenuItem("Activo");
        JMenuItem rolItem = new JMenuItem("Cambiar Rol");

       
               
        popupMenu.add(contraseñaItem);
        popupMenu.add(activoitem);
        popupMenu.add(rolItem);
       
        tableProfesores.setComponentPopupMenu(popupMenu);
       
        contraseñaItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    ReestablecerContraseñaPOP();
                } catch (SQLException ex) {
                    Logger.getLogger(MostrarProfesores.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
/*
        rolItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    buscarTematicaPOP();
                } catch (SQLException ex) {
                    Logger.getLogger(Peliculas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });  */
    }
    
     public void ReestablecerContraseñaPOP() throws SQLException{
        int cuentaFilasSeleccionadas = tableProfesores.getSelectedRowCount();

        if (cuentaFilasSeleccionadas == 0) {
            JOptionPane.showMessageDialog(this, "No hay filas seleccionadas","Error",JOptionPane.WARNING_MESSAGE);  
        } else {
            int resultado=JOptionPane.showConfirmDialog(this, "¿Esta seguro que desea Reestablecer la contraseña?","¿Seguro?", JOptionPane.YES_NO_CANCEL_OPTION);
            if (resultado==JOptionPane.YES_OPTION) {
                int fila = tableProfesores.getSelectedRow();
                var id =tableProfesores.getModel().getValueAt(fila, 0).toString();
               
                // Conexiones
                PreparedStatement s = null;
                conectar = new Conectar();
                Connection connection = conectar.getConnection();               
                var sql = "update fp_profesor set password = 'ESCH2022' where id_profesor = " + id + ";";                
                s = connection.prepareStatement(sql);
                s.executeUpdate(sql);
                
                RefrescarProfesores();
                
                
               
               
            }else if (resultado==JOptionPane.NO_OPTION) {
                JOptionPane.showConfirmDialog(this, "No se ha cambiado la contraseña","",JOptionPane.ERROR_MESSAGE);
            }
            else if (resultado==JOptionPane.CANCEL_OPTION) {
                JOptionPane.showConfirmDialog(this, "Operacion cancelada","",JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProfesores = new javax.swing.JTable();
        btnAddProfesor = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Lista de Profesores en la Base de Datos");

        tableProfesores.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableProfesores);

        btnAddProfesor.setText("Añadir Nuevo Profesor");
        btnAddProfesor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProfesorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(295, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(289, 289, 289))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnAddProfesor)
                        .addGap(29, 29, 29))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddProfesor)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddProfesorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProfesorActionPerformed
        try {
            AñadirProfesor addProvesor = new AñadirProfesor(null, true);
            addProvesor.setVisible(true);
            RefrescarProfesores();
        } catch (SQLException ex) {
            Logger.getLogger(MostrarProfesores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAddProfesorActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddProfesor;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableProfesores;
    // End of variables declaration//GEN-END:variables
}
