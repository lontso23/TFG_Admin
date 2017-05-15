package source.modelo;

import java.sql.Date;
import java.util.ArrayList;

public class SistemaDeVotaciones {
	
	private static SistemaDeVotaciones miSistema = new SistemaDeVotaciones();
	
	
	private SistemaDeVotaciones(){
		
	}
	
	public static SistemaDeVotaciones getSistema(){
		return miSistema;
	}

	public void iniciar(int codV,String descrip, Date Fecha){
		GestorVotacion.getGVotacion().iniciar(codV, descrip, Fecha);
	}
	
	public void detener(){
		GestorVotacion.getGVotacion().detener();
	}
	
	public void obtHistorico(int codV, Date Fecha){
		
	}
	
	public void obtResultadosGenerales(int codV, Date Fecha){
		
	}
	
	public void obtActasLocales(int codV, Date Fecha){
		
	}
	
	public void generarVotacion(String Provincia, ArrayList<String> alternativasParaLaVotacion){
		
	}

	public ArrayList<Votacion> obtVotacionesSinRealizar(){
		return GestorVotacion.getGVotacion().obtVotacionesNoRealizadas();
	}
	
	public void addVotanes(String ruta){
		
	}
	
	public void addAlternativa(String nombre, String descrip, String rutafoto){
		
	}
	
	
}
