/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jreloj;

import java.awt.Component;
import java.beans.PropertyEditorSupport;



public class alarmaPropertyEditorSupport extends PropertyEditorSupport{

    private alarmaPanel panelalarma = new alarmaPanel();
    
    @Override
    public boolean supportsCustomEditor() {
        return true;
    }

    @Override
    public Component getCustomEditor() {
        return panelalarma;
    }

    @Override
    public String getJavaInitializationString() {
        propiedadAlarma alarma = panelalarma.getSelectedValue();
        return "new jreloj.propiedadAlarma(new java.util.Date(" +alarma.getFecha().getTime()+"l),"+alarma.isActivar()+")";
    }

    

    @Override
    public Object getValue() {
        return panelalarma.getSelectedValue();
    }
    
    
}
