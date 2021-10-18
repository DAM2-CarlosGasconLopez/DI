
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
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author damA
 */
public class PantallaPrinciapl extends javax.swing.JFrame {
    List<Clientes> listaclientes = new ArrayList<Clientes>();
    Conectar conectar = null;
    PreparedStatement ps = null;
    /**
     * Creates new form PantallaPrinciapl
     */
    public PantallaPrinciapl() throws SQLException {
        initComponents();
        refrescarTabla();
        refrescarPoput();
    }
    
    public void anadirCliente(Clientes clientes) throws SQLException{
    
     listaclientes.add(clientes);
     refrescarTabla();
       
    }
    private void refrescarTabla() throws SQLException{
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(new String[]{"Id","Profesion","Edad","nº Hermanos","Sexo","Deporte","Compras","Television","Cine"});
        
        
        //for (Clientes cliente: listaclientes){
        //    dtm.addRow(cliente.toArrayString());
        //}
        
        jTableDatos.setModel(dtm);
        
        conectar = new Conectar();
        Connection conexion = conectar.getConnection();
        if (conexion != null) {
            
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery("select * from usuarios");
                       
            String user[] = new String[9];
                       
           
            while (rs.next()) {
                user[0] = rs.getString(1);
                user[1] = rs.getString(2);
                user[2] = rs.getString(3);
                user[3] = rs.getString(4);
                user[4] = rs.getString(5);
                user[5] = rs.getString(6);
                user[6] = rs.getString(7);
                user[7] = rs.getString(8);
                user[8] = rs.getString(9);
              
                
                dtm.addRow(user);
            }
                                    
        }
        else{
            JOptionPane.showMessageDialog(this, "conexion fallida");
        }
        
        
    }
    
    private void refrescarPoput() {
    
        JPopupMenu poputMenu = new JPopupMenu();
        JMenuItem menuItemAdd = new JMenuItem("Añadir nuevo usuario");
        JMenuItem menuItemRemove = new JMenuItem("Borrar usuario seleccionado");
        JMenuItem menuItemRemoveAll = new JMenuItem("Borrar toda la tabla");
        
        poputMenu.add(menuItemAdd);
        poputMenu.add(menuItemRemove);
        poputMenu.add(menuItemRemoveAll);
        
        jTableDatos.setComponentPopupMenu(poputMenu);
        
        menuItemAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    anadirUsuario();
                } catch (SQLException ex) {
                    Logger.getLogger(PantallaPrinciapl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        
        menuItemRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    eliminarUsuario();
                } catch (SQLException ex) {
                    Logger.getLogger(PantallaPrinciapl.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
        menuItemRemoveAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    eliminarTodo();
                } catch (SQLException ex) {
                    Logger.getLogger(PantallaPrinciapl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
       
    }       
            
    private void anadirUsuario() throws SQLException{
        /*IntroducirDatos intDatos = new IntroducirDatos (this,true);
        intDatos.setVisible(true);*/
        refrescarTabla();
        
        
    }
    
    private void eliminarUsuario() throws SQLException{ 
        if (jTableDatos.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila, Gracias","Error",JOptionPane.WARNING_MESSAGE);
        }else{
            
            
            
            int result =  JOptionPane.showConfirmDialog(this, "¿Estas seguro de eliminar el usuario?","¿Seguro?",JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION){
                
                String id = jTableDatos.getValueAt(jTableDatos.getSelectedRow(), 0).toString();
                
                conectar = new Conectar();
                Connection conexion = conectar.getConnection();
                                
                String SQL = "DELETE FROM aficiones.usuarios WHERE idusuarios='"+id+"'"; 

                ps = conexion.prepareStatement(SQL);
                ps.execute();
                
                refrescarTabla();
                
                JOptionPane.showMessageDialog(null, "¡El usuario fué eliminado!");
                
            }else{
                
            JOptionPane.showConfirmDialog(this,"Fila no borrada","",JOptionPane.ERROR_MESSAGE);
            
            }
            
            
        }
        
    }
    
    private void eliminarTodo() throws SQLException{
        
        int result =  JOptionPane.showConfirmDialog(this, "¿Estas seguro de eliminar toda la tabla?","¿Seguro?",JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION){
            
            
            conectar = new Conectar();
            Connection conexion = conectar.getConnection();
            
            String SQL = "DELETE FROM aficiones.usuarios";
            ps = conexion.prepareStatement(SQL);
            ps.execute();
                
            refrescarTabla();
            JOptionPane.showMessageDialog(null, "La tabla se a eliminado");
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDatos = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItemAltas = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableDatos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableDatos);

        jMenu2.setText("Personal");

        jMenuItemAltas.setText("Altas");
        jMenuItemAltas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAltasActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemAltas);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 910, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemAltasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAltasActionPerformed
        // TODO add your handling code here:
        IntroducirDatos intDatos = new IntroducirDatos (this, true);
        intDatos.setVisible(true);
        try {
            refrescarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(PantallaPrinciapl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItemAltasActionPerformed
        
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PantallaPrinciapl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaPrinciapl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaPrinciapl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaPrinciapl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new PantallaPrinciapl().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(PantallaPrinciapl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemAltas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableDatos;
    // End of variables declaration//GEN-END:variables
}
