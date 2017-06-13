package source.modelo;

import java.util.Observable;
import java.util.Observer;


public class Cronometro extends Observable implements Runnable{
	private Integer horas, segundos, minutos;
	private boolean TAMOactivo;
	private static Cronometro mCronometro = new Cronometro();
	private Thread hilo;

	private Cronometro() {
		horas = 0;
		segundos = 0;
		minutos = 0;
		TAMOactivo = false;
	}
	public static Cronometro getCronometro(){
		return mCronometro;
	}
	
	public void empezar() {
		TAMOactivo = true;
		horas = 0;
		segundos = 0;
		minutos = 0;
		hilo = new Thread(this);
		hilo.start();
	}


	

	public Integer getSegundos() {
		return segundos;
	}
	
	
	
	public Integer getHoras() {
		return horas;
	}
	public Integer getMinutos() {
		return minutos;
	}
	
	public void run() {
		try {
			while(TAMOactivo) {
				if(segundos==59) { segundos=0; minutos++; }
				if(minutos==59) { minutos=0; horas++; }
				segundos++;
				setChanged();
				String temp= horas+","+minutos+","+segundos;
				notifyObservers(temp);
				hilo.sleep(1000); }
			
		} catch (Exception e) {
			horas = 0;
			segundos = 0;
			minutos = 0;
			TAMOactivo = false;
			e.printStackTrace();
			System.out.println("Ha habido un error");
		}
	}

	
	public void parar(){
		TAMOactivo=false;
		//return segundos;
	}
	public void anadirObservador(Observer tb){
		addObserver(tb);
	
		
	}
	
	
}
