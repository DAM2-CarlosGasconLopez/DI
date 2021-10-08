/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author damA
 */
public class Clientes {

      
    private String profesion;
    private String edad;
    private Integer nhermanos;
    private String sexo;
    private String deporte;
    private Integer compras;
    private Integer television;
    private Integer cine;
    
    public Clientes(String profesion, String edad, Integer nhermanos, String sexo, String deporte,Integer compras, Integer television, Integer cine) {
        this.profesion = profesion;
        this.edad = edad;       
        this.nhermanos = nhermanos;
        this.sexo = sexo;
        this.deporte = deporte;
        this.compras = compras;
        this.television = television;
        this.cine = cine;
    }

   

    public String getProfesion() {
        return profesion;
    }

    public String getEdad() {
        return edad;
    }

    public Integer getNhermanos() {
        return nhermanos;
    }
    
    public String getSexo() {
        return sexo;
    }
    
    public String getDeporte() {
        return deporte;
    }

    public Integer getCompras() {
        return compras;
    }

    public Integer getTelevision() {
        return television;
    }

    public Integer getCine() {
        return cine;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public void setNhermanos(Integer nhermanos) {
        this.nhermanos = nhermanos;
    }
    
    public String setSexo() {
        return sexo;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public void setCompras(Integer compras) {
        this.compras = compras;
    }

    public void setTelevision(Integer television) {
        this.television = television;
    }

    public void setCine(Integer cine) {
        this.cine = cine;
    }
    
    public String[] toArrayString(){        
        String[] s = new String[8];
        s[0]= profesion;
        s[1]= edad;
        s[2]= nhermanos.toString();
        s[3]= sexo;
        s[4]= deporte;
        s[5] = compras.toString();
        s[6] = television.toString();
        s[7] = cine.toString();
        return s;
    }
    
}
