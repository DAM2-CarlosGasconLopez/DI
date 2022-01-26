/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyecto_t2_final_mantenimeinto.Clases;

/**
 *
 * @author damA
 */
public class Profesor {
    
    private int id_profesor;
    private String login;
    private String password;
    private String nombre_completo;
    private String email;
    private int activo;
    private String cod_pass;

    
    public Profesor() {
    }
    

    public Profesor(int id_profesor, String login, String password, String nombre_completo, String email, int activo, String cod_pass) {
        this.id_profesor = id_profesor;
        this.login = login;
        this.password = password;
        this.nombre_completo = nombre_completo;
        this.email = email;
        this.activo = activo;
        this.cod_pass = cod_pass;
    }

    public int getId_profesor() {
        return id_profesor;
    }

    public void setId_profesor(int id_profesor) {
        this.id_profesor = id_profesor;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public String getCod_pass() {
        return cod_pass;
    }

    public void setCod_pass(String cod_pass) {
        this.cod_pass = cod_pass;
    }
    
    
    
    
}
