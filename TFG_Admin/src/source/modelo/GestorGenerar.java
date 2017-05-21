package source.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import source.bd.SGBD;

public class GestorGenerar {
	
	private static GestorGenerar miGenerar = new GestorGenerar();
	private int codVotacion;
	private GestorGenerar(){
		
	}
	
	public static GestorGenerar getGenerar(){
		return miGenerar;
	}
	
	public int getCodVotacion() {
		return codVotacion;
	}



	private void setCodVotacion(int codVotacion) {
		this.codVotacion = codVotacion;
	}
	
	private void crearVotacion(String descrip){
		int codV = crearCodigoVotacion();
		String sentencia = "INSERT INTO Votacion(Cod,Descripcion) VALUES (?,?)";
		PreparedStatement ps;
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setInt(1, codV);
			ps.setString(3,descrip);
			SGBD.getConexion().Update(ps);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	private int crearCodigoVotacion(){
	String sentencia = "SELECT MAX(Cod) FROM Votacion";
		PreparedStatement ps;
		int nuevoCod=0;
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ResultSet r = SGBD.getConexion().Select(ps);
			while (r.next()){
				 nuevoCod=r.getInt("codV");				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	 nuevoCod = nuevoCod +1;
	setCodVotacion(nuevoCod);
	return nuevoCod;
	}
	
	
	public void generarVotacion(String Comunidad, ArrayList<String> nomAlter, String descripcionVotacion){
		crearVotacion(descripcionVotacion);
		addVotantes(Comunidad);
		ArrayList<String> coles = obtColegios(Comunidad);
		addAlternativas(nomAlter,coles);
	}
	
	public void addVotantesNuevos(ArrayList<String> l){
		//TODO Clase Votante o hacer con split
	}
	
	private void addVotantes(String Comunidad){
		PreparedStatement ps;
		String sentencia;
		
		
	}
	

	public void addAlternativaNueva(String nombre, String descrip, String rutafoto){
		String sentencia = "INSERT INTO Alternativa(Nombre, Logo, Descripcion) VALUES (?,?,?)";
		File f = new File(rutafoto);
		FileInputStream fis;
		try {
			try {
				fis = new FileInputStream(f);
				PreparedStatement ps;
				ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
				ps.setString(1, nombre);
				ps.setBlob(2, fis, f.length());
				ps.setString(3,descrip);
				SGBD.getConexion().Update(ps);
			} catch (FileNotFoundException e) {e.printStackTrace();}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	
	private void addAlternativas( ArrayList<String> alter,ArrayList<String> coles){
		for(int i=0; i< alter.size();i++){
			String act = alter.get(i);
			for(int j=0; j<coles.size();j++){
				String coleAct = coles.get(j);
				int n = obtNumMesas(coleAct);
				addAlterEnMesas(n, act, coleAct);
			}
			addAlterGeneral(act);
		}
	}
	
	private ArrayList<String> obtColegios(String comunidad){
		PreparedStatement ps=null;
		String sentencia;
		ArrayList<String> cole = new ArrayList<String>();
		if(comunidad.equals("todas")){
		sentencia =
		"SELECT NomColegio FROM ColegioElectoral";
			try {
				ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			} catch (SQLException e) {e.printStackTrace();}
		}else{
			sentencia ="SELECT NomColegio FROM ColegioElectoral AS c INNER JOIN Localidad AS l ON c.Localidad = l.Nombre WHERE l.Comunidad = ?";
			try {
				ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
				ps.setString(1, comunidad);
				} catch (SQLException e) {e.printStackTrace();}
		}
		ResultSet r = SGBD.getConexion().Select(ps);
		try {
			while (r.next()){
				cole.add(r.getString("Alternativa"));
			}
		} catch (SQLException e) {e.printStackTrace();}
		return cole;
	}
	
	
	private void addAlterGeneral(String alter){
		PreparedStatement ps;
		String sentencia;
		sentencia = "INSERT INTO VotosGeneral(CodV, Alternativa) VALUES (?,?)";
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setInt(1, getCodVotacion());
			ps.setString(2, alter);
			SGBD.getConexion().Update(ps);
		} catch (SQLException e) {e.printStackTrace();
		}
	}
	
	
	private int obtNumMesas(String Colegio){
		PreparedStatement ps;
		String sentencia;
		ResultSet r=null;
		int n=0;
		sentencia = "SELECT MAX(Numero) FROM MesaElectoral WHERE NomColegio = ?";
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setString(1, Colegio);
			 r = SGBD.getConexion().Select(ps);
		} catch (SQLException e) {e.printStackTrace();}
		try {
			while (r.next()){
				n=r.getInt("Numero");
			}
		} catch (SQLException e) {e.printStackTrace();}
		return n;
	}
	
	private void addAlterEnMesas(int n, String alter, String cole){
		PreparedStatement ps;
		String sentencia;
		for(int i=1; i<=n;i++){
			sentencia = "INSERT INTO VotosMesa(NumMesa, Alternativa, NomColegio, CodV) VALUES (?,?,?,?)";
			try {
				ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
				ps.setInt(1, i);
				ps.setString(2, alter);
				ps.setString(3, cole);
				ps.setInt(4, getCodVotacion());
				SGBD.getConexion().Update(ps);
			} catch (SQLException e) {e.printStackTrace();
			}
		}
		
	}
	

}
