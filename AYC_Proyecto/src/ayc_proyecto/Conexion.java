
package ayc_proyecto;

import java.sql.Connection;
import java.sql.DriverManager;

class Conexion {
    Connection con;
    
    
    public Conexion(String bdUso){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+bdUso,"root","");
        }catch(Exception e) {
            System.err.println("Eror: "+e);
        }
    }
    
}
