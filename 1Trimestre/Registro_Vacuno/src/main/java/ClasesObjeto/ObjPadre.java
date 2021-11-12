


package ClasesObjeto;

import java.util.Date;

public class ObjPadre {
    private int idCrotal;
    private int idRaza;
    private String estado;
    private Date entradaExplotacion;
    private Date fechaNacimiento;

    public ObjPadre(int idCrotal, int idRaza, String estado, Date entradaExplotacion) {
        this.idCrotal = idCrotal;
        this.idRaza = idRaza;
        this.estado = estado;
        this.entradaExplotacion = entradaExplotacion;
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
