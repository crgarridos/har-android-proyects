package cl.at.bussines;

import java.io.Serializable;

public class Coordenada implements Serializable{

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
	
	//destructor
	public void finalize() throws Throwable {

	}	

}