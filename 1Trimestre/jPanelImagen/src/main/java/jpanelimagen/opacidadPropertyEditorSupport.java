/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpanelimagen;

import java.awt.Component;
import java.beans.PropertyEditorSupport;

/**
 *
 * @author damA
 */
public class opacidadPropertyEditorSupport extends PropertyEditorSupport{

    private opacidadPanel panelOpacidad = new opacidadPanel();

    @Override
    public boolean supportsCustomEditor() {
        return true;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Component getCustomEditor() {
        return panelOpacidad; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getJavaInitializationString() {
        propiedadOpacidad opa = panelOpacidad.getSelectValue();
        return "new jpanelimagen.propiedadOpacidad("+opa.getOpacidad()+"f)";
    }

    @Override
    public Object getValue() {
        return panelOpacidad.getSelectValue(); //To change body of generated methods, choose Tools | Templates.
    }

    
   
    
    
}
