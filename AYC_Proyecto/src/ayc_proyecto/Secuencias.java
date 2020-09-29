
package ayc_proyecto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Secuencias {
    String[] datos;
    
    public Secuencias(String[] secu){
        this.datos=secu;
    }
    
    public void revisarSecuencias(){
        //Para ver el largo del arreglo
        int largo = this.datos.length;
        //Variable dinamica para el for
        int largover = largo;
        String[] data = this.datos;
        arbolbin arbol = new arbolbin();
        //Aqui se ejecuta la funcion para llenar el arbol
        agregandoPalabrasReservadas(arbol);
        //Para saber si se debe pasar al siguiente metodo
        int correctos = 0;
        if(largo >= 2 && largo <4){
            //Ya que hemos puesto que las secuencias basicas tendrán un largo de 2 o 3 de longitud, pues varias
            //En el caso que sean 2, significa que las 2 palabras seran comandos, por lo cual si es así no se le restara nada(Acordarse que en este caso el for empieza en 0)
            if (largo > 2) largover = largo -1; 
            //For para ver si los comandos introducidos existen en el arbol
            for(int x=0; x<largover ; x++){ 
                //Si no existen l metodo de abajo dara falso
                if(arbol.buscarNodo(data[x]) == false){
                    //Impresion de advertencia
                    System.out.println("Comando "+"'"+ data[x]+"'"+ " no reconocido!");
                    //El return es para interrumpir el for 
                    return;
                }else{
                    //Aumenta la variable correctos
                    correctos++;
                }
            }
            //Si la variable correctos llega a 2, significa que la linea de comandos esta bien, esto en caso sea basica 
            if(correctos == 2){
                //System.out.println("---La secuencia introducida es una secuencia basica---");
                if(largo == 3)
                    //Si el largo es 3 así se enviara el string
                    ejecutarSecuencias(data[0]+" "+data[1]+" "+data[2]);
                else if(largo == 2)
                    //Si es de 2
                     ejecutarSecuencias(data[0]+" "+data[1]);
            }
        }
    }
    
    //Administracion de las secuencias basicas
    public void ejecutarSecuencias(String sec){
        //Patrones aceptados
        String patron = ("(mostrar bd)|(crear bd [a-z0-9])|(eliminar bd [a-z0-9])");
        Pattern p = Pattern.compile(patron);
        Matcher matcher = p.matcher(sec);
        while(matcher.find()){
            //Patrones en variables
            String tokenTipo1 = matcher.group(1);
            String tokenTipo2 = matcher.group(2);
            String tokenTipo3 = matcher.group(3);
            //Si conincide con el patron 1
            if(tokenTipo1 != null){
                try{
                    //Creando conexion con el servidor
                    Conexion cn = new Conexion();
                     Statement st;
                     ResultSet rs;
                     st =cn.con.createStatement();
                     //Haciendo la consulta
                     rs = st.executeQuery("show databases");
                     System.out.println("****Usted cuenta con las siguientes Bases de datos: ****");
                     //Recibiendo los resultados del servidor
                     while(rs.next()){
                         //Se imprime cada resultado devuelto por el servidor
                        System.out.println("---> "+rs.getString("Database"));
                     }
                     System.out.println(" ");
                     //Cierre de conexón
                     cn.con.close();
                }catch(Exception e){
                    //Por si hat error esto lo muestra en consola
                    System.out.printf("Error : "+e);
                }
                //Si conincide con el patron 2
            }else if(tokenTipo2 != null){
               try{
                   //Creando conexion 
                     Conexion cn = new Conexion();
                     Statement st;
                     st =cn.con.createStatement();
                     //Separando la cadena para obtener el nombre
                     String nom[] = sec.split("crear bd ");
                     //Ejecutando el crear base de datos(En este caso se uso executeUpdate(), ya que se esta haciendo manipulacion de informacion,
                     //se usa executeQuery() cuando se espera información , o tambien en los insert)
                     st.executeUpdate("create database "+nom[1]);
                     //Mensaje de exito
                     System.out.printf("Base de datos "+nom[1]+" creada!\n");
                    
                }catch(Exception e){
                    //Por si hay error
                    System.out.println("Error : "+e);
                }
             //Si hay match con el patron 3
            }else if(tokenTipo3 != null){
                try{
                    //Creando conexion
                     Conexion cn = new Conexion();
                     Statement st;
                     st =cn.con.createStatement();
                     //Obteniendo el nombre
                     String nom[] = sec.split("eliminar bd ");
                     //Eliminando base de datos pasando el nombre
                     st.executeUpdate("drop database "+nom[1]);
                     //Mensaje de exito
                     System.out.printf("Base de datos "+nom[1]+" Eliminada!\n");
                    
                }catch(Exception e){
                    //Por si hay error
                    System.out.printf("Error : "+ e);
                }
            }
            else{
                System.out.printf("Secuencia erronia, por favor revise sus comandos\n");
            }
        }
    }
    
    public void agregandoPalabrasReservadas(arbolbin arbol){
        //Aqui se llena el arbol con las palabas aceptadas como comandos
        arbol.agregarNodo("crear");
        arbol.agregarNodo("bd");
        arbol.agregarNodo("usar");
        arbol.agregarNodo("tabla");
        arbol.agregarNodo("campos");
        arbol.agregarNodo("mostrar");
        arbol.agregarNodo("eliminar");
    }
}
