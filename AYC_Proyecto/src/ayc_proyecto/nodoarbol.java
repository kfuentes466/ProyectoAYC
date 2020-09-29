package ayc_proyecto;

public class nodoarbol {
    String nombre;
    nodoarbol hizquierdo, hderecho;
    
    //Este es el constructor
    public nodoarbol(String nom){
        this.nombre = nom;
        this.hizquierdo = null;
        this.hderecho = null;
    }
    
    public String toString(){
        return "\n El nombre del nodo es: " + nombre;
    }
    
    
}
