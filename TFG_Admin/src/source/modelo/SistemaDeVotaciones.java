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

	public void iniciar(String codV,String descrip, Date Fecha){
		
	}
	
	public void detener(){
		
	}
	
	public void obtHistorico(String codV, Date Fecha){
		
	}
	
	public void obtResultadosGenerales(String codV, Date Fecha){
		
	}
	
	public void obtActasLocales(String codV, Date Fecha){
		
	}
	
	public void generarVotacion(String Provincia, ArrayList<String> alternativasParaLaVotacion){
		
	}
	
	public void addVotanes(String ruta){
		
	}
	
	public void addAlternativa(String nombre, String descrip, String rutafoto){
		
	}
	
	
}
