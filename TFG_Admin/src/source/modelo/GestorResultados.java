package source.modelo;

import java.util.ArrayList;

public class GestorResultados {
	
	private static GestorResultados miResult = new GestorResultados();
	private GestorResultados(){
		
	}
	
	public static GestorResultados getGestorResultados(){
		return miResult;
	}
	

}
