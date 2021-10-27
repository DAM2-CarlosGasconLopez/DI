/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Añadir;

import com.mycompany.practica_1._cine.Conexion_BD_Cine;
import com.mycompany.practica_1._cine.Directores;
import com.mycompany.practica_1._cine.MenuPrincipal;
import com.mycompany.practica_1._cine.Peliculas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author damA
 */
public class AddPeliculasAndElementos extends javax.swing.JDialog {

    public AddPeliculasAndElementos(java.awt.Frame parent, boolean modal) throws SQLException {
        super(parent, modal);
        initComponents();
        RefrescarDirectores(cbDirector);
        RefrescarTematica(cbTematica);
        RefrescarSala(cbSala);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        spinnerPrecio = new javax.swing.JSpinner();
        txtTitulo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbSala = new javax.swing.JComboBox<>();
        cbDirector = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        spinnerFecha = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        spinnerAño = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        cbTematica = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnDirector = new javax.swing.JButton();
        btnTematicas = new javax.swing.JButton();
        btnSala = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Titulo");

        spinnerPrecio.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Sala");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Director");

        jButton1.setText("Insertar nueva Pelicula");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Fecha Proyección");

        spinnerFecha.setModel(new javax.swing.SpinnerDateModel());
        spinnerFecha.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        spinnerFecha.setEditor(new javax.swing.JSpinner.DateEditor(spinnerFecha, "yyyy-MM-dd"));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Año Estreno");

        spinnerAño.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.DAY_OF_YEAR));
        spinnerAño.setEditor(new javax.swing.JSpinner.DateEditor(spinnerAño, "yyyy"));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Temática");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Añadir Nueva Película y Nuevos Elementos");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("precioEntrada");

        btnDirector.setText("Añadir Director");
        btnDirector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDirectorActionPerformed(evt);
            }
        });

        btnTematicas.setText("Añadir Temática");
        btnTematicas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTematicasActionPerformed(evt);
            }
        });

        btnSala.setText("Añadir Sala");
        btnSala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel5)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtTitulo, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbDirector, javax.swing.GroupLayout.Alignment.LEADING, 0, 139, Short.MAX_VALUE)
                                    .addComponent(cbTematica, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbSala, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnSala)
                                    .addComponent(btnTematicas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnDirector, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(spinnerFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spinnerAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spinnerPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbDirector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDirector))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(spinnerFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(spinnerAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbTematica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTematicas))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(spinnerPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbSala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSala))
                .addGap(34, 34, 34)
                .addComponent(jButton1)
                .addContainerGap(65, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (txtTitulo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Porfavor, inserte el titulo de la pelicula","Error", JOptionPane.ERROR_MESSAGE);

        }else{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy");

            String titulo = txtTitulo.getText();
            String director = cbDirector.getSelectedItem().toString();
            String fecha = simpleDateFormat.format(spinnerFecha.getValue()).toString();
            String año = simpleDateFormat2.format(spinnerAño.getValue()).toString();
            String tematica = cbTematica.getSelectedItem().toString();
            String precio =  spinnerPrecio.getValue().toString();
            String sala = cbSala.getSelectedItem().toString();

            String[] array = director.split(",");
            director = array[0];
            String[] array1 = tematica.split(",");
            tematica = array1[0];
            String[] array2 = sala.split(",");
            sala = array2[0];

            PreparedStatement ps = null;
            String sql;
            Connection conexion = new Conexion_BD_Cine().getConnection();

            try {
                sql ="insert into pelicula(titulo,director,fechaProyeccion,añoEstreno,tematica,precioEntrada,sala) values(?,?,?,?,?,?,?)";

                ps = conexion.prepareStatement(sql);

                ps.setString(1,titulo);
                ps.setString(2,director);
                ps.setString(3,fecha);
                ps.setString(4,año);
                ps.setString(5,tematica);
                ps.setString(6,precio );
                ps.setString(7,sala);

                ps.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(Peliculas.class.getName()).log(Level.SEVERE, null, ex);
            }

            dispose();
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnDirectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDirectorActionPerformed
        AddDirectores directores = null;
        directores = new AddDirectores(null, true);
        directores.setVisible(true);
        try {
            RefrescarDirectores(cbDirector);
        } catch (SQLException ex) {
            Logger.getLogger(AddPeliculasAndElementos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDirectorActionPerformed

    private void btnSalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalaActionPerformed
        AddSala sala = null;
        sala = new AddSala(null, true);
        sala.setVisible(true);
        try {
            RefrescarSala(cbSala);
        } catch (SQLException ex) {
            Logger.getLogger(AddPeliculasAndElementos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSalaActionPerformed

    private void btnTematicasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTematicasActionPerformed
        AddTemática directores = null;
        directores = new AddTemática(null, true);
        directores.setVisible(true);
        try {
            RefrescarTematica(cbTematica);
        } catch (SQLException ex) {
            Logger.getLogger(AddPeliculasAndElementos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnTematicasActionPerformed

    
    public void RefrescarSala(JComboBox cbsala) throws SQLException{
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(new String[]{"Id Sala", "Nombre", "Capacidad"});

        //for (Clientes cliente: listaclientes){
        //    dtm.addRow(cliente.toArrayString());
        //}
        Conexion_BD_Cine conectar = new Conexion_BD_Cine();
        Connection conexion = conectar.getConnection();
        if (conexion != null) {

            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery("select * from cine_di.sala");

            String sala[] = new String[4];

            while (rs.next()) {
                sala[0] = rs.getString(1);
                sala[1] = rs.getString(2);
                sala[2] = rs.getString(3);

                cbSala.addItem(sala[0]+", " + sala[1]);
            }
            

        } else {
            JOptionPane.showMessageDialog(this, "conexion fallida");
        }
    }
    public void RefrescarTematica(JComboBox cbTematica) throws SQLException{
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(new String[]{"Id Tematica", "Nombre",});

        //for (Clientes cliente: listaclientes){
        //    dtm.addRow(cliente.toArrayString());
        //}
        Conexion_BD_Cine conectar = new Conexion_BD_Cine();
        Connection conexion = conectar.getConnection();
        if (conexion != null) {

            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery("select * from cine_di.tematica");

            String tem[] = new String[4];

            while (rs.next()) {
                tem[0] = rs.getString(1);
                tem[1] = rs.getString(2);
                

                cbTematica.addItem(tem[0]+", " + tem[1]);
            }
            

        } else {
            JOptionPane.showMessageDialog(this, "conexion fallida");
        }
    }
    public void RefrescarDirectores(JComboBox cbDirector) throws SQLException {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(new String[]{"Id Director", "Nombre", "Apellidos", "Fecha Nacimiento"});

        //for (Clientes cliente: listaclientes){
        //    dtm.addRow(cliente.toArrayString());
        //}
        Conexion_BD_Cine conectar = new Conexion_BD_Cine();
        Connection conexion = conectar.getConnection();
        if (conexion != null) {

            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery("select * from cine_di.director");

            String peli[] = new String[4];

            while (rs.next()) {
                peli[0] = rs.getString(1);
                peli[1] = rs.getString(2);
                peli[2] = rs.getString(3);
                peli[3] = rs.getString(4);

                cbDirector.addItem(peli[0]+", " + peli[1]);
            }
            

        } else {
            JOptionPane.showMessageDialog(this, "conexion fallida");
        }

    }
    /**
     * @param args the command line arguments
     */    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDirector;
    private javax.swing.JButton btnSala;
    private javax.swing.JButton btnTematicas;
    private javax.swing.JComboBox<String> cbDirector;
    private javax.swing.JComboBox<String> cbSala;
    private javax.swing.JComboBox<String> cbTematica;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSpinner spinnerAño;
    private javax.swing.JSpinner spinnerFecha;
    private javax.swing.JSpinner spinnerPrecio;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}
