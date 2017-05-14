package source.modelo;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import source.bd.SGBD;

public class GestorVotacion {
	
	private static GestorVotacion miVotacion = new GestorVotacion();
	private String codVotacion;
	private boolean activa = false;

	private GestorVotacion(){
		
	}
	
	public static GestorVotacion getGVotacion(){
		return miVotacion;
	}
	
	public String getCodVotacion() {
		return codVotacion;
	}



	private void setCodVotacion(String codVotacion) {
		this.codVotacion = codVotacion;
	}



	public boolean isActiva() {
		return activa;
	}



	private void setActiva(boolean activa) {
		this.activa = activa;
	}



	public void iniciar(String codV,String descrip, Date Fecha){

		java.util.Date d = new java.util.Date(); 
		java.sql.Date date2 = new java.sql.Date(d.getTime());
		String sentencia = "UPDATE Votacion SET Activa S, Date ? WHERE CodV = ?";
		SGBD.getConexion().UpdateIniciarVotacion(sentencia, codV, date2);
		setCodVotacion(codV);
		setActiva(true);
	}
	
	public void detener(){
		String sentencia = "UPDATE Votacion SET Activa N WHERE CodV = ?";
		String[] buffer = new String[1];
		buffer[0]=getCodVotacion();
		SGBD.getConexion().Update(sentencia, buffer);
		setActiva(false);
	}
	
	
	
	public ArrayList<Votacion> obtVotacionesNoRealizadas(){
		ArrayList<Votacion> codVotaciones = new ArrayList<Votacion>();
		String sentencia = "SELECT Cod Descripcion FROM Votacion Where Date = '"+""+"'";
		ResultSet r = SGBD.getConexion().Select(sentencia);
		try {
			while (r.next()){
				Votacion v = new Votacion(r.getString("codV"), r.getString("Descripcion"));
				codVotaciones.add(v);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return codVotaciones;
	}
	
	public ArrayList<Votacion> obtVotacionesFinalizadas(){
		java.util.Date d = new java.util.Date(); 
		java.sql.Date date2 = new java.sql.Date(d.getTime());
		ArrayList<Votacion> codVotaciones = new ArrayList<Votacion>();
		String sentencia = "SELECT Cod Descripcion FROM Votacion Where Date > '"+date2+"'";
		ResultSet r = SGBD.getConexion().Select(sentencia);
		try {
			while (r.next()){
				Votacion v = new Votacion(r.getString("codV"), r.getString("Descripcion"));
				codVotaciones.add(v);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return codVotaciones;
	}
	
	
	public ArrayList<String> obtAlternativasAlmacenadas(){
		return null;
	}
	
	public ArrayList<String> obtAlternativasInscritas(String CodV){
		return null;
	}

}
