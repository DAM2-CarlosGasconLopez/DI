/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpanelimagen;

import java.io.Serializable;

/**
 *
 * @author damA
 */
public class propiedadOpacidad implements Serializable{
    private Float opacidad = 1.0f;

    public propiedadOpacidad(Float opacidad) {
        this.opacidad = opacidad;
    }

    public Float getOpacidad() {
        return opacidad;
    }

    public void setOpacidad(Float opacidad) {
        this.opacidad = opacidad;
    }
    
}
