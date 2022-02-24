/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Incidencias;

import com.mycompany.proyecto_t2_final_mantenimeinto.Conectar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author damA
 */
public class NuevaIncidencia extends javax.swing.JDialog {
    DateTimeFormatter dtfShort = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
    DateTimeFormatter dtfLarge = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    String[] array;
    Conectar conectar = null;
    int idProfesorActual;
    int idUbicacion;
    

    /**
     * Creates new form NuevaIncidencia
     */
    public NuevaIncidencia(java.awt.Frame parent, boolean modal, int idProfesor) throws SQLException {
        super(parent, modal);
        initComponents();
        idProfesorActual = idProfesor;
        refrescarPantalla();
    }

    private void refrescarPantalla() throws SQLException {
         // Conexión
        conectar = new Conectar();
        Connection conexion = conectar.getConnection();
        
        if (conexion != null) {
            
            Statement s = conexion.createStatement();
            // Le pasamos la consulta del nombre del profesor
            ResultSet rs = s.executeQuery("select nombre_completo\n" +
                                          "from fp_profesor\n" +
                                          "where id_profesor = "+idProfesorActual+"");
            // Rellenamos el texto del nombre
            while (rs.next()) {
               txtNombre.setText(rs.getString(1));
            }
            
            // Consulta de ubicacion 
            rs = s.executeQuery("select u.id_ubicacion, ubicacion, e.edificio\n" +
                                "from man_ubicacion u\n" +
                                "inner join man_edificio e on e.id_edificio = u.id_edificio");
            
            array = new String[3];
            // Recorremos el Rs
            while (rs.next()) {                
                array[0] = rs.getString(1);
                array[1] = rs.getString(2);
                array[2] = rs.getString(3);
                
                cboUbicacion.addItem(array[0] + "  " + array[1] + "  " + array[2]);
            }
            
            // Fecha actual
            
            txtFecha.setText(dtfLarge.format(LocalDateTime.now()));
            
           
        }
        else{
            JOptionPane.showMessageDialog(this, "conexion fallida");
        }
        
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        txtIdProfesor1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        txtObservaciones = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();
        cboUbicacion = new javax.swing.JComboBox<>();
        txtDescripcion = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        txtIdProfesor1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Profesor:");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));

        btnCancelar.setBackground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnAceptar.setBackground(new java.awt.Color(255, 255, 255));
        btnAceptar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAceptar.setText("Aceptar");
        btnAceptar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        txtObservaciones.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtFecha.setEditable(false);
        txtFecha.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        cboUbicacion.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtDescripcion.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtNombre.setEditable(false);
        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Observaciones");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Fecha");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Ubicación");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Descripcion");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Profesor");

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("NUEVA INCIDENCIA !!!");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(104, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(102, 102, 102))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7)
                                .addComponent(jLabel8)
                                .addComponent(jLabel3)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6))
                            .addGap(44, 44, 44)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtObservaciones)
                                .addComponent(txtFecha)
                                .addComponent(cboUbicacion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtDescripcion)
                                .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 46, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cboUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
       conectar = new Conectar();
        Connection conexion = conectar.getConnection();
        
        String ubi = cboUbicacion.getSelectedItem().toString();
        array = ubi.split("  ");
        System.out.println(ubi);
        
        if (conexion != null) {
            
            
           try {
               // Le pasamos la consulta              
               String sql ="insert into man_incidencias (id_profesor_crea,descripcion,id_estado,fecha,id_ubicacion,observaciones,nivel_urgencia) \n" +
                           "values (?,?,?,?,?,?,"+4+");";
               
            PreparedStatement ps = null;
            ps = conexion.prepareStatement(sql);

            ps.setInt(1, idProfesorActual);
            ps.setString(2, txtDescripcion.getText());
            ps.setInt(3, 1);
            ps.setString(4, dtfLarge.format(LocalDateTime.now()));
            ps.setInt(5, Integer.parseInt(array[0]));
            ps.setString(6, txtObservaciones.getText());
            
            ps.executeUpdate();
               
           } catch (SQLException ex) {
               Logger.getLogger(NuevaIncidencia.class.getName()).log(Level.SEVERE, null, ex);
           }
                       
           dispose();
            
                                    
        }
        else{
            JOptionPane.showMessageDialog(this, "conexion fallida");
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox<String> cboUbicacion;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtIdProfesor1;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtObservaciones;
    // End of variables declaration//GEN-END:variables

   
}
