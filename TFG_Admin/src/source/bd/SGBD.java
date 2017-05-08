package source.bd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;


public class SGBD {
	
	/**********************************************/
	/*  	CONEXION CON LA BASE DE DATOS		  */
	/**********************************************/
	private static SGBD mConexion = new SGBD();
	
	private static Connection conexion; 
	private static Statement statement;
	private String SentenciaSQL;
	private ResultSet Resultado;
	private static String  usuario = "Xmnieto012"; 
	private static String password = "HdrNjBIwm";
	private static String server = "jdbc:mysql://galan.ehu.eus:3306/Xmnieto012_Votaciones";
	
	private SGBD(){
		
	}
	
	public static SGBD getConexion(){
		return mConexion;
	}
	
	/*******************************************************/
	/* METODO PARA CONECTARSE AL DRIVER Y PODER USAR MYSQL */
	/*******************************************************/
	
	public static void conectar(){
		try{
			Class.forName("org.gjt.mm.mysql.Driver");
			//Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e) {
			//e.printStackTrace();
			e.getMessage();
		}
		// Open connection
		try{
			conexion= DriverManager.getConnection(server,usuario,password);
			conexion.setAutoCommit(true);
			statement=conexion.createStatement();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/***********************************************************/
	/* METODO PARA ESTABLECER LA CONEXION CON LA BASE DE DATOS */
	/***********************************************************/

	public static Statement conexion() {
        Statement st = null;
        try {
            st = conexion.createStatement();
        } catch (SQLException e) {
            System.out.println("Error: Conexion incorrecta.");
            e.printStackTrace();
        }
        return st;
    }
 
	/*************************************************************************
     * M�todo para realizar consultas del tipo: SELECT * FROM tabla WHERE..."*
     *************************************************************************/
	
	public ResultSet Select(String SentenciaSQL){
		this.SentenciaSQL = SentenciaSQL;
		
		try{
			this.Resultado = statement.executeQuery(this.SentenciaSQL);
		}catch(SQLException e){
			e.getMessage();			
		}
		return Resultado;
	}	
     
	public static ResultSet consultaDatos(Statement st, String cadena) {
        ResultSet rs = null;
        try {
            rs = st.executeQuery(cadena);
        } catch (SQLException e) {
            System.out.println("Error con: " + cadena);
            System.out.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }
        return rs;
    }
	
	/*****************************************************************************************************************
     * M�todo para realizar consultas de actualizaci�n, creaci�n o eliminaci�n (DROP,INSERT INTO, ALTER...,NO SELECT * 
     *****************************************************************************************************************/
    
    
    public void Update(String SentenciaSQL, String[] buffer){
		this.SentenciaSQL = SentenciaSQL;
		try{
			
			PreparedStatement ps = conexion.prepareStatement(SentenciaSQL);
			for(int i=0; i<buffer.length; i++){
				ps.setString(i+1, buffer[i]);
			}
				ps.executeUpdate();
				
				System.out.println(ps.toString());
		}catch(SQLException e){
			e.getMessage();			
		}
	}
    
    /*********************************
     * M�todo para cerrar la consula *
     *********************************/
    static void cerrarSelect(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                System.out.print("Error: No es posible cerrar la consulta.");
            }
        }
    }
    
    /***********************************
     * M�todo para cerrar la conexi�n. *
     ***********************************/
    public static void cerrar(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (Exception e) {
                System.out.print("Error: No es posible cerrar la conexi�n.");
            }
        }
    }
    

}
