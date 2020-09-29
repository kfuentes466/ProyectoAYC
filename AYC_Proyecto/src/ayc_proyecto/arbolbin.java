
package ayc_proyecto;

public class arbolbin {
   
    nodoarbol raiz; //Nodo raiz del arbol
    
    public arbolbin(){
        raiz = null; //Inicializa el nodo creado como nulo
    }
    
    //Para ver si el arbol esta vacio
    public boolean arbVacio(){
        return raiz == null;
    }
    
    //Insertar un nodo en el arbol
    public void agregarNodo(String nom){
        nodoarbol nuevo = new nodoarbol(nom);
        if(raiz == null){
            raiz = nuevo;//Si el arbol es nulo, este nodo se convierte en la raiz
        }else{
            //Si no se empieza a recorrer el arbol
            nodoarbol auxiliar = raiz;
            nodoarbol padre;
            while(true){
                padre = auxiliar;
                //Compara el parametro dado con el contenido de ese nodo
                //Si lo introducid es menor esto se coloca como nodohijo izquierdo
                if(nom.compareTo(auxiliar.nombre) <= 0){
                    auxiliar = auxiliar.hizquierdo;
                    //Y si es nulo significa que hasta alli llego el arbol, por lo cual
                    //No lo tiene que seguir recorriendo
                    if(auxiliar == null){
                        padre.hizquierdo = nuevo;
                        return;//Esto es solo para detener el bucle
                    }
                }else{
                    //si no se cumple el if a auxiliar se le agegara el hijo derecho
                    auxiliar = auxiliar.hderecho;
                    if(auxiliar == null){
                        //Cuando llega al final del arbol
                        padre.hderecho = nuevo;
                        return;//Para salir de funcion
                    }
                }
            }
        }
    }
    
    public void inOrden(nodoarbol r){
        if(r!=null){
            inOrden(r.hizquierdo);
            System.out.print(r.nombre + " , ");
            inOrden(r.hderecho);
        }
    }
    
    //Para buscar las palabras en el arbol
    public boolean buscarNodo(String palabra){
        nodoarbol auxiliar = raiz;
        while(!auxiliar.nombre.equals(palabra)){
            if(palabra.compareTo(auxiliar.nombre) < 0){
                auxiliar = auxiliar.hizquierdo;
            }else{
                auxiliar = auxiliar.hderecho;
            }
            if(auxiliar == null){
                return false;
            }
        }
        
        return true;
    }
}
