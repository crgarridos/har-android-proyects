package cl.at.bussines;


public class Coordenada{

	private Double latitud;
	private Double longitud;

	//constructor
	public Coordenada(){
		this.latitud = null;
		this.longitud = null;
	}
	
	public Coordenada(Double d, Double e){
		setLatitud(d);
		setLongitud(e);
	}


	//get y set latitud
	public Double getLatitud(){
		return latitud;
	}
	
	public void setLatitud(Double latitud){
		this.latitud = latitud;
	}
	
	//get y set longitud
	public Double getLongitud(){
		return longitud;
	}
	
	public void setLongitud(Double longitud){
		this.longitud = longitud;
	}
	
	@Override
	public String toString(){
		return "Longitud: "+this.longitud+", Latitud: "+this.latitud;
	}	

}