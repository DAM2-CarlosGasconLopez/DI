/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jreloj;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author damA
 */
public class propiedadAlarma implements Serializable{
    
    private Date fecha;
    private boolean activar;

    public propiedadAlarma(Date fecha, boolean activar) {
        this.fecha = fecha;
        this.activar = activar;
    }

      public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isActivar() {
        return activar;
    }

    public void setActivar(boolean activar) {
        this.activar = activar;
    }
  
    
}
