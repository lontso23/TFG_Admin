package source.modelo;

import java.sql.Date;
import java.util.ArrayList;

public class GestorVotacion {
	
	private static GestorVotacion miVotacion = new GestorVotacion();
	private String codVotacion;
	private boolean activa = false;
	private boolean referendum = false;
	
	private GestorVotacion(){
		
	}
	
	
	
	public String getCodVotacion() {
		return codVotacion;
	}



	private void setCodVotacion(String codVotacion) {
		this.codVotacion = codVotacion;
	}



	public boolean isActiva() {
		return activa;
	}



	private void setActiva(boolean activa) {
		this.activa = activa;
	}



	public boolean isReferendum() {
		return referendum;
	}



	private void setReferendum(boolean referendum) {
		this.referendum = referendum;
	}



	public void iniciar(String codV,String descrip, Date Fecha){
		
	}
	
	public void detener(){
		
	}
	
	
	public void obtResultadosGenerales(String codV, Date Fecha){
		
	}
	
	public void obtActasLocales(String codV, Date Fecha){
		
	}
	
	private void realizarEscrutinio(){
		
	}
	
	public ArrayList<String> obtVotacionesNoRealizadas(){
		return null;
	}
	
	public ArrayList<String> obtVotacionesFinalizadas(){
		return null;
	}
	
	public ArrayList<String> obtAlternativasAlmacenadas(){
		return null;
	}
	
	public ArrayList<String> obtAlternativasInscritas(String CodV){
		return null;
	}

}
