package source.modelo;

import java.awt.Image;


public class Alternativa {

	private String nombre;
	private Image logo;
	private String descrip;
	
	public Alternativa(String n, Image l, String des){
		this.nombre = n;
		this.logo = l;
		this.descrip = des;
	}

	public String getNombre() {
		return nombre;
	}

	public Image getLogo() {
		return logo;
	}

	public String getDescrip() {
		return descrip;
	}
	
	
	
}
