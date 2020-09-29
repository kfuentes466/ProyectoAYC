
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
        int largo = this.datos.length;
        int largover = largo;
        String[] data = this.datos;
        arbolbin arbol = new arbolbin();
        agregandoPalabrasReservadas(arbol);
        int correctos = 0;
        if(largo >= 2 && largo <4){
            if (largo > 2) largover = largo -1; 
            for(int x=0; x<largover ; x++){ 
                if(arbol.buscarNodo(data[x]) == false){
                    System.out.println("Comando "+"'"+ data[x]+"'"+ " no reconocido!");
                    return;
                }else{
                    correctos++;
                }
            }
            if(correctos == 2){
                //System.out.println("---La secuencia introducida es una secuencia basica---");
                if(largo == 3)
                    ejecutarSecuencias(data[0]+" "+data[1]+" "+data[2]);
                else if(largo == 2)
                     ejecutarSecuencias(data[0]+" "+data[1]);
            }
        }
    }
    
    public void ejecutarSecuencias(String sec){
        String patron = ("(mostrar bd)|(crear bd [a-z0-9])|(eliminar bd [a-z0-9])");
        Pattern p = Pattern.compile(patron);
        Matcher matcher = p.matcher(sec);
        while(matcher.find()){
            String tokenTipo1 = matcher.group(1);
            String tokenTipo2 = matcher.group(2);
            String tokenTipo3 = matcher.group(3);
            if(tokenTipo1 != null){
                try{
                    Conexion cn = new Conexion();
                     Statement st;
                     ResultSet rs;
                     st =cn.con.createStatement();
                     rs = st.executeQuery("show databases");
                     System.out.println("****Usted cuenta con las siguientes Bases de datos: ****");
                     while(rs.next()){
                        System.out.println("---> "+rs.getString("Database"));
                     }
                     System.out.println(" ");
                     cn.con.close();
                }catch(Exception e){
                    System.out.printf("Error : "+e);
                }
            }else if(tokenTipo2 != null){
               try{
                     Conexion cn = new Conexion();
                     Statement st;
                     st =cn.con.createStatement();
                     String nom[] = sec.split("crear bd ");
                     st.executeUpdate("create database "+nom[1]);
                     System.out.printf("Base de datos "+nom[1]+" creada!\n");
                    
                }catch(Exception e){
                    System.out.println("Error : "+e);
                }
            }else if(tokenTipo3 != null){
                try{
                    Conexion cn = new Conexion();
                     Statement st;
                     ResultSet rs;
                     st =cn.con.createStatement();
                     String nom[] = sec.split("eliminar bd ");
                     st.executeUpdate("drop database "+nom[1]);
                     System.out.printf("Base de datos "+nom[1]+" Eliminada!\n");
                    
                }catch(Exception e){
                    System.out.printf("Error : "+ e);
                }
            }
            else{
                System.out.printf("Secuencia erronia, por favor revise sus comandos\n");
            }
        }
    }
    
    public void agregandoPalabrasReservadas(arbolbin arbol){
        //Aqui se llena el arbol con las palabas 
        arbol.agregarNodo("crear");
        arbol.agregarNodo("bd");
        arbol.agregarNodo("usar");
        arbol.agregarNodo("tabla");
        arbol.agregarNodo("campos");
        arbol.agregarNodo("mostrar");
        arbol.agregarNodo("eliminar");
    }
}
