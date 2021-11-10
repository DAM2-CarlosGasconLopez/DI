/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jreloj;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.Timer;


public class jRelojDigital extends JLabel implements Serializable {
    
    private boolean formato24;
    private propiedadAlarma alarma;
    private SimpleDateFormat sdf24h = new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat sdf12h = new SimpleDateFormat("hh:mm:ss");
    
    private iAlarma sonarListener;

    
    public iAlarma getSonarListener() {
        return sonarListener;
    }

    public void setSonarListener(iAlarma sonarListener) {
        this.sonarListener = sonarListener;
    }

    
    
    public propiedadAlarma getAlarma() {
        return alarma;
    }

    public void setAlarma(propiedadAlarma alarma) {
        this.alarma = alarma;
    }
    
     
    
    public boolean isFormato24() {
        return formato24;
    }

    public void setFormato24(boolean formato24) {
        this.formato24 = formato24;
    }

    public SimpleDateFormat getSdf24h() {
        return sdf24h;
    }

    public void setSdf24h(SimpleDateFormat sdf24h) {
        this.sdf24h = sdf24h;
    }

    public SimpleDateFormat getSdf12h() {
        return sdf12h;
    }

    public void setSdf12h(SimpleDateFormat sdf12h) {
        this.sdf12h = sdf12h;
    }
    
          

    public jRelojDigital() {
        
        Timer timer = new Timer(1000,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Date date = new Date();
                if (formato24) {
                  setText(sdf24h.format(date));
                
                }else{
                    setText(sdf12h.format(date));
                }
                
                if(alarma!=null){
                    
                    if (alarma.isActivar() && comprobarHoras(date,alarma.getFecha())) {
                        if (sonarListener!=null) {
                            sonarListener.sonarAlarma();
                        }
                    }
                }
                
            }

            private boolean comprobarHoras(Date date, Date fecha) {
                
                Calendar date1 = Calendar.getInstance(); 
                Calendar date2 = Calendar.getInstance(); 
                date1.setTime(date);
                date2.setTime(fecha);
                
                if (date1.get(Calendar.HOUR)==date2.get(Calendar.HOUR) && date1.get(Calendar.MINUTE)==date2.get(Calendar.MINUTE) && date1.get(Calendar.SECOND)==date2.get(Calendar.SECOND)) {
                    return true;
                }else{
                    return false;
                }
            }
        }) ;
        timer.start();
    }
    
    //public comprobar

}
