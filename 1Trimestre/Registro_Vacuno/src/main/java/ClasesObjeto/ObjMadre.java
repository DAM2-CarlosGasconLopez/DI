

package ClasesObjeto;

import java.util.Date;

public class ObjMadre {
    
    private int idCrotal;
    private int idRaza;
    private String estado;
    private int partos;
    private Date entradaExplotacion;
    private Date fechaNacimiento;

    public ObjMadre(int idCrotal, int idRaza, String estado, int partos, Date entradaExplotacion, Date fechaNacimiento) {
        this.idCrotal = idCrotal;
        this.idRaza = idRaza;
        this.estado = estado;
        this.partos = partos;
        this.entradaExplotacion = entradaExplotacion;
        this.fechaNacimiento = fechaNacimiento;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getPartos() {
        return partos;
    }

    public void setPartos(int partos) {
        this.partos = partos;
    }

    public Date getEntradaExplotacion() {
        return entradaExplotacion;
    }

    public void setEntradaExplotacion(Date entradaExplotacion) {
        this.entradaExplotacion = entradaExplotacion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    
    
}
