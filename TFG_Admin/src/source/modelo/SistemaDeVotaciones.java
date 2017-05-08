package source.modelo;

import java.sql.Date;

public class SistemaDeVotaciones {
	
	private static SistemaDeVotaciones miSistema = new SistemaDeVotaciones();
	
	
	private SistemaDeVotaciones(){
		
	}
	
	public static SistemaDeVotaciones getSistema(){
		return miSistema;
	}

	public void iniciar(String codV, Date Fecha){
		
	}
	
	public void detener(String codV, Date Fecha){
		
	}
	
	public void obtHistorico(String codV, Date Fecha){
		
	}
	
	public void obtResultadosGenerales(String codV, Date Fecha){
		
	}
	
	
	
	
}
