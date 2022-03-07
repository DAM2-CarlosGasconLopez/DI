/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuProfesores;

import Ajustes.PantallaAjustes;
import Email.PantallaEmail;
import com.mycompany.proyecto_t2_final_mantenimeinto.Conectar;
import com.opencsv.CSVReader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
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
        /* JavaHelp */ CargarAyuda();
        FiltrarCBORefrescar();
        RefrescarProfesores();

        // Si es Root Creamos el Poput y mostramos el boton de añadir profesor
        if (rol == 1) {
            crearpopupmenu();
            btnAddProfesor.setVisible(true);
        } else {
            btnAddProfesor.setVisible(false);
        }

    }

    // Filtrar
    public void FiltrarCBORefrescar() throws SQLException {
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

    private void CargarAyuda() {
        File fichero = null;
        String separ = fichero.separator;

        fichero = new File("src" + separ + "main" + separ + "java" + separ + "help" + separ + "help_set.hs");
        URL hsUrl = null;
        try {

            hsUrl = fichero.toURI().toURL();
            HelpSet helsep = new HelpSet(getClass().getClassLoader(), hsUrl);
            HelpBroker hp = helsep.createHelpBroker();

            //hp.enableHelpOnButton(btnAyuda, "aplicacion", helsep);
            hp.enableHelpKey(this.getRootPane(), "email", helsep);
            hp.enableHelpKey(jPanel2, "email", helsep);

        } catch (HelpSetException ex) {
            Logger.getLogger(PantallaAjustes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(PantallaAjustes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Hacemos un SELECT con todas las INCIDENCIAS
    public void RefrescarProfesores() throws SQLException {
        DefaultTableModel dtm = new DefaultTableModel();
        // Modelo de la tabla
        dtm.setColumnIdentifiers(new String[]{"Id", "Login", "Nombre", "E-Mail", "Activo", "Rol", "Departamento"});

        // Conexión
        conectar = new Conectar();
        Connection conexion = conectar.getConnection();

        if (conexion != null) {

            Statement s = conexion.createStatement();
            // Le pasamos la consulta
            ResultSet rs = s.executeQuery("SELECT p.id_profesor, p.login, p.nombre_completo, p.email, if(p.activo = 1,\"Si\",\"No\"),"
                    + "r.rol,d.departamento_corto\n"
                    + "FROM fp_profesor p\n"
                    + "inner join fp_rol r on r.id_rol = p.id_rol\n"
                    + "inner join fp_departamento d on d.id_departamento = p.id_departamento;");

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

        } else {
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
    public void cambiarPorRoot() throws SQLException {
        int cuentaFilasSeleccionadas = tableProfesores.getSelectedRowCount();

        if (cuentaFilasSeleccionadas == 0) {
            JOptionPane.showMessageDialog(this, "No hay filas seleccionadas", "Error", JOptionPane.WARNING_MESSAGE);
        } else {
            int resultado = JOptionPane.showConfirmDialog(this, "¿Cambiar a Root?", "¿Seguro?", JOptionPane.YES_NO_CANCEL_OPTION);
            if (resultado == JOptionPane.YES_OPTION) {
                int fila = tableProfesores.getSelectedRow();
                var id = tableProfesores.getModel().getValueAt(fila, 0).toString();

                // Conexiones
                PreparedStatement s = null;
                conectar = new Conectar();
                Connection connection = conectar.getConnection();
                var sql = "update fp_profesor set id_rol = 1 where id_profesor =" + id + ";";
                s = connection.prepareStatement(sql);
                s.executeUpdate(sql);

                RefrescarProfesores();
                JOptionPane.showMessageDialog(this, "Operacion Correcta");

            } else if (resultado == JOptionPane.NO_OPTION) {
                JOptionPane.showConfirmDialog(this, "No se ha cambiado a Root", "", JOptionPane.ERROR_MESSAGE);
            } else if (resultado == JOptionPane.CANCEL_OPTION) {
                JOptionPane.showConfirmDialog(this, "Operacion cancelada", "", JOptionPane.WARNING_MESSAGE);
            }
        }

    }

    // cambiar rol a tecnico
    public void cambiarPorTecnico() throws SQLException {

        int cuentaFilasSeleccionadas = tableProfesores.getSelectedRowCount();

        if (cuentaFilasSeleccionadas == 0) {
            JOptionPane.showMessageDialog(this, "No hay filas seleccionadas", "Error", JOptionPane.WARNING_MESSAGE);
        } else {
            int resultado = JOptionPane.showConfirmDialog(this, "¿Cambiar a Tecnico?", "¿Seguro?", JOptionPane.YES_NO_CANCEL_OPTION);
            if (resultado == JOptionPane.YES_OPTION) {
                int fila = tableProfesores.getSelectedRow();
                var id = tableProfesores.getModel().getValueAt(fila, 0).toString();

                // Conexiones
                PreparedStatement s = null;
                conectar = new Conectar();
                Connection connection = conectar.getConnection();
                var sql = "update fp_profesor set id_rol = 2 where id_profesor =" + id + ";";
                s = connection.prepareStatement(sql);
                s.executeUpdate(sql);

                RefrescarProfesores();
                JOptionPane.showMessageDialog(this, "Operacion Correcta");

            } else if (resultado == JOptionPane.NO_OPTION) {
                JOptionPane.showConfirmDialog(this, "No se ha cambiado a Técnico", "", JOptionPane.ERROR_MESSAGE);
            } else if (resultado == JOptionPane.CANCEL_OPTION) {
                JOptionPane.showConfirmDialog(this, "Operacion cancelada", "", JOptionPane.WARNING_MESSAGE);
            }
        }

    }

    // cambiar rol a profesor
    public void cambiarPorProfe() throws SQLException {

        int cuentaFilasSeleccionadas = tableProfesores.getSelectedRowCount();

        if (cuentaFilasSeleccionadas == 0) {
            JOptionPane.showMessageDialog(this, "No hay filas seleccionadas", "Error", JOptionPane.WARNING_MESSAGE);
        } else {
            int resultado = JOptionPane.showConfirmDialog(this, "¿Cambiar a Profesor?", "¿Seguro?", JOptionPane.YES_NO_CANCEL_OPTION);
            if (resultado == JOptionPane.YES_OPTION) {
                int fila = tableProfesores.getSelectedRow();
                var id = tableProfesores.getModel().getValueAt(fila, 0).toString();

                // Conexiones
                PreparedStatement s = null;
                conectar = new Conectar();
                Connection connection = conectar.getConnection();
                var sql = "update fp_profesor set id_rol = 3 where id_profesor =" + id + ";";
                s = connection.prepareStatement(sql);
                s.executeUpdate(sql);

                RefrescarProfesores();
                JOptionPane.showMessageDialog(this, "Operacion Correcta");

            } else if (resultado == JOptionPane.NO_OPTION) {
                JOptionPane.showConfirmDialog(this, "No se ha cambiado a Profesor", "", JOptionPane.ERROR_MESSAGE);
            } else if (resultado == JOptionPane.CANCEL_OPTION) {
                JOptionPane.showConfirmDialog(this, "Operacion cancelada", "", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    // mostrar JOptionPane con CheckBox para Activo
    public void MostrarJOP() throws SQLException {

        int cuentaFilasSeleccionadas = tableProfesores.getSelectedRowCount();

        if (cuentaFilasSeleccionadas == 0) {
            JOptionPane.showMessageDialog(this, "No hay filas seleccionadas", "Error", JOptionPane.WARNING_MESSAGE);
        } else {
            // Sacamos la fila
            int fila = tableProfesores.getSelectedRow();
            // Cogemos si es activo o no
            var activo = tableProfesores.getModel().getValueAt(fila, 4).toString();
            // Cogemos el id
            var id = tableProfesores.getModel().getValueAt(fila, 0).toString();

            // Generamos un checkBox
            JCheckBox check = new JCheckBox("Activo");
            // Pasamos al check box lo recogido en la tabla
            if (activo.equals("Si")) {
                check.setSelected(true);
            } else {
                check.setSelected(false);
            }

            var seleccionado = check.isSelected();

            // Generamos un mensage
            String msg = "Cambiar actividad del profesor";

            // Creamos el objeto con el checkbox + msg
            Object[] msgContent = {msg, check};

            // JOptionPane para guardar el cambia recien realizado en el checkbox
            int n = JOptionPane.showConfirmDialog(this, msgContent, "Altas y Bajas", JOptionPane.OK_CANCEL_OPTION);

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
                        JOptionPane.showMessageDialog(this, "Operacion Correcta");

                    }

                    // Si no,...
                } else {

                    // Si antes era true y ahora es false, confirmamos operacion
                    if (seleccionado != false) {
                        // Cambiamos a desactivo el profesor con el id seleccionado
                        var sql = "update fp_profesor set activo = 0 where id_profesor =" + id + ";";
                        s = connection.prepareStatement(sql);
                        s.executeUpdate(sql);

                        RefrescarProfesores();

                        JOptionPane.showMessageDialog(this, "Operacion Correcta");
                    }
                }

            }

        }

    }

    // cambiar contraseña profesor
    public void ReestablecerContraseñaPOP() throws SQLException {
        int cuentaFilasSeleccionadas = tableProfesores.getSelectedRowCount();

        if (cuentaFilasSeleccionadas == 0) {
            JOptionPane.showMessageDialog(this, "No hay filas seleccionadas", "Error", JOptionPane.WARNING_MESSAGE);
        } else {
            int resultado = JOptionPane.showConfirmDialog(this, "¿Esta seguro que desea Reestablecer la contraseña?", "¿Seguro?", JOptionPane.YES_NO_CANCEL_OPTION);
            if (resultado == JOptionPane.YES_OPTION) {
                int fila = tableProfesores.getSelectedRow();
                var id = tableProfesores.getModel().getValueAt(fila, 0).toString();

                // Conexiones
                PreparedStatement s = null;
                conectar = new Conectar();
                Connection connection = conectar.getConnection();
                var sql = "update fp_profesor set password = 'c332fde7539a87108ad5e85ae431c697' where id_profesor = " + id + ";";
                s = connection.prepareStatement(sql);
                s.executeUpdate(sql);

                RefrescarProfesores();
                JOptionPane.showMessageDialog(this, "Operacion Correcta");

            } else if (resultado == JOptionPane.NO_OPTION) {
                JOptionPane.showConfirmDialog(this, "No se ha cambiado la contraseña", "", JOptionPane.ERROR_MESSAGE);
            } else if (resultado == JOptionPane.CANCEL_OPTION) {
                JOptionPane.showConfirmDialog(this, "Operacion cancelada", "", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    // Fin POPUT

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPopupMenu2 = new javax.swing.JPopupMenu();
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
        btnEnviarEmail = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btnExportar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        btnImportar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProfesores = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Activo");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Filtrar Por:");
        jLabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Rol");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Departamento");

        btnRol.setBackground(new java.awt.Color(255, 255, 255));
        btnRol.setText("OK");
        btnRol.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRolActionPerformed(evt);
            }
        });

        btnActivo.setBackground(new java.awt.Color(255, 255, 255));
        btnActivo.setText("OK");
        btnActivo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnActivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActivoActionPerformed(evt);
            }
        });

        btnDepa.setBackground(new java.awt.Color(255, 255, 255));
        btnDepa.setText("OK");
        btnDepa.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDepa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepaActionPerformed(evt);
            }
        });

        brnSinFiltros.setBackground(new java.awt.Color(255, 255, 255));
        brnSinFiltros.setText("Eliminar Filtros");
        brnSinFiltros.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        brnSinFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brnSinFiltrosActionPerformed(evt);
            }
        });

        btnAddProfesor.setBackground(new java.awt.Color(255, 255, 255));
        btnAddProfesor.setText("Añadir Nuevo Profesor");
        btnAddProfesor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAddProfesor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProfesorActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("----------------------------");

        btnEnviarEmail.setBackground(new java.awt.Color(255, 255, 255));
        btnEnviarEmail.setText("E-Mail");
        btnEnviarEmail.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEnviarEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarEmailActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("----------------------------");

        btnExportar.setBackground(new java.awt.Color(255, 255, 255));
        btnExportar.setText("Exportar");
        btnExportar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("CSV");
        jLabel9.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));

        btnImportar.setBackground(new java.awt.Color(255, 255, 255));
        btnImportar.setText("Importar");
        btnImportar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnImportar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(brnSinFiltros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cboDepartamento, javax.swing.GroupLayout.Alignment.LEADING, 0, 124, Short.MAX_VALUE)
                                    .addComponent(cboActivo, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnActivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnDepa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cboRol, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(btnEnviarEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAddProfesor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addGap(12, 12, 12)
                .addComponent(btnAddProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEnviarEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImportar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        tableProfesores.setForeground(new java.awt.Color(0, 153, 153));
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1424, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));

        jLabel2.setBackground(new java.awt.Color(0, 153, 153));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Lista de Profesores en la Base de Datos");
        jLabel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
        ordenar.setRowFilter(RowFilter.regexFilter("", 1));
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
        } else {
            ordenar.setRowFilter(RowFilter.regexFilter("(?i)" + "No", 4));
        }

    }//GEN-LAST:event_btnActivoActionPerformed

    // Boton FILTRAR por DEPARTAMENTO
    private void btnDepaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepaActionPerformed

        ordenar.setRowFilter(RowFilter.regexFilter("(?i)" + cboDepartamento.getSelectedItem().toString(), 6));
    }//GEN-LAST:event_btnDepaActionPerformed

    private void btnEnviarEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarEmailActionPerformed
        try {
            PantallaEmail email = new PantallaEmail(null, true);
            email.setVisible(true);
            RefrescarProfesores();
        } catch (SQLException ex) {
            Logger.getLogger(MostrarProfesores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnEnviarEmailActionPerformed

    // Boton exportar a la base de datos
    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed

        JFileChooser fileChooser = new JFileChooser();
        //Para que solo de deje escojer archivos csv
        fileChooser.setAcceptAllFileFilterUsed(false);
        //Ponemos la opcion de csv en el file chooser 
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.csv", "csv");
        fileChooser.setFileFilter(filtro);
        fileChooser.showOpenDialog(fileChooser);

        String ruta = fileChooser.getSelectedFile().getAbsolutePath();

        try {

            TableModel model = tableProfesores.getModel();

            FileWriter csv = new FileWriter(new File(ruta));

            for (int i = 0; i < model.getColumnCount(); i++) {
                csv.write(model.getColumnName(i) + ",");
            }

            csv.write("\n");

            for (int i = 0; i < model.getRowCount() - 1; i++) {
                for (int j = 0; j < model.getColumnCount() - 1; j++) {
                    System.out.println(model.getValueAt(i, j).toString());
                    csv.write(model.getValueAt(i, j).toString() + ",");

                }
                csv.write("\n");
            }

            csv.close();
            
        } catch (IOException e) {
            System.out.println(" Error al exportar ");
        }

    }//GEN-LAST:event_btnExportarActionPerformed

    // Boton importar a la base de datos
    private void btnImportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportarActionPerformed
        // Conexion
        conectar = new Conectar();
        Connection conexion = conectar.getConnection();

        try {

            JFileChooser fileChooser = new JFileChooser();
            //Para que solo de deje escojer archivos csv
            fileChooser.setAcceptAllFileFilterUsed(false);
            //Ponemos la opcion de csv en el file chooser 
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.csv", "csv");
            fileChooser.setFileFilter(filtro);
            fileChooser.showOpenDialog(fileChooser);

            String ruta = fileChooser.getSelectedFile().getAbsolutePath();

            CSVReader reader = new CSVReader(new FileReader(ruta));
            System.out.println(reader);
            System.out.println(ruta);

            // Hacemos una lista de strings para guardar lo del archivo
            List<String[]> entrada = reader.readAll();

            // Creamos un array para separar una linea del csv
            String[] separador;
            System.out.println(entrada.size());

            for (int j = 1; j < entrada.size(); j++) {

                // Recogemos los datos y los metemos en el array separador dividido por comas
                String[] palabra = new String[6];
                palabra = entrada.get(j);
                separador = palabra[0].split(",");

                // Le pasamos la consulta              
                String sql = "insert into fp_profesor (login,password,nombre_completo,email,activo,id_rol,id_departamento)\n"
                        + "values (?,?,?,?,?,?,?);";

                PreparedStatement ps = null;
                ps = conexion.prepareStatement(sql);

                ps.setString(1, separador[0]);
                ps.setString(2, "c332fde7539a87108ad5e85ae431c697");
                ps.setString(3, separador[1]);
                ps.setString(4, separador[2]);
                ps.setString(5, separador[3]);
                ps.setString(6, separador[4]);
                ps.setString(7, separador[5]);

                // Ejecutamos la consulta
                ps.executeUpdate();

            }

            //tableProfesores.setModel(dtm);
            RefrescarProfesores();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(MostrarProfesores.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | SQLException ex) {
            Logger.getLogger(MostrarProfesores.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnImportarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton brnSinFiltros;
    private javax.swing.JButton btnActivo;
    private javax.swing.JButton btnAddProfesor;
    private javax.swing.JButton btnDepa;
    private javax.swing.JButton btnEnviarEmail;
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnImportar;
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
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableProfesores;
    // End of variables declaration//GEN-END:variables
}
