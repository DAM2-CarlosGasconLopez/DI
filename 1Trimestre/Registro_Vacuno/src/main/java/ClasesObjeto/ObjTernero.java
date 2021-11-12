

package ClasesObjeto;

import java.util.Date;


public class ObjTernero {
    
    private int idCrotal;
    private int idRaza;
    private String sexo;
    private double peso;
    private String estado;
    private Date fechaNacimiento;
    private int idPadre;
    private int idMadre;

    public ObjTernero(int idCrotal, int idRaza, String sexo, double peso, String estado, Date fechaNacimiento, int idPadre, int idMadre) {
        this.idCrotal = idCrotal;
        this.idRaza = idRaza;
        this.sexo = sexo;
        this.peso = peso;
        this.estado = estado;
        this.fechaNacimiento = fechaNacimiento;
        this.idPadre = idPadre;
        this.idMadre = idMadre;
    }

    public int getIdCrotal() {
        return idCrotal;
    }

    public void setIdCrotal(int idCrotal) {
        this.idCrotal = idCrotal;
    }

    public int getIdRaza() {
        return idRaza;
    }

    public void setIdRaza(int idRaza) {
        this.idRaza = idRaza;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(int idPadre) {
        this.idPadre = idPadre;
    }

    public int getIdMadre() {
        return idMadre;
    }

    public void setIdMadre(int idMadre) {
        this.idMadre = idMadre;
    }
    
    
    
    
}
