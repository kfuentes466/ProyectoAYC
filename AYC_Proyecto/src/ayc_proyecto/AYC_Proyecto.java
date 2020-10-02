/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ayc_proyecto;
import java.util.Scanner;

/**
 *
 * @author ADMIN
 */
public class AYC_Proyecto {

  
    public static void main(String[] args) {
        // TODO code application logic here
        
        String secuencias;
        boolean salgo = false;
        //arbolbin palabrasReservadas = new arbolbin();
        //agregandoPalabrasReservadas(palabrasReservadas);
        String[] secun;
        //Conexion cn = new Conexion();
        Secuencias s = new Secuencias();
        Scanner leer = new Scanner(System.in);
        System.out.println("Bienvenido a su Gestor de Bases, ingrese sus comandos");
       do{
           secuencias = leer.nextLine();
           
           if("salir".equals(secuencias)) 
               salgo = true;
           else{
               secun = secuenciaSeparo(secuencias.toLowerCase());
               s.setDatos(secun);
               s.revisarSecuencias();
                /*try{
                     Statement st;
                     ResultSet rs;
                     st =cn.con.createStatement();
                     rs = st.executeQuery("show databases");
                     while(rs.next()){
                        System.out.println(rs.getString("Database"));
                     }
                     cn.con.close();
                     }catch(Exception e){
               
                     }*/
                
           
           }
           //Metodo para recorrer el arbol
           /*
           if("1".equals(secuencias)){
               palabrasReservadas.inOrden(palabrasReservadas.raiz);
           }*/
       }while(!salgo);
    }
    
    /*public static void agregandoPalabrasReservadas(arbolbin arbol){
        arbol.agregarNodo("crear");
        arbol.agregarNodo("bd");
        arbol.agregarNodo("usar");
        arbol.agregarNodo("tabla");
        arbol.agregarNodo("campos");
    }*/
    
    public static String[] secuenciaSeparo(String secu){
        String[] partes = secu.split(" ");
        return partes;
    }
}
