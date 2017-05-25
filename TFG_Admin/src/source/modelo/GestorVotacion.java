package source.modelo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;

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
		String sentencia = "UPDATE Votacion SET Activa = S, Date ? WHERE CodV = ?";
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
		String sentencia = "UPDATE Votacion SET Activa = N WHERE CodV = ?";
		
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
		String sentencia = "SELECT Cod, Descripcion FROM Votacion Where Date = '2099-01-01'";
		PreparedStatement ps;
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ResultSet r = SGBD.getConexion().Select(ps);
			while (r.next()){
				System.out.println(r.getInt("Cod")+" "+r.getString("Descripcion"));
				Votacion v = new Votacion(r.getInt("Cod"), r.getString("Descripcion"));
				codVotaciones.add(v);
			}
			SGBD.getConexion().cerrarSelect(r);
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
		String sentencia = "SELECT Cod, Descripcion FROM Votacion Where Date < ?";
		PreparedStatement ps;
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setDate(1, date2);
			ResultSet r = SGBD.getConexion().Select(ps);
			while (r.next()){
				Votacion v = new Votacion(r.getInt("Cod"), r.getString("Descripcion"));
				codVotaciones.add(v);
			}
			SGBD.getConexion().cerrarSelect(r);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return codVotaciones;
	}
	
	
	public ArrayList<String> obtNombreAlternativasAlmacenadas(){
		ArrayList<String> alter = new ArrayList<String>();
		String sentencia = "SELECT * FROM Alternativa";
		PreparedStatement ps;
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ResultSet r = SGBD.getConexion().Select(ps);
			while (r.next()){
				alter.add(r.getString("Nombre"));
			}
			SGBD.getConexion().cerrarSelect(r);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return alter;
	}
	
	public ArrayList<String> obtNombreAlternativasInscritas(int CodV){
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
			SGBD.getConexion().cerrarSelect(r);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return alter;
	}
	
	
	public ArrayList<Alternativa> obtAlternativasInscritas(int CodV){
		ArrayList<Alternativa> alter = new ArrayList<Alternativa>();
		ArrayList<String> nomAlter = obtNombreAlternativasInscritas(CodV);
		String sentencia = "SELECT * FROM Alternativa WHERE Nombre = ?";
		PreparedStatement ps;
		for (int i = 0; i < nomAlter.size(); i++) {
			String act = nomAlter.get(i);
			try {
				ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
				ps.setString(1, act);
				ResultSet r = SGBD.getConexion().Select(ps);
				while (r.next()) {
					Blob blob = r.getBlob("Logo");
					byte[] data = blob.getBytes(1, (int) blob.length());
					BufferedImage img = null;
					try {
						img = ImageIO.read(new ByteArrayInputStream(data));
						Alternativa a = new Alternativa(r.getString("Nombre"), img, r.getString("Descripcion"));
						alter.add(a);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				SGBD.getConexion().cerrarSelect(r);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return alter;
	}
	

	/*private Date obtFechaInicial(){
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		return sqlDate;
	}*/
	
	/*private java.util.Date obtFechaInicial(){
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
		String fecha ="2099-01-01";
		java.util.Date f = null;
		try {

		f = (java.util.Date) formatoDelTexto.parse(fecha);

		} catch (ParseException ex) {

		ex.printStackTrace();

		}
		return f;
	}
*/
}
