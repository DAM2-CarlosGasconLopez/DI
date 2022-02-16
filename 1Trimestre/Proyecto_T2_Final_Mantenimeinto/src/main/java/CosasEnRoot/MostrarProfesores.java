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
import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author damA
 */
public class MostrarProfesores extends javax.swing.JDialog {
    Conectar conectar = null;
    TableRowSorter<TableModel> ordenar;
    
   
    public MostrarProfesores(java.awt.Frame parent, boolean modal, int rol) throws SQLException {
        super(parent, modal);
        initComponents();
        FiltrarCBORefrescar();
        RefrescarProfesores();
        
        // Si es Root Creamos el Poput y mostramos el boton de añadir profesor
        if (rol == 1) {
            crearpopupmenu();   
            btnAddProfesor.setVisible(true);
        }else{
            btnAddProfesor.setVisible(false);
        }
        
    }

    
    // Filtrar
    public void FiltrarCBORefrescar() throws SQLException{
        // Conexión
        conectar = new Conectar();
        Connection conexion = conectar.getConnection();
        
        if (conexion != null) {
            
            Statement s = conexion.createStatement();
            // Le pasamos la consulta del departamento
            ResultSet rs = s.executeQuery("SELECT departamento_corto FROM fp_departamento;");
            
            String[] array = new String[1];
            // Rellenamos el cbo de departamento
            while (rs.next()) {
               array[0] = rs.getString(1);
               cboDepartamento.addItem(array[0]);
            }
            
            // Consulta de Rol 
            rs = s.executeQuery("SELECT rol FROM fp_rol;");
            
            array = new String[1];
            // Recorremos el Rs
            while (rs.next()) {                
                array[0] = rs.getString(1);
                cboRol.addItem(array[0]);
            }
            
            // ComboBox de Activo
            cboActivo.addItem("Activo");
            cboActivo.addItem("No Activo");
            
            
           
        }
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
            
            // Habilitamos la ordenacion
            ordenar = new TableRowSorter<TableModel>(dtm);
            tableProfesores.setRowSorter(ordenar);
                                    
        }
        else{
            JOptionPane.showMessageDialog(this, "conexion fallida");
        }
    }
    
    // POPUP MENU
    private void crearpopupmenu() {
        // Generamos el Poput
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem contraseñaItem = new JMenuItem("Restablecer Contraseña (IESCH2022)");
        JMenuItem activoitem = new JMenuItem("Activo");
        
        JMenu rolProfesor = new JMenu("Cambiar rol");
        JMenuItem root = new JMenuItem("Poner como root");
        JMenuItem tecnico = new JMenuItem("Poner como tecnico");
        JMenuItem profesor = new JMenuItem("Poner como profesor");

       // Añadir Campos al PoputMenu
               
        popupMenu.add(contraseñaItem);
        popupMenu.add(activoitem);
        
        popupMenu.add(rolProfesor);
        
        rolProfesor.add(root);
        rolProfesor.add(tecnico);
        rolProfesor.add(profesor);
        
       
        tableProfesores.setComponentPopupMenu(popupMenu);
       
        // Le damos accion a los items del PoputMenu
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
        
        activoitem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    MostrarJOP();
                } catch (SQLException ex) {
                    Logger.getLogger(MostrarProfesores.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

        root.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    cambiarPorRoot();
                } catch (SQLException ex) {
                    Logger.getLogger(MostrarProfesores.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            
        });
         
        tecnico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    cambiarPorTecnico();
                } catch (SQLException ex) {
                    Logger.getLogger(MostrarProfesores.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }); 
        
        profesor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    cambiarPorProfe();
                } catch (SQLException ex) {
                    Logger.getLogger(MostrarProfesores.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }); 
    }
    
    // cambiar rol a root
    public void cambiarPorRoot() throws SQLException{
        int cuentaFilasSeleccionadas = tableProfesores.getSelectedRowCount();

        if (cuentaFilasSeleccionadas == 0) {
            JOptionPane.showMessageDialog(this, "No hay filas seleccionadas","Error",JOptionPane.WARNING_MESSAGE);  
        } else {
            int resultado=JOptionPane.showConfirmDialog(this, "¿Cambiar a Root?","¿Seguro?", JOptionPane.YES_NO_CANCEL_OPTION);
            if (resultado==JOptionPane.YES_OPTION) {
                int fila = tableProfesores.getSelectedRow();
                var id =tableProfesores.getModel().getValueAt(fila, 0).toString();
               
                // Conexiones
                PreparedStatement s = null;
                conectar = new Conectar();
                Connection connection = conectar.getConnection();               
                var sql = "update fp_profesor set id_rol = 1 where id_profesor =" + id + ";";                
                s = connection.prepareStatement(sql);
                s.executeUpdate(sql);
                
                RefrescarProfesores();
                JOptionPane.showMessageDialog(this,"Operacion Correcta");
                
               
               
            }else if (resultado==JOptionPane.NO_OPTION) {
                JOptionPane.showConfirmDialog(this, "No se ha cambiado a Root","",JOptionPane.ERROR_MESSAGE);
            }
            else if (resultado==JOptionPane.CANCEL_OPTION) {
                JOptionPane.showConfirmDialog(this, "Operacion cancelada","",JOptionPane.WARNING_MESSAGE);
            }
        }
         
    }
    // cambiar rol a tecnico
    public void cambiarPorTecnico() throws SQLException{
        
        int cuentaFilasSeleccionadas = tableProfesores.getSelectedRowCount();

        if (cuentaFilasSeleccionadas == 0) {
            JOptionPane.showMessageDialog(this, "No hay filas seleccionadas","Error",JOptionPane.WARNING_MESSAGE);  
        } else {
            int resultado=JOptionPane.showConfirmDialog(this, "¿Cambiar a Tecnico?","¿Seguro?", JOptionPane.YES_NO_CANCEL_OPTION);
            if (resultado==JOptionPane.YES_OPTION) {
                int fila = tableProfesores.getSelectedRow();
                var id =tableProfesores.getModel().getValueAt(fila, 0).toString();
               
                // Conexiones
                PreparedStatement s = null;
                conectar = new Conectar();
                Connection connection = conectar.getConnection();               
                var sql = "update fp_profesor set id_rol = 2 where id_profesor =" + id + ";";                
                s = connection.prepareStatement(sql);
                s.executeUpdate(sql);
                
                RefrescarProfesores();
                JOptionPane.showMessageDialog(this,"Operacion Correcta");
                
               
               
            }else if (resultado==JOptionPane.NO_OPTION) {
                JOptionPane.showConfirmDialog(this, "No se ha cambiado a Técnico","",JOptionPane.ERROR_MESSAGE);
            }
            else if (resultado==JOptionPane.CANCEL_OPTION) {
                JOptionPane.showConfirmDialog(this, "Operacion cancelada","",JOptionPane.WARNING_MESSAGE);
            }
        }
         
    }
    // cambiar rol a profesor
    public void cambiarPorProfe() throws SQLException{
         
        int cuentaFilasSeleccionadas = tableProfesores.getSelectedRowCount();

        if (cuentaFilasSeleccionadas == 0) {
            JOptionPane.showMessageDialog(this, "No hay filas seleccionadas","Error",JOptionPane.WARNING_MESSAGE);  
        } else {
            int resultado=JOptionPane.showConfirmDialog(this, "¿Cambiar a Profesor?","¿Seguro?", JOptionPane.YES_NO_CANCEL_OPTION);
            if (resultado==JOptionPane.YES_OPTION) {
                int fila = tableProfesores.getSelectedRow();
                var id =tableProfesores.getModel().getValueAt(fila, 0).toString();
               
                // Conexiones
                PreparedStatement s = null;
                conectar = new Conectar();
                Connection connection = conectar.getConnection();               
                var sql = "update fp_profesor set id_rol = 3 where id_profesor =" + id + ";";                
                s = connection.prepareStatement(sql);
                s.executeUpdate(sql);
                
                RefrescarProfesores();
                JOptionPane.showMessageDialog(this,"Operacion Correcta");
                
                
                
               
               
            }else if (resultado==JOptionPane.NO_OPTION) {
                JOptionPane.showConfirmDialog(this, "No se ha cambiado a Profesor","",JOptionPane.ERROR_MESSAGE);
            }
            else if (resultado==JOptionPane.CANCEL_OPTION) {
                JOptionPane.showConfirmDialog(this, "Operacion cancelada","",JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    // mostrar JOptionPane con CheckBox para Activo
    public void MostrarJOP() throws SQLException{
        
        int cuentaFilasSeleccionadas = tableProfesores.getSelectedRowCount();

        if (cuentaFilasSeleccionadas == 0) {
            JOptionPane.showMessageDialog(this, "No hay filas seleccionadas","Error",JOptionPane.WARNING_MESSAGE);  
        } else {
                // Sacamos la fila
                int fila = tableProfesores.getSelectedRow();
                // Cogemos si es activo o no
                var activo =tableProfesores.getModel().getValueAt(fila, 4).toString();
                // Cogemos el id
                var id =tableProfesores.getModel().getValueAt(fila, 0).toString();
                
                // Generamos un checkBox
                JCheckBox check = new JCheckBox("Activo");
                // Pasamos al check box lo recogido en la tabla
                if (activo.equals("Si")) {
                    check.setSelected(true);
                }else{
                    check.setSelected(false);
                }
                
                var seleccionado = check.isSelected();
                
                // Generamos un mensage
                String msg = "Cambiar actividad del profesor"; 

                // Creamos el objeto con el checkbox + msg
                Object[] msgContent = {msg,check }; 
                
                // JOptionPane para guardar el cambia recien realizado en el checkbox
                int n = JOptionPane.showConfirmDialog(this,msgContent, "Altas y Bajas",JOptionPane.OK_CANCEL_OPTION);
                
                // Conexiones
                PreparedStatement s = null;
                conectar = new Conectar();
                Connection connection = conectar.getConnection();     
                // Si la respuesta del panel es acepatar, entonces...
                if (n == 0) {
                    // Si esta seleccionado el check,...
                    if (check.isSelected() == true) {
                        
                        // si antes era false y ahora es true, confirmamos operacion
                        if (seleccionado != true) {
                            // Cambiamos a activo el profesor con el id seleccionado
                            var sql = "update fp_profesor set activo = 1 where id_profesor =" + id + ";";                
                            s = connection.prepareStatement(sql);
                            s.executeUpdate(sql);

                            RefrescarProfesores();
                            JOptionPane.showMessageDialog(this,"Operacion Correcta");
                            
                        }
                        
                    // Si no,...
                    }else{
                         
                        // Si antes era true y ahora es false, confirmamos operacion
                        if (seleccionado != false) {
                            // Cambiamos a desactivo el profesor con el id seleccionado
                            var sql = "update fp_profesor set activo = 0 where id_profesor =" + id + ";";                
                            s = connection.prepareStatement(sql);
                            s.executeUpdate(sql);

                            RefrescarProfesores();
                            
                            JOptionPane.showMessageDialog(this,"Operacion Correcta");            
                        }
                    }
                   
                
                }
               
                
                
                
            
        }
   
    }
    
    // cambiar contraseña profesor
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
                var sql = "update fp_profesor set password = 'c332fde7539a87108ad5e85ae431c697' where id_profesor = " + id + ";";                
                s = connection.prepareStatement(sql);
                s.executeUpdate(sql);
                
                RefrescarProfesores();
                JOptionPane.showMessageDialog(this,"Operacion Correcta");
                
               
               
            }else if (resultado==JOptionPane.NO_OPTION) {
                JOptionPane.showConfirmDialog(this, "No se ha cambiado la contraseña","",JOptionPane.ERROR_MESSAGE);
            }
            else if (resultado==JOptionPane.CANCEL_OPTION) {
                JOptionPane.showConfirmDialog(this, "Operacion cancelada","",JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    // Fin POPUT
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProfesores = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cboActivo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cboRol = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cboDepartamento = new javax.swing.JComboBox<>();
        btnRol = new javax.swing.JButton();
        btnActivo = new javax.swing.JButton();
        btnDepa = new javax.swing.JButton();
        brnSinFiltros = new javax.swing.JButton();
        btnAddProfesor = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

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

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Activo");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Filtrar Por:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Rol");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Departamento");

        btnRol.setText("OK");
        btnRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRolActionPerformed(evt);
            }
        });

        btnActivo.setText("OK");
        btnActivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActivoActionPerformed(evt);
            }
        });

        btnDepa.setText("OK");
        btnDepa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepaActionPerformed(evt);
            }
        });

        brnSinFiltros.setText("Eliminar Filtros");
        brnSinFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brnSinFiltrosActionPerformed(evt);
            }
        });

        btnAddProfesor.setText("Añadir Nuevo Profesor");
        btnAddProfesor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProfesorActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("-------------------------");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(brnSinFiltros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnAddProfesor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cboRol, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnRol, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cboDepartamento, javax.swing.GroupLayout.Alignment.LEADING, 0, 124, Short.MAX_VALUE)
                            .addComponent(cboActivo, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnDepa, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(btnActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(39, 39, 39))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRol, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboActivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActivo))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDepa))
                .addGap(36, 36, 36)
                .addComponent(brnSinFiltros)
                .addGap(35, 35, 35)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAddProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 982, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(289, 289, 289))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE))
                .addContainerGap(40, Short.MAX_VALUE))
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

    // Boton SIN FILTROS
    private void brnSinFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brnSinFiltrosActionPerformed
        ordenar.setRowFilter(RowFilter.regexFilter("",1));
    }//GEN-LAST:event_brnSinFiltrosActionPerformed
    // Boton FILTRAR por ROL   
    private void btnRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRolActionPerformed
        
        ordenar.setRowFilter(RowFilter.regexFilter("(?i)" + cboRol.getSelectedItem().toString(), 5));
    }//GEN-LAST:event_btnRolActionPerformed
    // Boton FILTRAR por ACTIVO
    private void btnActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActivoActionPerformed
      // Si el valor seleccionado0 en el cbo es Activo...
        if (cboActivo.getSelectedItem().equals("Activo")) {
            
            ordenar.setRowFilter(RowFilter.regexFilter("(?i)" + "Si", 4));
        
            
        // Si es No Activo
        }else{
            ordenar.setRowFilter(RowFilter.regexFilter("(?i)" + "No", 4));
        }
        
    }//GEN-LAST:event_btnActivoActionPerformed

    // Boton FILTRAR por DEPARTAMENTO
    private void btnDepaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepaActionPerformed
        
        ordenar.setRowFilter(RowFilter.regexFilter("(?i)" + cboDepartamento.getSelectedItem().toString(), 6));
    }//GEN-LAST:event_btnDepaActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton brnSinFiltros;
    private javax.swing.JButton btnActivo;
    private javax.swing.JButton btnAddProfesor;
    private javax.swing.JButton btnDepa;
    private javax.swing.JButton btnRol;
    private javax.swing.JComboBox<String> cboActivo;
    private javax.swing.JComboBox<String> cboDepartamento;
    private javax.swing.JComboBox<String> cboRol;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableProfesores;
    // End of variables declaration//GEN-END:variables
}
