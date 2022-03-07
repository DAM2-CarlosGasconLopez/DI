/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Incidencias;

import com.mycompany.proyecto_t2_final_mantenimeinto.Conectar;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author carlo
 */
public class ModificarIncidenciaProfesor extends javax.swing.JDialog {
    
    String idIncidencia;
    
    Date dateini;

    DateTimeFormatter dtfShort = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
    DateTimeFormatter dtfLarge = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    SimpleDateFormat sdtDate = new SimpleDateFormat("yyyy/MM/dd");
    DefaultTableModel dtm = new DefaultTableModel();
    Conectar conectar = null;
    
    // Campos donde guardar lo modificado
    String descripcion;
    String descripcionTecnica;
    int horas;
    int estado;
    String fecha_ini_rep;
    String fecha_fin_rep;
    int nivel;
    int ubicacion;
    String observaciones;
    
    
    
    public ModificarIncidenciaProfesor(java.awt.Frame parent, boolean modal, String idIncidencia, int rol) throws SQLException, ParseException {
        super(parent, modal);
        initComponents();
        this.idIncidencia = idIncidencia;
        RefrescarIncidencia(idIncidencia);
        
        // Si es Profesor 
        if (rol == 3) {
            restriccionesProfesor();
        }
        
      
    }
    
    // Meter los datos en los objetos para mostrar la incidencia
    private void RefrescarIncidencia(String idIncidencia) throws SQLException, ParseException {
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
                    + "where i.id_incidencia = " + idIncidencia);

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
                

                
                
            }
            
            // Añadimos valores a lo campos
            txtIdIncidencia.setText(incide[0]);
            txtNombre.setText(incide[1]);
            txtDescripcion.setText(incide[2]);
            txtDescripcionTecnica.setText(incide[3]);  
            
            // Para el campo fecha lo combierto de string a double y de double a int
            // Si es null, le pongo valor 0
            if (incide[4] !=null) {
                double valor = Double.parseDouble(incide[4]);
                spinnerHoras.setValue((int)valor);
                
            }else{
                spinnerHoras.setValue(0);
            }
            
            
            // Consulta para recoger los ESTADOS
            rs = s.executeQuery("SELECT id_estado,estado from man_estado; ");
            String[] estado = new String[2];
            
            while(rs.next()){
                estado[0] = rs.getString(1);
                estado[1] = rs.getString(2);

                
                cboEstado.addItem(estado[0] + " " + estado[1]);
                
                if (incide[5].equals(estado[1])) {
                    cboEstado.setSelectedItem(estado[0] + " " + estado[1]);
                }
                
            }
            
            
            // FECHA
            txtFecha.setText(incide[6]);
            
            // Fecha Inicio
            if(incide[7] != null){
                
                String[] elminar = incide[7].split(" ");
                 String[] fecha=elminar[0].split("-");
                int year= Integer.parseInt(fecha[0]);
                // Al convertir la fecha a Calendar, me suma un mes, por lo tanto tengo que regularlo
                int mes= Integer.parseInt(fecha[1]) - 1;
                int dia= Integer.parseInt(fecha[2]);               
               
                Calendar calendario = new GregorianCalendar(year,mes,dia);
                calendarIni.setDate(calendario.getTime());

                
            }else{
                
            }
            
            // Fecha Fin
            if(incide[8] != null){
                
                String[] elminar = incide[8].split(" ");
                 String[] fecha=elminar[0].split("-");
                int year= Integer.parseInt(fecha[0]);
                // Al convertir la fecha a Calendar, me suma un mes, por lo tanto tengo que regularlo
                int mes= Integer.parseInt(fecha[1]) - 1;
                int dia= Integer.parseInt(fecha[2]);               
               
                Calendar calendario = new GregorianCalendar(year,mes,dia);
                calendarFin1.setDate(calendario.getTime());

                
            }else{
                
            }
            
            // Consulta de urgencia
            rs = s.executeQuery("SELECT id_urgencia, urgencia FROM man_urgencia;");
            String[] urgencia = new String[2];
            
            while(rs.next()){
                urgencia[0] = rs.getString(1);
                urgencia[1] = rs.getString(2);

                
                cboUrgencia.addItem(urgencia[0] + " " + urgencia[1]);
                
                if (incide[9].equals(urgencia[1])) {
                    cboUrgencia.setSelectedItem(urgencia[0] + " " + urgencia[1]);
                }
                
            }
            
            // Consulta de ubicacion 
            rs = s.executeQuery("select u.id_ubicacion, ubicacion, e.edificio\n" +
                                "from man_ubicacion u\n" +
                                "inner join man_edificio e on e.id_edificio = u.id_edificio");
            
            String[] ubicacion = new String[3];
            // Recorremos el Rs
            while (rs.next()) {                
                ubicacion[0] = rs.getString(1);
                ubicacion[1] = rs.getString(2);
                ubicacion[2] = rs.getString(3);
                
                cboUbicacion.addItem(ubicacion[0] + "  " + ubicacion[1] + "  " + ubicacion[2]);
                
                if ((ubicacion[1] + "  " + ubicacion[2]).equals(incide[10] + "  " + incide[11])) {
                    
                    cboUbicacion.setSelectedItem(ubicacion[0] + "  " + ubicacion[1] + "  " + ubicacion[2]);
                }
            }
            
            txtObservaciones.setText(incide[12]);
            
            
           

        } else {
            JOptionPane.showMessageDialog(this, "conexion fallida");
        }
      
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtObservaciones = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtIdIncidencia = new javax.swing.JTextField();
        labelEstado = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        labelUrgencia = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        spinnerHoras = new javax.swing.JSpinner();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtDescripcionTecnica = new javax.swing.JTextArea();
        cboUrgencia = new javax.swing.JComboBox<>();
        cboUbicacion = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        calendarIni = new com.toedter.calendar.JDateChooser();
        cboEstado = new javax.swing.JComboBox<>();
        calendarFin1 = new com.toedter.calendar.JDateChooser();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(0, 153, 153));

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Profesor");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Descripcion");

        txtObservaciones.setColumns(20);
        txtObservaciones.setRows(5);
        jScrollPane2.setViewportView(txtObservaciones);

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane3.setViewportView(txtDescripcion);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Descripcion Técnica");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Referencia Incidencia");

        txtIdIncidencia.setEditable(false);
        txtIdIncidencia.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        labelEstado.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelEstado.setForeground(new java.awt.Color(255, 255, 255));
        labelEstado.setText("Estado Actual");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Inicio Reparación");

        labelUrgencia.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelUrgencia.setForeground(new java.awt.Color(255, 255, 255));
        labelUrgencia.setText("Nivel Urgencia");

        jLabel2.setBackground(new java.awt.Color(0, 102, 102));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("MODIFICAR INCIDENCIA");
        jLabel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Fin de Reparacion");

        spinnerHoras.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtDescripcionTecnica.setColumns(20);
        txtDescripcionTecnica.setRows(5);
        jScrollPane4.setViewportView(txtDescripcionTecnica);

        cboUrgencia.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        cboUbicacion.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Observaciones");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Ubicación");

        calendarIni.setDateFormatString("yyyy MM dd");

        cboEstado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        btnAceptar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Fecha");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Horas Reparación");

        txtNombre.setEditable(false);
        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtFecha.setEditable(false);
        txtFecha.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8)
                                    .addComponent(labelEstado)
                                    .addComponent(jLabel9))
                                .addGap(54, 54, 54))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel10))
                                .addGap(57, 57, 57)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNombre)
                                .addComponent(txtIdIncidencia)
                                .addComponent(jScrollPane3)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(spinnerHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 27, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel14)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                                            .addGap(16, 16, 16))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(labelUrgencia)
                                            .addGap(12, 12, 12))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel15)
                                            .addGap(18, 18, 18)))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtFecha, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                                        .addComponent(cboUrgencia, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cboUbicacion, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(calendarIni, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(calendarFin1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGap(92, 92, 92)
                                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                                    .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(57, 57, 57))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(calendarIni, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtIdIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel12)))
                    .addComponent(jLabel7))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(calendarFin1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboUrgencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelUrgencia))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(spinnerHoras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelEstado)
                            .addComponent(cboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Guardar la modificacion de la incidencia
    private void GuardarCamposVariables() {
        descripcion = txtDescripcion.getText();
        descripcionTecnica = txtDescripcionTecnica.getText();
        horas = (int) spinnerHoras.getValue();
        String[] estadoArray = cboEstado.getSelectedItem().toString().split(" ");
        estado = Integer.parseInt(estadoArray[0]);
        
        
        if (calendarIni.getDate() == null) {
            fecha_ini_rep = null;
        }else{
            fecha_ini_rep = sdtDate.format(calendarIni.getDate());
        }
        
        if (calendarFin1.getDate() == null) {
            fecha_fin_rep = null;
        }else{
            fecha_fin_rep = sdtDate.format(calendarFin1.getDate());
        
        }

        String[] nivelUrge = cboUrgencia.getSelectedItem().toString().split(" ");
        nivel = Integer.parseInt(nivelUrge[0]);
        String[] ubicacionArray = cboUbicacion.getSelectedItem().toString().split("  ");
        ubicacion = Integer.parseInt(ubicacionArray[0]);
        observaciones = txtObservaciones.getText();
        
        
        
    }
    
    // Actualizamos en la base de datos la modificacion
    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
      
        GuardarCamposVariables();
        
        Connection conexion = conectar.getConnection();

       

        if (conexion != null) {

            try {
                // Le pasamos la consulta
                String sql ="update man_incidencias set \n" +
                            "	 descripcion = ?, \n" +
                            "    desc_tecnica = ?, \n" +
                            "    horas = ?, \n" +
                            "    id_estado = ?, \n" +
                            "    fecha_ini_rep = ?, \n" +
                            "    fecha_fin_rep = ?, \n" +
                            "    nivel_urgencia = ?, \n" +
                            "    id_ubicacion = ?, \n" +
                            "    observaciones = ? \n" +
                            "where id_incidencia = " + idIncidencia + ";";
                System.out.println(sql);

                PreparedStatement ps = null;
                ps = conexion.prepareStatement(sql);

                ps.setString(1, descripcion);
                ps.setString(2, descripcionTecnica);
                ps.setInt(3, horas);
                ps.setInt(4, estado);
                ps.setString(5, fecha_ini_rep);
                ps.setString(6, fecha_fin_rep);
                ps.setInt(7, nivel);
                ps.setInt(8, ubicacion);
                ps.setString(9, observaciones);
                
                System.out.println(sql);
                System.out.println(ps);
               

                ps.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(ModificarIncidenciaProfesor.class.getName()).log(Level.SEVERE, null, ex);
            }

            dispose();

        }
        else{
            JOptionPane.showMessageDialog(this, "conexion fallida");
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    // Restricciones si no tiene permisos
     private void restriccionesProfesor() {
        txtDescripcionTecnica.setEditable(false);
        spinnerHoras.setEnabled(false);
        cboEstado.setEnabled(false);
        calendarIni.setEnabled(false);
        calendarFin1.setEnabled(false);
        cboUrgencia.setEnabled(false);
    }
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private com.toedter.calendar.JDateChooser calendarFin1;
    private com.toedter.calendar.JDateChooser calendarIni;
    private javax.swing.JComboBox<String> cboEstado;
    private javax.swing.JComboBox<String> cboUbicacion;
    private javax.swing.JComboBox<String> cboUrgencia;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel labelEstado;
    private javax.swing.JLabel labelUrgencia;
    private javax.swing.JSpinner spinnerHoras;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextArea txtDescripcionTecnica;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtIdIncidencia;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextArea txtObservaciones;
    // End of variables declaration//GEN-END:variables
   
    
}
