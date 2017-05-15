package source.modelo;

public class Votacion {

	private int cod;
	private String descrip;
	
	public Votacion(int cod, String descrip){
		cod = cod;
		descrip = descrip;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getDescrip() {
		return descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	
	
}
