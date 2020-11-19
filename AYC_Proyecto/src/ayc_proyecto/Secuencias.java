
package ayc_proyecto;

import static java.lang.Integer.MAX_VALUE;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Scanner;

public class Secuencias {
    String[] datos;
    String usando; 
    public Secuencias(){
        this.datos = null;
        this.usando = "";
    }
    
    public void setUsando(String us){
        this.usando = us;
    }
    
    public String getUsando(){
        return this.usando;
    }
    
    public void setDatos( String[] secu){
        this.datos = secu;
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
            //En el caso que sean 2, significa que las 2 palabras seran comandos, por lo cual si es así no se le 
            //restara nada(Acordarse que en este caso el for empieza en 0)
            if (largo > 2) largover = largo -1; 
            //For para ver si los comandos introducidos existen en el arbol
            for(int x=0; x<largover ; x++){ 
                //Si no existen l metodo de abajo dara falso
                if(arbol.buscarNodo(data[x]) == false){
                    //Impresion de advertencia
                    System.out.println("\u001B[31m"+"Comando "+"'"+ data[x]+"'"+ 
                            " no reconocido!"+"\u001B[0m");
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
    
    public boolean buscarTipoDato(String dato){
        
        String[] tipos = {"int","text","varchar","char"};
        return Arrays.asList(tipos).contains(dato);
    }
    
    public boolean verTabla(String tabla){
        try{
         Conexion cn = new Conexion(getUsando());
         ResultSet rs;
         int i=0;
         DatabaseMetaData meta = cn.con.getMetaData();
         String[] tablas = new String[50];
         rs = meta.getTables(null, null, null, new String[] {"TABLE"});
         
         while(rs.next()){
             tablas[i] = rs.getString("TABLE_NAME");
             i++;
         }
         return Arrays.asList(tablas).contains(tabla);
        }catch(SQLException e){
         System.out.println("Error : "+e);
         return false;
        }
    }
    
    public int cuentoColumnas(String tabla){
        int numero = 0;
        try{
            Conexion cn = new Conexion(getUsando());
            DatabaseMetaData meta = cn.con.getMetaData();
            ResultSet rs = meta.getColumns(null, null, tabla, null);
            while(rs.next()){
                numero++;
            }
            return numero;
        }catch(Exception e){
            System.out.println("Error : "+e);
            return numero;
        }
    }
    
     public boolean columnaexiste(String tabla, String columna){
        try{
            int i =0;
            String[] nombreColumna = new String[50];
            Conexion cn = new Conexion(getUsando());
            DatabaseMetaData meta = cn.con.getMetaData();
            ResultSet rs = meta.getColumns(null, null, tabla, null);
            while(rs.next()){
                nombreColumna[i] = rs.getString(4);
                i++;
            }
            return Arrays.asList(nombreColumna).contains(columna);
        }catch(Exception e){
            System.out.println("Error "+e);
            return false;
        }
     }
    
    //Administracion de las secuencias basicas
    public void ejecutarSecuencias(String sec){
        //Patrones aceptados
        String patron = ("(mostrar bd)|(crear bd [a-z0-9])|(eliminar bd [a-z0-9])|(usar bd [a-z0-9])|(mostrar tablas)|(crear tabla [a-z0-9])|(mostrar estructura [a-z0-9])|(mostrar datos [a-z0-9])|(agregar datos [a-z0-9])|(eliminar datos [a-z0-9])");
        Pattern p = Pattern.compile(patron);
        Matcher matcher = p.matcher(sec);
        String rojo = "\u001B[31m";
        String azul = "\u001B[34m";
        String terminar = "\u001B[0m";
    
        while(matcher.find()){
            //Patrones en variables
            String tokenTipo1 = matcher.group(1);
            String tokenTipo2 = matcher.group(2);
            String tokenTipo3 = matcher.group(3);
            String tokenTipo4 = matcher.group(4);
            String tokenTipo5 = matcher.group(5);
            String tokenTipo6 = matcher.group(6);
            String tokenTipo7 = matcher.group(7);
            String tokenTipo8 = matcher.group(8);
            String tokenTipo9 = matcher.group(9);
            String tokenTipo10 = matcher.group(10);
            //Si conincide con el patron 1
            if(tokenTipo1 != null){
                try{
                    //Creando conexion con el servidor
                    Conexion cn = new Conexion(getUsando());
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
                     Conexion cn = new Conexion(getUsando());
                     Statement st;
                     st =cn.con.createStatement();
                     //Separando la cadena para obtener el nombre
                     String nom[] = sec.split("crear bd ");
                     //Ejecutando el crear base de datos(En este caso se uso executeUpdate(), ya que se esta haciendo manipulacion de informacion,
                     //se usa executeQuery() cuando se espera información , o tambien en los insert)
                     st.executeUpdate("create database "+nom[1]);
                     //Mensaje de exito
                     System.out.printf(azul+"Base de datos "+nom[1]+" creada!\n"+terminar);
                    
                }catch(Exception e){
                    //Por si hay error
                    System.out.println("Error : "+e);
                }
             //Si hay match con el patron 3
            }else if(tokenTipo3 != null){
                try{
                    //Creando conexion
                     Conexion cn = new Conexion(getUsando());
                     Statement st;
                     st =cn.con.createStatement();
                     //Obteniendo el nombre
                     String nom[] = sec.split("eliminar bd ");
                     //Eliminando base de datos pasando el nombre
                     st.executeUpdate("drop database "+nom[1]);
                     //Mensaje de exito
                     System.out.printf(azul+"Base de datos "+nom[1]+" Eliminada!\n"+terminar);
                    
                }catch(Exception e){
                    //Por si hay error
                    System.out.printf("Error : "+ e);
                }
            }else if(tokenTipo4 != null){
                try{
                    Conexion cn = new Conexion("");
                    Statement st;
                    st =cn.con.createStatement();
                    //Obteniendo nombre de la bd
                    String nombre[] = sec.split("usar bd ");
                    //System.out.println(nombre[1]);
                    setUsando(nombre[1]);
                    st.executeQuery("USE "+nombre[1]+";");
                    System.out.printf(azul+"Usando base de datos "+getUsando()+"\n"+terminar);
                }catch(Exception e){
                    System.out.printf("Error : "+e+"\n");
                }
            }else if(tokenTipo5 != null){
                if(this.usando == null || this.usando==""){
                    System.out.printf(rojo+"No se ha ejecutado el comando 'usar bd bd_nombre', debes uar una bd para mostrar tablas!\n"+terminar);
                }else{
                    try{
                        Conexion cn = new Conexion(getUsando());
                        Statement st = cn.con.createStatement();
                        ResultSet rs;
                        DatabaseMetaData meta = cn.con.getMetaData();
                        //st =cn.con.createStatement();
                        //rs = st.executeQuery("show tables from "+this.usando);
                        rs = meta.getTables(null, null, null, new String[] {"TABLE"});
                        System.out.println("**** La base de datos "+ getUsando()+" tiene las siguientes tablas : ****");
                        while(rs.next()){
                         //Se imprime cada resultado devuelto por el servidor
                        System.out.println("---> "+rs.getString("TABLE_NAME"));
                        }
                        //System.out.println(getUsando()+ "esta seleccionada");
                    }catch(Exception e){
                        System.out.println("Error : "+e);
                    }
                }
            }else if( tokenTipo6 != null ){
                if(this.usando == null || this.usando =="" ){
                    System.out.printf(rojo+"No se ha ejecutado el comando 'usar bd bd_nombre', debes uar una bd para crear tablas!\n"+terminar);
                }else{
                        String tabla_nombre[] = sec.split("crear tabla ");
                        String secuencia="", s1 , s2;
                        int columnas, x=0;
        
                        System.out.print("Introduce el numero de columnas que tendra la tabla '"+tabla_nombre[1]+"' : ");
                        Scanner entrada = new Scanner(System.in);
                        columnas  = Integer.parseInt(entrada.nextLine());
                        secuencia += "CREATE TABLE IF NOT EXISTS "+tabla_nombre[1]+"(";
                        while(x<columnas){
                            System.out.print("Ingresar el tipo de dato la columna #"+ (x+1)+" :");
                            s1=entrada.nextLine();
                            if(!this.buscarTipoDato(s1)){
                                System.out.println(rojo+"Error! El tipo de dato no existe!, vuelve a intentarlo."+terminar);
                            }else{
                                System.out.print("Ingresa el nombre de la tabla #"+(x+1)+" :");
                                s2 = entrada.nextLine().replaceAll("\\s","");
                                secuencia += s2+" "+s1;
                                if("varchar".equals(s1))
                                    secuencia +="(50)";
                                if(x+1==columnas)
                                    secuencia += " NOT NULL ";
                                else
                                    secuencia += " NOT NULL, ";
                                x++;
                            }
                        }
                        secuencia +=")";
                        System.out.println(secuencia);
                        try{
                            Conexion cn = new Conexion(getUsando());
                            Statement st;
                            st =cn.con.createStatement();
                            st.executeUpdate(secuencia);
                            System.out.println(azul+"Tabla "+tabla_nombre[1]+" creada con exito!"+terminar);
                        }catch(Exception e){
                            System.out.println("Error : "+e);
                        }
                    }
            }else if(tokenTipo7 != null){
                String tabla[] = sec.split("mostrar estructura ");
                try{
                    Conexion cn = new Conexion(getUsando());
                     DatabaseMetaData meta = cn.con.getMetaData();
                    ResultSet rs = meta.getColumns(null, null, tabla[1], null);
                    System.out.println("La estructura de la tabla '"+tabla[1]+"' es :");
                    while(rs.next()){
                         String nombreColumna = rs.getString(4);
                         String tipoColumna = rs.getString(6);
                         System.out.println("---> COLUMNA, nombre=" + nombreColumna+ " tipo = " + tipoColumna);
                    }
                    System.out.println();
                }catch(Exception e){
                    System.out.println("Error : "+e);
                }
            }else if(tokenTipo8 != null){
                if(this.usando == null || this.usando == ""){
                    System.out.printf(rojo+"No se ha ejecutado el comando 'usar bd bd_nombre', debes uar una bd para acceder a los datos!\n"+terminar);
                }else{
                    String tabla[] = sec.split("mostrar datos ");
                    boolean existo = this.verTabla(tabla[1]);
                    if(!existo){
                        System.out.println(rojo+"La tabla '"+tabla[1]+"' no existe en la base de datos '"+this.usando+"'"+terminar);
                    }else{
                        String muestro="";
                        Scanner entrada = new Scanner(System.in);
                        int op;
                        System.out.println("Opciones para la muestra de datos : ");
                        System.out.println("---> 1)Mostrar datos clasicos");
                        System.out.println("---> 2)Mostrar datos de columnas seleccionadas");
                        System.out.print("Seleccion a escoger : ");
                        op = Integer.parseInt(entrada.nextLine());
                        while(op > 2){
                            System.out.println(rojo+"Opcion no valida!, por favor inserta una opcion valida!");
                            op = Integer.parseInt(entrada.nextLine());
                        }
                        try{
                            if(op == 1){ 
                                 Conexion cn = new Conexion(getUsando());
                                 Statement st;
                                 ResultSet rs, rs2;
                                 DatabaseMetaData meta = cn.con.getMetaData();
                                 rs2 = meta.getColumns(null, null, tabla[1], null);
                                 System.out.println(azul+"Mostrando datos de tabla '"+tabla[1]+"' "+terminar);
                                 while(rs2.next()){
                                     System.out.print("|\t"+rs2.getString(4)+"\t |");
                                 }
                                 System.out.println();
                                 st =cn.con.createStatement();
                                 rs = st.executeQuery("SELECT * FROM "+tabla[1]);
                                 while(rs.next()){
                                     for(int x=0; x< this.cuentoColumnas(tabla[1]); x++){
                                         muestro += "|\t"+rs.getString(x+1)+" \t|";
                                     }
                                     
                                     System.out.println(muestro);
                                     muestro = "";
                                 }
                            }else {
                                int numColumn = this.cuentoColumnas(tabla[1]);
                                int seleccion = 0, i=0;
                                String columnasSeleccionadas = "", c="";
                                String[] columnasNombres = new String[50];
                                System.out.println("La tabla '"+tabla[1]+"' tiene un total de "+numColumn+" columnas");
                                System.out.print("Ingrese numero de columnas que seleccionara : ");
                                seleccion = Integer.parseInt(entrada.nextLine());
                                while(seleccion > numColumn){
                                    System.out.print(rojo+"No puedes seleccionar más columnas que las disponibles. Introduce un numeró valido : "+terminar);
                                    seleccion = Integer.parseInt(entrada.nextLine());
                                }
                                try{
                                    while(i < seleccion){
                                        System.out.print("Ingresa el nombre de la columna #"+(i+1)+" a seleccionar : ");
                                        c= entrada.nextLine();
                                        if(this.columnaexiste(tabla[1], c)){
                                            if((i+1)== seleccion){
                                                columnasSeleccionadas += c;
                                            }else{
                                                columnasSeleccionadas += c+", ";
                                            }
                                            columnasNombres[i]=c;
                                            i++;
                                        }else{
                                            System.out.println(rojo+"Error, columna no existente en la tabla '"+tabla[1]+"'"+terminar);
                                        }
                                    }
                                    System.out.println("Mostrando datos de las columnas "+columnasSeleccionadas+" de la tabla '"+tabla[1]+"'\n");
                                    for(int b=0; b<seleccion; b++){
                                        System.out.print("|\t"+columnasNombres[b]+" \t|");
                                    }
                                   System.out.println();
                                    Conexion cn = new Conexion(getUsando());
                                    Statement st;
                                    ResultSet rs;
                                    st =cn.con.createStatement();
                                    rs = st.executeQuery("SELECT "+columnasSeleccionadas+" FROM "+tabla[1]);
                                    
                                    while(rs.next()){
                                        for(int x=0; x< seleccion; x++){
                                            muestro += "|\t"+rs.getString(x+1)+" \t|";
                                        }
                                     System.out.println(muestro);
                                     muestro = "";
                                 }
                                        
                                }catch(Exception e){
                                    System.out.println("Error : "+e);
                                }
                            }
                        }catch(SQLException e){
                            System.out.println("Eror : "+e);
                        }
                    }
                }
            }else if(tokenTipo9 != null){
                if(this.usando == null || this.usando == ""){
                    System.out.printf(rojo+"No se ha ejecutado el comando 'usar bd bd_nombre', debes uar una bd para acceder a los datos!\n"+terminar);
                }else{
                    String tabla[] = sec.split("agregar datos ");
                    boolean existo = this.verTabla(tabla[1]);
                    if(!existo){
                        System.out.println(rojo+"La tabla '"+tabla[1]+"' no existe en la base de datos '"+this.usando+"'"+terminar);
                    }else{
                        try{
                            int numColumn = this.cuentoColumnas(tabla[1]), i=0, a=0;
                            String[] tablas = new String[50];
                            String secuencias="", dato="";
                            Scanner entrada = new Scanner(System.in);
                            Conexion cn = new Conexion(getUsando());
                            Statement st = cn.con.createStatement();
                            ResultSet rs2;
                            DatabaseMetaData meta = cn.con.getMetaData();
                            rs2 = meta.getColumns(null, null, tabla[1], null);
                            while(rs2.next()){
                                tablas[i] = rs2.getString(4);
                                i++;
                            }
                            secuencias += "INSERT INTO "+tabla[1]+" VALUES(";
                            while(a < numColumn){
                                System.out.print("Dato a ingresar a columna "+tablas[a]+" : ");
                                dato = entrada.nextLine();
                                if((a+1)==numColumn){
                                    secuencias +="'" +dato+"');"; 
                                }else{
                                    secuencias+= "'"+dato+"', ";
                                }
                                a++;
                            }
                            
                            st.executeUpdate(secuencias);
                            System.out.println(azul+"Datos ingresados en tabla '"+tabla[1]+"' exitosamente"+terminar);
                            
                        }catch(Exception e){
                            System.out.println("Error : "+e);
                        }
                        
                    }
                }
            }else if(tokenTipo10 != null){
                if(this.usando == null || this.usando == ""){
                    System.out.printf(rojo+"No se ha ejecutado el comando 'usar bd bd_nombre', debes uar una bd para la manipulación de datos!\n"+terminar);
                }else{
                    String tabla[] = sec.split("eliminar datos ");
                    boolean existo = this.verTabla(tabla[1]);
                    if(!existo){
                        System.out.println(rojo+"La tabla '"+tabla[1]+"' no existe en la base de datos '"+this.usando+"'"+terminar);
                    }else{
                        try{
                            Scanner entrada = new Scanner(System.in);
                            String columna = "", secuencia = "", info="";
                            Conexion cn = new Conexion(getUsando());
                            Statement st = cn.con.createStatement();
                            System.out.print("Escribe el nombre de columna según la cual quieras eliminar un registro : ");
                            columna = entrada.nextLine();
                            boolean existeColumna = this.columnaexiste(tabla[1], columna);
                            while(!existeColumna){
                                System.out.print("la columna '"+columna+"' no existe! Por favor vuelve a intentarlo : ");
                                columna = entrada.nextLine();
                                existeColumna = this.columnaexiste(tabla[1], columna);
                            }
                            secuencia += "DELETE FROM "+tabla[1]+" WHERE "+columna+"= '";
                            System.out.print("Ingrese el valor que debe tener dicha columna : ");
                            info = entrada.nextLine();
                            secuencia += info+"'";
                            st.executeUpdate(secuencia);
                            System.out.println(azul+"Dato eliminado de la base de datos!"+terminar);
                        }catch(Exception e){
                            System.out.println("Error : "+e);
                        }
                    }
                }
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
        arbol.agregarNodo("mostrar");
        arbol.agregarNodo("tablas");
        arbol.agregarNodo("estructura");
        arbol.agregarNodo("datos");
        arbol.agregarNodo("agregar");
        arbol.agregarNodo("eliminar");
    }
}
