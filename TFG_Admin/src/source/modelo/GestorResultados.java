package source.modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import source.bd.SGBD;

public class GestorResultados {
	
	private static GestorResultados miResult = new GestorResultados();
	private GestorResultados(){
		
	}
	
	public static GestorResultados getGestorResultados(){
		return miResult;
	}
	

	public ArrayList<String> obtListaResultadosGenerales(int codV){
		ArrayList<String> alternativas = GestorVotacion.getGVotacion().obtNombreAlternativasInscritas(codV);
		ArrayList<String> resFinal = new ArrayList<String>();
		for(int i=0;i<alternativas.size();i++){
			String sentencia = "SELECT SUM(NumVotos) FROM VotosMesa WHERE CodV = ? AND Alternativa = ?";
			PreparedStatement ps;
			int n=0;
			try {
				ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
				ps.setInt(1, codV);
				ps.setString(2, alternativas.get(i));
				ResultSet r = SGBD.getConexion().Select(ps);
				while (r.next()){
					n=r.getInt("SUM(NumVotos)");
				}
				SGBD.getConexion().cerrarSelect(r);
			} catch (SQLException e) {e.printStackTrace();}
			actualizarVotosGeneral(alternativas.get(i),codV,n);
			resFinal.add(alternativas.get(i)+","+n);
		}
		
		return resFinal;
	}
	
	public void obtResultadosGenerales(int codV,String rutaPdf){
		ArrayList<String> alternativas = GestorVotacion.getGVotacion().obtNombreAlternativasInscritas(codV);
		ArrayList<String> resFinal = new ArrayList<String>();
		for(int i=0;i<alternativas.size();i++){
			String sentencia = "SELECT NumVotos FROM VotosGeneral WHERE CodV = ? AND Alternativa = ?";
			PreparedStatement ps;
			int n=0;
			try {
				ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
				ps.setInt(1, codV);
				ps.setString(2, alternativas.get(i));
				ResultSet r = SGBD.getConexion().Select(ps);
				while (r.next()){
					n=r.getInt("NumVotos");
				}
				SGBD.getConexion().cerrarSelect(r);
			} catch (SQLException e) {e.printStackTrace();}
			resFinal.add(alternativas.get(i)+","+n);
			GetToPDF.getPdf().resultadosGeneral(resFinal, rutaPdf, getDescripVotacion(codV));
		}
		
		
	}
	
	
	private String getDescripVotacion(int codV){
		String sentencia = "SELECT Descripcion FROM Votacion WHERE Cod = ?";
		PreparedStatement ps;
		String n="";
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setInt(1, codV);
			ResultSet r = SGBD.getConexion().Select(ps);
			while (r.next()){
				n=r.getString("Descripcion");
			}
			SGBD.getConexion().cerrarSelect(r);
		} catch (SQLException e) {e.printStackTrace();}
		return n;
	}
	
	private void actualizarVotosGeneral(String alter, int codV,int numVotos){
		String sentencia = "UPDATE VotosGeneral SET NumVotos = ? WHERE CodV = ? AND Alternativa = ?";
		PreparedStatement ps;
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setInt(1, numVotos);
			ps.setInt(2, codV);
			ps.setString(3, alter);
			SGBD.getConexion().Update(ps);
		} catch (SQLException e) {e.printStackTrace();}
	}
	
	public void obtActasLocales(int codV, String rutaPdf){
		ArrayList<String> coles = obtColegios(codV);
		HashMap<String, ArrayList<String>>colesPorLocal = ordenarColesPorLocalidad(coles);
		ArrayList<String> alternativas = GestorVotacion.getGVotacion().obtNombreAlternativasInscritas(codV);
		for (String LocalidadAct : colesPorLocal.keySet()) {
			HashMap<String, ArrayList<String>> puntPorCole = new HashMap<String, ArrayList<String>>();
	        for(int i=0;i<colesPorLocal.get(LocalidadAct).size();i++){
	        	String coleAct=colesPorLocal.get(LocalidadAct).get(i);
	        	if(!puntPorCole.containsKey(coleAct)){
					 ArrayList<String> puntAlter = new ArrayList<String>();
					puntPorCole.put(coleAct, puntAlter);
				 }
	        	for(int j=0;j<alternativas.size();j++){
	        		String AlterYPuntuacion=obtPuntuacionAlterEnColegio(codV, coleAct, alternativas.get(j));
	        		puntPorCole.get(coleAct).add(AlterYPuntuacion);
	        	}
	        }
	      GetToPDF.getPdf().actasLocales(puntPorCole, rutaPdf, LocalidadAct, getDescripVotacion(codV));
	    }
		
	}
	
	private String obtPuntuacionAlterEnColegio(int codV, String nomColegio, String nomALter){
		String sentencia = "SELECT SUM(NumVotos) FROM VotosMesa WHERE CodV = ? AND Alternativa = ? AND NomColegio = ?";
		PreparedStatement ps;
		int n=0;
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setInt(1, codV);
			ps.setString(2, nomALter);
			ps.setString(3, nomColegio);
			ResultSet r = SGBD.getConexion().Select(ps);
			while (r.next()){
				n=r.getInt("SUM(NumVotos)");
			}
			SGBD.getConexion().cerrarSelect(r);
		} catch (SQLException e) {e.printStackTrace();}
		String res = nomALter+","+n;
		return res;
	}
	
	private HashMap<String, ArrayList<String>> ordenarColesPorLocalidad(ArrayList<String> coles){
		 HashMap<String, ArrayList<String>> colesPorLocal = new HashMap<String, ArrayList<String>>();
		 for(int i=0;i<coles.size();i++){
			 String Localidad = getLocalidadDelCole(coles.get(i));
			 if(!colesPorLocal.containsKey(Localidad)){
				 ArrayList<String> colesDeLocal = new ArrayList<String>();
				 colesPorLocal.put(Localidad, colesDeLocal);
				colesPorLocal.get(Localidad).add(coles.get(i));
			 }else{
				 colesPorLocal.get(Localidad).add(coles.get(i));
			 }
		 }
		 return colesPorLocal;

	}
	
	private String getLocalidadDelCole(String cole){
		String sentencia = "SELECT NomLocalidad FROM ColegioElectoral WHERE Nombre = ?";
		PreparedStatement ps;
		String res="";
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setString(1, cole);
			ResultSet r = SGBD.getConexion().Select(ps);
			while (r.next()){
				res = r.getString("NomLocalidad");
			}
			SGBD.getConexion().cerrarSelect(r);
		} catch (SQLException e) {e.printStackTrace();}
		return res;
	}
	
	private ArrayList<String> obtColegios(int codV){
		ArrayList<String> coles = new ArrayList<String>();
		String sentencia = "SELECT NomColegio FROM VotosMesa WHERE CodV = ?";
		PreparedStatement ps;
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setInt(1, codV);
			ResultSet r = SGBD.getConexion().Select(ps);
			while (r.next()){
				String act = r.getString("NomColegio");
				if(!coles.contains(act)){
				coles.add(act);
				}
			}
			SGBD.getConexion().cerrarSelect(r);
		} catch (SQLException e) {e.printStackTrace();}
		return coles;
	}
	public void obtHistorico(int codV, String rutaPdf){
		obtResultadosGenerales(codV, rutaPdf);
	}

	private void realizarEscrutinio(){
		
	}
}
