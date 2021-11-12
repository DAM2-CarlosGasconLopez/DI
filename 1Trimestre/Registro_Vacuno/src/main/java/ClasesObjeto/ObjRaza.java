

package ClasesObjeto;


public class ObjRaza {
    
    private int id;
    private String tipo;
    private String color;

    public ObjRaza(int id, String tipo, String color) {
        this.id = id;
        this.tipo = tipo;
        this.color = color;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
      
}
