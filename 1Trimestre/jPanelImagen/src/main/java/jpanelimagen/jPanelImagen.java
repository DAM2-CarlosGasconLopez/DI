/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpanelimagen;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class jPanelImagen extends JPanel implements Serializable{
    
    private File archivoImagen;
    private propiedadOpacidad opacidad = new propiedadOpacidad(1.0f);
    private boolean presioado = false;
    
    private Point puntoPresion;
    private iArrastreListener arrArrastreListener;
    
    
    

    public jPanelImagen() {
        this.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if (presioado) {
                    Point posicionActual = e.getPoint();
                    if (Math.abs(puntoPresion.x - posicionActual.x) > 50) {
                        if (arrArrastreListener != null) {
                            arrArrastreListener.arrastre();
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                presioado=true;
                puntoPresion=e.getPoint();
                
            }
            
            
            
        });
        
        
        
    }
    public void addArrastreListener(iArrastreListener arrastreListener){
        this.arrArrastreListener = arrastreListener;
    }
    
    public void removeArrastreListener(iArrastreListener arrastreListener){
        this.arrArrastreListener = arrastreListener;
    }
    

    public File getArchivoImagen() {
        return archivoImagen;
    }

    public void setArchivoImagen(File archivoImagen) {
        this.archivoImagen = archivoImagen;
        repaint();
    }

    public propiedadOpacidad getOpacidad() {
        return opacidad;
    }

    public void setOpacidad(propiedadOpacidad opacidad) {
        this.opacidad = opacidad;
    }

    
     

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        if (archivoImagen!=null && archivoImagen.exists() && opacidad.getOpacidad()!=null) {
            ImageIcon imageIcon = new ImageIcon(archivoImagen.getAbsolutePath());
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,opacidad.getOpacidad()));
              
            g.drawImage(imageIcon.getImage(), 0, 0,this);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1));
            
        }
        
        
    }
    
    
    
    
}
