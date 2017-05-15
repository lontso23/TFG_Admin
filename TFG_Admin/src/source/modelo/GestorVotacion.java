package source.modelo;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import source.bd.SGBD;

public class GestorVotacion {
	
	private static GestorVotacion miVotacion = new GestorVotacion();
	private int codVotacion;
	private boolean activa = false;

	private GestorVotacion(){
		
	}
	
	public static GestorVotacion getGVotacion(){
		return miVotacion;
	}
	
	public int getCodVotacion() {
		return codVotacion;
	}



	private void setCodVotacion(int codVotacion) {
		this.codVotacion = codVotacion;
	}



	public boolean isActiva() {
		return activa;
	}



	private void setActiva(boolean activa) {
		this.activa = activa;
	}



	public void iniciar(int codV,String descrip, Date Fecha){

		java.util.Date d = new java.util.Date(); 
		java.sql.Date date2 = new java.sql.Date(d.getTime());
		String sentencia = "UPDATE Votacion SET Activa S, Date ? WHERE CodV = ?";
		PreparedStatement ps;
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setDate(1, date2);
			ps.setInt(2, codV);
			SGBD.getConexion().Update(ps);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setCodVotacion(codV);
		setActiva(true);
	}
	
	public void detener(){
		String sentencia = "UPDATE Votacion SET Activa N WHERE CodV = ?";
		
		PreparedStatement ps;
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setInt(1, getCodVotacion());
			SGBD.getConexion().Update(ps);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setActiva(false);
	}
	
	
	
	public ArrayList<Votacion> obtVotacionesNoRealizadas(){
		ArrayList<Votacion> codVotaciones = new ArrayList<Votacion>();
		Date fecha = obtFechaInicial();
		String sentencia = "SELECT Cod Descripcion FROM Votacion Where Date = ?";
		PreparedStatement ps;
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setDate(1, fecha);
			ResultSet r = SGBD.getConexion().Select(ps);
			while (r.next()){
				Votacion v = new Votacion(r.getInt("codV"), r.getString("Descripcion"));
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
		String sentencia = "SELECT Cod Descripcion FROM Votacion Where Date > ?";
		PreparedStatement ps;
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setDate(1, date2);
			ResultSet r = SGBD.getConexion().Select(ps);
			while (r.next()){
				Votacion v = new Votacion(r.getInt("Cod"), r.getString("Descripcion"));
				codVotaciones.add(v);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return codVotaciones;
	}
	
	
	public ArrayList<Alternativa> obtAlternativasAlmacenadas(){
		ArrayList<Alternativa> alter = new ArrayList<Alternativa>();
		String sentencia = "SELECT * FROM Alternativa";
		PreparedStatement ps;
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ResultSet r = SGBD.getConexion().Select(ps);
			while (r.next()){
				Alternativa a = new Alternativa(r.getString("Nombre"), r.getString("Logo"), r.getString("Descripcion"));
				alter.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return alter;
	}
	
	public ArrayList<String> obtAlternativasInscritas(int CodV){
		ArrayList<String> alter = new ArrayList<String>();
		String sentencia = "SELECT Alternativa FROM VotosGeneral WHERE CodV = ?";
		PreparedStatement ps;
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setInt(1, CodV);
			ResultSet r = SGBD.getConexion().Select(ps);
			while (r.next()){
				alter.add(r.getString("Alternativa"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return alter;
	}
	
	private Date obtFechaInicial(){
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
		String fecha ="0000-00-00";
		Date f = null;
		try {

		f = (Date) formatoDelTexto.parse(fecha);

		} catch (ParseException ex) {

		ex.printStackTrace();

		}
		return f;
	}

}
