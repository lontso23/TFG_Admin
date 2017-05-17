package source.modelo;

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
	
	public void crearVotacion(String descrip){
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
	
	
	public void generarVotacion(String Provincia, ArrayList<Alternativa> l){
		
		addVotantes(Provincia);
		//obtColegiosDeLosVotantes()
		addAlternativas(l);
	}
	
	public void addVotantesNuevos(ArrayList<String> l){
		//TODO Clase Votante o hacer con split
	}
	
	private void addVotantes(String Provincia){
		
	}
	

	public void addAlternativaNueva(String nombre, String descrip, InputStream rutafoto){
		String sentencia = "INSERT INTO Alternativa(Nombre, Logo, Descripcion) VALUES (?,?,?)";
		PreparedStatement ps;
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setString(1, nombre);
			ps.setBlob(2, rutafoto);//TODO lo de la foto
			ps.setString(3,descrip);
			SGBD.getConexion().Update(ps);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	private void addAlternativas( ArrayList<Alternativa> l){
		for(int i=0; i< l.size();i++){
			Alternativa act = l.get(i);
			String sentencia = "INSERT INTO VotosGeneral(CodV, Alternativa, NumVotos) VALUES (?,?,?)";
			PreparedStatement ps;
			try {
				ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
				ps.setInt(1, getCodVotacion());
				ps.setString(2, act.getNombre());
				ps.setInt(3,0);
				SGBD.getConexion().Update(ps);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			//obtColegiosDeLosVotantes()
			//TODO Añadir cada alternativa por cada mesa de cada colego
		}
	}

}
