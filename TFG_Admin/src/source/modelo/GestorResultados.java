package source.modelo;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import source.bd.SGBD;

public class GestorResultados {
	
	private static GestorResultados miResult = new GestorResultados();
	private GestorResultados(){
		
	}
	
	public static GestorResultados getGestorResultados(){
		return miResult;
	}
	

	public void obtResultadosGenerales(int codV, String rutaPdf){
		ArrayList<String> alternativas = GestorVotacion.getGVotacion().obtNombreAlternativasInscritas(codV);
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
					n=r.getInt("NumVotos");
				}
				SGBD.getConexion().cerrarSelect(r);
			} catch (SQLException e) {e.printStackTrace();}
			actualizarVotosGeneral(alternativas.get(i),codV,n);
		}
	}
	
	private void actualizarVotosGeneral(String alter, int codV,int numVotos){
		String sentencia = "UPDATE VotosGeneral SET NumVotos ? WHERE CodV = ? AND Alternativa = ?";
		PreparedStatement ps;
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setInt(1, numVotos);
			ps.setInt(2, codV);
			ps.setString(3, alter);
			SGBD.getConexion().Update(ps);
		} catch (SQLException e) {e.printStackTrace();}
	}
	
	public void obtActasLocales(String codV, String rutaPdf){
		
	}
	
	public void obtHistorico(String codV, String rutaPdf){
		
	}

	private void realizarEscrutinio(){
		
	}
}
