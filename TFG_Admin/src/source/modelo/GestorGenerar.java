package source.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
	
	public void crearVotacion(String descrip){
		int codV = crearCodigoVotacion();
		String sentencia = "INSERT INTO Votacion(Cod, Descripcion, Date, Activa) VALUES (?,?,'2099-01-01','N')";
		PreparedStatement ps;
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setInt(1, codV);
			ps.setString(2,descrip);
			SGBD.getConexion().Update(ps);
		} catch (SQLException e) {
			
			System.out.println(e.getMessage());
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
				 nuevoCod=r.getInt("MAX(Cod)");				
			}
			SGBD.getConexion().cerrarSelect(r);
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
		addAlternativas(nomAlter,Comunidad);
	}
	
	public void addVotantesNuevos(String ruta){
		Scanner entrada;
		try {
			entrada = new Scanner(new FileReader(ruta));
			while (entrada.hasNext()) {
			String[] datos = null;
			String linea= entrada.nextLine();
			datos = linea.split(",");
				String sentencia = "INSERT INTO Votante(Dni, Pin, Nombre, Apellidos, Domicilio, NomCalle) VALUES (?,?,?,?,?,?)";
				PreparedStatement ps;
				try {
					ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
					for(int i=0; i<datos.length; i++){
						if(i==1){
							ps.setString(i+1, "1234");
						}else{
							ps.setString(i+1, datos[i]);
						}
						
					}
					SGBD.getConexion().Update(ps);
				} catch (SQLException e) {e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {e.printStackTrace();
		}
		
	}
	
	private void addVotantes(String Comunidad){
		if(Comunidad.equals("Todas")){
			addVotantesCompletos();
		}else{
			addVotantesPorComunidad(Comunidad);
		}
		
	}
	
	private void addVotantesPorComunidad(String Comunidad){
		PreparedStatement ps=null;
		String sentencia;
		String dni="";
		ArrayList<String> coles = obtColegios(Comunidad);
		for(int i=0; i< coles.size();i++){
			String coleAct = coles.get(i);
			sentencia="SELECT v.Dni FROM Votante AS v INNER JOIN Calle AS c ON v.NomCalle = c.Nombre WHERE c.NomColegio = ?";
			try {
				ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
				ps.setString(1, coleAct);
				SGBD.getConexion().Select(ps);
				ResultSet r = SGBD.getConexion().Select(ps);
				while (r.next()){
					dni=r.getString("DNI");
					añadirVotanteFinal(dni, coleAct);
				}
				SGBD.getConexion().cerrarSelect(r);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();}
			
		}
	}
	
	
	
	private void addVotantesCompletos(){
		PreparedStatement ps=null;
		String sentencia;
		String dni="";
		String nomColegio="";
		String nomCalle;
		try {
			sentencia = "SELECT Dni, NomCalle FROM Votante";
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			SGBD.getConexion().Select(ps);
			ResultSet r = SGBD.getConexion().Select(ps);
			while (r.next()){
				dni=r.getString("DNI");
				nomCalle = r.getString("NomCalle");
				nomColegio = obtColegioDesdeCalle(nomCalle);
				añadirVotanteFinal(dni, nomColegio);
			}
			SGBD.getConexion().cerrarSelect(r);
		} catch (SQLException e) {e.printStackTrace();}
		
	}

	private void añadirVotanteFinal(String dni, String nomColegio){
		PreparedStatement ps;
		String sentencia;
		sentencia = "INSERT INTO VotosPersona(DniP, NumMesa, NomColegio, CodV) VALUES (?,?,?,?)";
		int numMaxMesas = obtNumMesas(nomColegio);
		Random r = new Random();
		int n=0;
		while(n==0){
			n = r.nextInt(numMaxMesas);
			
		}
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setString(1, dni);
			ps.setInt(2, n);
			ps.setString(3, nomColegio);
			ps.setInt(4, getCodVotacion());
			SGBD.getConexion().Update(ps);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private String obtColegioDesdeCalle(String calle){
		PreparedStatement ps=null;
		String sentencia;
		String nomColegio="";
		try {
			sentencia = "SELECT NomColegio FROM Calle WHERE Nombre = ?";
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setString(1, calle);
			SGBD.getConexion().Select(ps);
			ResultSet r = SGBD.getConexion().Select(ps);
			while (r.next()){
				nomColegio=r.getString("NomColegio");
			}
			SGBD.getConexion().cerrarSelect(r);
		} catch (SQLException e) {e.printStackTrace();}
		return nomColegio;
	}
	
	
	public void addAlternativaNueva(String nombre, String descrip, String rutafoto){
		String sentencia = "INSERT INTO Alternativa(Nombre, Logo, Descrip) VALUES (?,?,?)";
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
	
	
	private void addAlternativas( ArrayList<String> alter,String comunidad){
		ArrayList<String> coles = obtColegios(comunidad);
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
		ArrayList<String> cole = new ArrayList<String>();
		if(comunidad.equals("Todas")){
		return todasLasComunidades(cole);
		}else{
			return porComunidad(comunidad, cole);
		}
	}
	
	private ArrayList<String> todasLasComunidades(ArrayList<String> cole){
		PreparedStatement ps=null;
		String sentencia;
		sentencia = "SELECT Nombre FROM ColegioElectoral";
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ResultSet r = SGBD.getConexion().Select(ps);
			while (r.next()) {
				cole.add(r.getString("Nombre"));
			}
			SGBD.getConexion().cerrarSelect(r);

		} catch (SQLException e) {
			e.printStackTrace();
		}
			return cole;
	}
	
	private ArrayList<String> porComunidad(String comunidad,ArrayList<String> cole){
		PreparedStatement ps=null;
		String sentencia;
		sentencia ="SELECT c.Nombre FROM ColegioElectoral AS c INNER JOIN Localidad AS l ON c.NomLocalidad = l.Nombre WHERE l.Comunidad = ?";
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setString(1, comunidad);
			ResultSet r = SGBD.getConexion().Select(ps);
			while (r.next()){
				cole.add(r.getString("Nombre"));
			}
			SGBD.getConexion().cerrarSelect(r);
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
				n=r.getInt("MAX(Numero)");
			}
			SGBD.getConexion().cerrarSelect(r);
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
