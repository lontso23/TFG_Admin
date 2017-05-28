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

	public void iniciar(int codV){
		GestorVotacion.getGVotacion().iniciar(codV);
	}
	
	public void detener(){
		GestorVotacion.getGVotacion().detener();
	}
	
	public void obtHistorico(int codV, String rutaPdf){
		
	}
	
	public void obtResultadosGenerales(int codV, String rutaPdf){
		
	}
	
	public void obtActasLocales(int codV, String rutaPdf){
		
	}
	
	public void generarVotacion(String Comunidad, ArrayList<String> alternativasParaLaVotacion,String descripcionVotacion){
		GestorGenerar.getGenerar().generarVotacion(Comunidad, alternativasParaLaVotacion, descripcionVotacion);
	}

	public ArrayList<Votacion> obtVotacionesSinRealizar(){
		return GestorVotacion.getGVotacion().obtVotacionesNoRealizadas();
	}
	
	public void addVotanes(String ruta){
		GestorGenerar.getGenerar().addVotantesNuevos(ruta);
	}
	
	public void addAlternativa(String nombre, String descrip, String rutafoto){
		GestorGenerar.getGenerar().addAlternativaNueva(nombre, descrip, rutafoto);
	}
	
	
}
