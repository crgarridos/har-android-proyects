package Negocio;

public class Coordenada {

	private Float latitud;
	private Float longitud;

	//constructor
	public Coordenada(Float latitud, Float longitud){
		setLatitud(latitud);
		setLongitud(longitud);
	}

	//get y set latitud
	public Float getLatitud(){
		return latitud;
	}
	
	public void setLatitud(Float latitud){
		this.latitud = latitud;
	}
	
	//get y set longitud
	public Float getLongitud(){
		return longitud;
	}
	
	public void setLongitud(Float longitud){
		this.longitud = longitud;
	}
	
	//destructor
	public void finalize() throws Throwable {

	}	

}