package source.modelo;

import java.sql.Date;
import java.util.ArrayList;

import source.bd.SGBD;

public class SistemaDeVotaciones {
	
	private static SistemaDeVotaciones miSistema = new SistemaDeVotaciones();
	
	
	private SistemaDeVotaciones(){
		
	}
	
	public static SistemaDeVotaciones getSistema(){
		SGBD.getConexion().conectar();
		return miSistema;
	}

	public void iniciar(int codV){
		GestorVotacion.getGVotacion().iniciar(codV);
	}
	
	public void detener(){
		GestorVotacion.getGVotacion().detener();
	}
	
	public void obtHistorico(int codV, String rutaPdf){
		GestorResultados.getGestorResultados().obtHistorico(codV, rutaPdf);
	}
	
	public void obtResultadosGenerales(int codV, String rutaPdf){
		GestorResultados.getGestorResultados().obtResultadosGenerales(codV, rutaPdf);
	}
	
	public ArrayList<String> obtListaResultadosGenerales(int codV){
		return GestorResultados.getGestorResultados().obtListaResultadosGenerales(codV);
	}
	
	public void obtActasLocales(int codV, String rutaPdf){
		GestorResultados.getGestorResultados().obtActasLocales(codV, rutaPdf);
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
	
	public ArrayList<String> obtComunidades(){
	return GestorVotacion.getGVotacion().obtComunidades();
	}
	
	public ArrayList<String> obtAlternativasAlmacenadas(){
		return GestorVotacion.getGVotacion().obtNombreAlternativasAlmacenadas();
		}
	
}
