package source.modelo;

public class Votacion {

	private String cod;
	private String descrip;
	
	public Votacion(String cod, String descrip){
		cod = cod;
		descrip = descrip;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public String getDescrip() {
		return descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	
	
}
